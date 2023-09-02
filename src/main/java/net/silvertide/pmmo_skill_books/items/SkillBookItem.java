package net.silvertide.pmmo_skill_books.items;

import net.minecraft.client.gui.screens.Screen;
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
        UseSkillBookResult useResult = playerCanUseSkillBook(pPlayer);
        boolean hasEnoughXP = pPlayer.getAbilities().instabuild || this.xpLevelsConsumed == 0 || pPlayer.experienceLevel >= this.xpLevelsConsumed;
        if(useResult.isSuccessful() && hasEnoughXP){
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.success(itemstack);
        } else {
            if(pLevel.isClientSide) {
                if(!hasEnoughXP){
                    pPlayer.sendSystemMessage(Component.literal("Requires " + this.xpLevelsConsumed + " experience levels to use."));
                }
                pPlayer.sendSystemMessage(Component.literal(useResult.getMessage()));
            }
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        boolean clientSide = pLevel.isClientSide;

        if (player != null) {
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
            useSkillBook(player);
            if(this.xpLevelsConsumed > 0) {
                if(clientSide) {
                    player.sendSystemMessage(Component.literal("Ate your xp lol."));
                }
                player.giveExperienceLevels(-this.xpLevelsConsumed);
            }

            if(clientSide) player.sendSystemMessage(Component.literal(getEffectDescription()));
        }

        if (!clientSide) {
            ServerLevel serverlevel = (ServerLevel)pLevel;

//            player.openMenu(getMenuProvider(pLevel, pPos));

            for(int i = 0; i < 5; ++i) {
                serverlevel.sendParticles(ParticleTypes.SONIC_BOOM, player.getX() + pLevel.random.nextDouble(), (double)(player.getY() + 1), (double)player.getZ() + pLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
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


//    @Nullable
//    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
//        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
//        if (blockentity instanceof EnchantmentTableBlockEntity) {
//            Component component = ((Nameable) blockentity).getDisplayName();
//            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> new EnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(pLevel, pPos)), component);
//        } else {
//            return null;
//        }
//    }

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
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("§3Effect: " + getHoverTextDescription() + "§r"));
            if(this.xpLevelsConsumed > 0) {
                pTooltipComponents.add(Component.literal("§aXP Cost: " + this.xpLevelsConsumed + " levels§r"));
            }
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.pmmo_skill_books.skill_book.tooltip.shift"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public static class Properties {
        int xpLevelsRequired = 0;
        String description;
        Rarity rarity = Rarity.RARE;

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
