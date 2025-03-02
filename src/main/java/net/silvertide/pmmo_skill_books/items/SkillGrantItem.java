package net.silvertide.pmmo_skill_books.items;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import io.netty.util.internal.StringUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillGrantResult;
import net.silvertide.pmmo_skill_books.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkillGrantItem extends Item {
    private static final int USE_DURATION = 60;

    public SkillGrantItem() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            UseSkillGrantResult useResult = SkillBookUtil.canPlayerUseSkillBook(serverPlayer, stack);
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
        DataComponentUtil.getSkillGrantData(stack).ifPresent(skillGrantData -> {
            if(skillGrantData.skills().size() == 1) {
                String skill = skillGrantData.skills().getFirst();
                long currentLevel = APIUtils.getLevel(skill, serverPlayer);
                long maxLevel = Config.server().levels().maxLevel();

                try {
                    long valueToAdd = skillGrantData.applicationValue();

                    switch(skillGrantData.getApplicationType()) {
                        case ApplicationType.LEVEL -> {
                            if(currentLevel != maxLevel && currentLevel + valueToAdd >= maxLevel) {
                                APIUtils.setLevel(skill, serverPlayer, Math.toIntExact(maxLevel));
                            } else {
                                APIUtils.addLevel(skill, serverPlayer, Math.toIntExact(skillGrantData.applicationValue()));
                            }
                            PlayerMessenger.displayTranslatabelClientMessage(serverPlayer, Component.translatable(SkillBookUtil.getSkillBookEffectTranslationKey(skillGrantData), skillGrantData.applicationValue(), GUIUtil.prettifySkill(skill)));
                        }
                        case ApplicationType.XP -> APIUtils.addXp(skill, serverPlayer, valueToAdd);
                    }

                } catch(IllegalArgumentException | ArithmeticException ignored) {
                    serverPlayer.sendSystemMessage(Component.translatable("pmmo_skill_books.message.use_book_error"));
                }
            } else if (skillGrantData.skills().size() > 1) {

            }

        });
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        DataComponentUtil.getSkillGrantData(stack).ifPresent(skillGrantData -> {
//            tooltipComponents.add(Component.translatable(SkillBookUtil.getSkillBookEffectTranslationKey(skillGrantData), skillGrantData.applicationValue(), skillGrantData.getSkillName()));
//        });
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return DataComponentUtil.getSkillGrantData(stack).map(insigniaData ->  {
            if(!StringUtil.isNullOrEmpty(insigniaData.name())) {
                return insigniaData.name();
            }
            return super.getDescriptionId(stack);
        }).orElse(super.getDescriptionId(stack));
    }
}
