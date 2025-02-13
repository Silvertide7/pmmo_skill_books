package net.silvertide.pmmo_skill_books.items;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import harmonised.pmmo.core.Core;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_skill_books.utils.ApplicationType;
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;
import net.silvertide.pmmo_skill_books.utils.UseSkillBookResult;
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
            UseSkillBookResult useResult = canPlayerUseSkillBook(serverPlayer, stack);
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
            serverPlayer.sendSystemMessage(Component.literal(getEffectDescription()));
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

    private UseSkillBookResult canPlayerUseSkillBook(Player player, ItemStack stack) {
        return DataComponentUtil.getSkillBookData(stack).map(skillBookData -> {
            if(StringUtil.isBlank(skillBookData.skill())){
                return new UseSkillBookResult(true, "Skill book has no skill specified");
            }

            if(skillBookData.applicationValue() <= 0) {
                return new UseSkillBookResult(true, "Value must be greater than 0");
            }

            try {
                ApplicationType.valueOf(skillBookData.applicationType());
            } catch(IllegalArgumentException ex) {
                return new UseSkillBookResult(true, "Application type must be level or xp");
            }

            if ( APIUtils.getLevel(skillBookData.skill(), player) >= Config.server().levels().maxLevel() ) {
                return new UseSkillBookResult(false, SkillBookUtil.capitalize(skillBookData.skill()) + " is at max level.");
            }
            return new UseSkillBookResult(true, "");
        }).orElse(new UseSkillBookResult(false, "No Skill Book Data Found"));


    }

    private void useSkillBook(ServerPlayer serverPlayer, ItemStack stack) {
        DataComponentUtil.getSkillBookData(stack).ifPresent(skillBookData -> {
            long currentLevel = APIUtils.getLevel(skillBookData.skill(), serverPlayer);
            long maxLevel = Config.server().levels().maxLevel();

            try {
                ApplicationType applicationType = ApplicationType.valueOf(skillBookData.applicationType());
                long valueToAdd = skillBookData.applicationValue();
                if(currentLevel != maxLevel && currentLevel + valueToAdd >= maxLevel) {
                    APIUtils.setLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(maxLevel));
                } else {
                    APIUtils.addLevel(skillBookData.skill(), serverPlayer, Math.toIntExact(skillBookData.applicationValue()));
                }

//                if(willLevelToMax(this.skill, player, this.xpToAdd)){
//                    APIUtils.setLevel(this.skill, player, Config.server().levels().);
//                } else {
//                    APIUtils.addXp(this.skill, player, this.xpToAdd);
//                }

            } catch(IllegalArgumentException | ArithmeticException ignored) {
                serverPlayer.sendSystemMessage(Component.literal("Error applying skill book."));
            }



        });
    }

//    private boolean willLevelToMax(String skill, Player player, long xpToAdd) {
//        long maxLevel = Config.server().levels().maxLevel();
//        long currXP = APIUtils.getXp(skill, player);
//        int resultingLevel = Core.get(player.level()).getData().getXp(currXP + xpToAdd);
//        return resultingLevel >= maxLevel;
//    }

    private String getEffectDescription() {
//        return "You have gained " + this.xpToAdd + " " + SkillBookUtil.capitalize(this.skill) + " experience.";
//        return "You have gained " + this.levelsToAdd + " " + (this.levelsToAdd == 1 ? "level" : "levels") + " of " + SkillBookUtil.capitalize(this.skill) + ".";
        return "effect description";

    }

    private String getHoverTextDescription() {
//        if(this.description != null) return this.description;
//        return "+" + this.levelsToAdd + " " + SkillBookUtil.capitalize(this.skill) + (this.levelsToAdd == 1 ? " Level" : " Levels");

//        if(this.description != null) return this.description;
//        return "+" + this.xpToAdd + " " + SkillBookUtil.capitalize(this.skill) + " Experience";
        return "hovertext";
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
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    //    @Override
//    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
//        pTooltipComponents.add(Component.literal("§3" + getHoverTextDescription() + "§r"));
//        if(this.xpLevelsConsumed > 0) {
//            pTooltipComponents.add(Component.literal("§aXP Cost: " + this.xpLevelsConsumed + " levels§r"));
//        }
//        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
//    }

    // TODO: Implement
    @Override
    public String getDescriptionId(ItemStack stack) {
        return stack.has(DataComponents.LODESTONE_TRACKER) ? "item.minecraft.lodestone_compass" : super.getDescriptionId(stack);
    }
}
