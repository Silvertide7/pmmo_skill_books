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
import net.silvertide.pmmo_skill_books.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkillBookItem extends Item {
    private static final int USE_DURATION = 80;

    public SkillBookItem() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            UseSkillBookResult useResult = SkillBookUtil.canPlayerUseSkillBook(serverPlayer, stack);
            if (useResult.isSuccessful()) {
                serverPlayer.startUsingItem(usedHand);
                return InteractionResultHolder.success(stack);
            } else {
                serverPlayer.sendSystemMessage(Component.literal(useResult.getMessage()));
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
        DataComponentUtil.getSkillBookData(stack).ifPresent(skillBookData -> {
            long currentLevel = APIUtils.getLevel(skillBookData.skill(), serverPlayer);
            long maxLevel = Config.server().levels().maxLevel();

            try {
                long valueToAdd = skillBookData.applicationValue();

                switch(skillBookData.getApplicationType()) {
                    case ApplicationType.LEVEL -> {
                        if(currentLevel != maxLevel && currentLevel + valueToAdd >= maxLevel) {
                            APIUtils.setLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(maxLevel));
                        } else {
                            APIUtils.addLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(skillBookData.applicationValue()));
                        }
                        PlayerMessenger.displayClientMessage(serverPlayer, SkillBookUtil.getSkillBookEffectDescription(skillBookData));
                    }
                    case ApplicationType.XP -> APIUtils.addXp(skillBookData.skill(), serverPlayer, valueToAdd);
                }

            } catch(IllegalArgumentException | ArithmeticException ignored) {
                serverPlayer.sendSystemMessage(Component.literal("Something went wrong."));
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        DataComponentUtil.getSkillBookData(stack).ifPresent(skillBookData -> {
            tooltipComponents.add(Component.literal("§3" + SkillBookUtil.getSkillBookEffectDescription(skillBookData) + "§r"));
        });
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    // TODO: Implement
//    @Override
//    public String getDescriptionId(ItemStack stack) {
//        return stack.has(DataComponents.LODESTONE_TRACKER) ? "item.minecraft.lodestone_compass" : super.getDescriptionId(stack);
//    }
}
