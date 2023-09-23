package net.silvertide.pmmo_skill_books.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.pmmo_skill_books.utils.UseSkillBookResult;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SkillBookItem extends Item {
    private static final int USE_DURATION = 32;
    private int xpLevelsConsumed;
    @Nullable
    protected String description;

    protected SkillBookItem(Properties properties) {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(properties.rarity));
        this.xpLevelsConsumed = properties.xpLevelsRequired;
        this.description = properties.description;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide) {
            UseSkillBookResult useResult = playerCanUseSkillBook(pPlayer);
            boolean hasEnoughXP = pPlayer.getAbilities().instabuild || this.xpLevelsConsumed == 0 || pPlayer.experienceLevel >= this.xpLevelsConsumed;
            if (pPlayer.getAbilities().instabuild || (useResult.isSuccessful() && hasEnoughXP)) {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.success(itemstack);
            } else {
                if (!useResult.isSuccessful()){
                    pPlayer.sendSystemMessage(Component.literal(useResult.getMessage()));
                } else if (!hasEnoughXP) {
                    pPlayer.sendSystemMessage(Component.literal("Requires " + this.xpLevelsConsumed + " experience levels to use."));
                }
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;

        if (player != null && !pLevel.isClientSide) {
            boolean stillHasEnoughXP = true;
            if(!player.getAbilities().instabuild && this.xpLevelsConsumed > 0) {
                if(player.experienceLevel >= this.xpLevelsConsumed) {
                    player.giveExperienceLevels(-this.xpLevelsConsumed);
                } else {
                    player.sendSystemMessage(Component.literal("Requires " + this.xpLevelsConsumed + " experience levels to use."));
                    stillHasEnoughXP = false;
                }
            }
            if(stillHasEnoughXP) {
                useSkillBook(player);
                player.sendSystemMessage(Component.literal(getEffectDescription()));
                if (!player.getAbilities().instabuild) {
                    pStack.shrink(1);
                }
                ServerLevel serverlevel = (ServerLevel)pLevel;
                for(int i = 0; i < 30; ++i) {
                    serverlevel.sendParticles(ParticleTypes.ENCHANT, player.getX() + pLevel.random.nextDouble(), (double)(player.getY() + 1), (double)player.getZ() + pLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                }
            }
        }
        return pStack;
    }

    protected abstract UseSkillBookResult playerCanUseSkillBook(Player player);

    private String getRequirementDescription() {
        return "Requires " + this.xpLevelsConsumed + " levels to use.";
    }

    protected abstract void useSkillBook(Player player);
    protected abstract String getEffectDescription();
    protected abstract String getHoverTextDescription();

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§3" + getHoverTextDescription() + "§r"));
        if(this.xpLevelsConsumed > 0) {
            pTooltipComponents.add(Component.literal("§aXP Cost: " + this.xpLevelsConsumed + " levels§r"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public static class Properties {
        int xpLevelsRequired = 0;
        String description;
        Rarity rarity = Rarity.COMMON;

        public Properties xpLevelsRequired(int xpLevelsRequired){
            this.xpLevelsRequired = xpLevelsRequired;
            return this;
        }

        public Properties description(String description){
            this.description = description;
            return this;
        }

        public Properties rarity(Rarity rarity){
            this.rarity = rarity;
            return this;
        }
    }
}
