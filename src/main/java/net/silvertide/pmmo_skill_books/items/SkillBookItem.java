package net.silvertide.pmmo_skill_books.items;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_skill_books.data.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.util.PlayerMessenger;
import net.silvertide.pmmo_skill_books.util.SkillBookUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class SkillBookItem extends Item {
    private static final int USE_DURATION = 80;

    public SkillBookItem() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            UseSkillBookResult useResult = SkillBookUtil.canPlayerUseSkillBook(serverPlayer, stack);
            if (useResult.success()) {
                serverPlayer.startUsingItem(usedHand);
                return InteractionResultHolder.success(stack);
            } else {
                serverPlayer.sendSystemMessage(Component.translatable(useResult.message()));
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            useSkillBook(serverPlayer, stack);
            if (!serverPlayer.getAbilities().instabuild) {
                stack.shrink(1);
            }
            ServerLevel serverlevel = (ServerLevel) level;
            for(int i = 0; i < 30; ++i) {
                serverlevel.sendParticles(ParticleTypes.ENCHANT, serverPlayer.getX() + level.random.nextDouble(), serverPlayer.getY() + 1, serverPlayer.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
            }
        }
        return stack;
    }

    private void useSkillBook(ServerPlayer serverPlayer, ItemStack stack) {
        SkillBookUtil.getSkillBookData(stack).ifPresent(skillBookData -> {
            long currentLevel = APIUtils.getLevel(skillBookData.skill(), serverPlayer);
            long maxLevel = Config.MAX_LEVEL.get();

            try {
                long valueToAdd = skillBookData.applicationValue();

                switch(skillBookData.getApplicationType()) {
                    case LEVEL -> {
                        if(currentLevel != maxLevel && currentLevel + valueToAdd >= maxLevel) {
                            APIUtils.setLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(maxLevel));
                        } else {
                            APIUtils.addLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(skillBookData.applicationValue()));
                        }
                        PlayerMessenger.displayTranslatabelClientMessage(serverPlayer, Component.translatable(SkillBookUtil.getSkillBookEffectTranslationKey(skillBookData), skillBookData.applicationValue(), skillBookData.getSkillName()));
                    }
                    case XP -> APIUtils.addXp(skillBookData.skill(), serverPlayer, valueToAdd);
                }

            } catch(IllegalArgumentException | ArithmeticException ignored) {
                serverPlayer.sendSystemMessage(Component.translatable("pmmo_skill_books.message.use_book_error"));
            }
        });
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        SkillBookUtil.getSkillBookData(stack).ifPresent(skillBookData -> {
            tooltipComponents.add(Component.translatable(SkillBookUtil.getSkillBookEffectTranslationKey(skillBookData), skillBookData.applicationValue(), skillBookData.getSkillName()));
        });
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return SkillBookUtil.getSkillBookData(stack).map(skillBookData -> switch (skillBookData.getTrim()) {
            case GOLD -> "item.pmmo_skill_books.gold_skill_book";
            case EMERALD -> "item.pmmo_skill_books.emerald_skill_book";
            case DIAMOND -> "item.pmmo_skill_books.diamond_skill_book";
            default -> super.getDescriptionId(stack);
        }).orElse(super.getDescriptionId(stack));
    }
}
