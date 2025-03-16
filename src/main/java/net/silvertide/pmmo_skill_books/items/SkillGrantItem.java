package net.silvertide.pmmo_skill_books.items;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.silvertide.pmmo_skill_books.client.ClientUtil;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillGrantResult;
import net.silvertide.pmmo_skill_books.events.SkillGrantEvent;
import net.silvertide.pmmo_skill_books.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkillGrantItem extends Item {
    private static final int USE_DURATION = 50;

    public SkillGrantItem() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            UseSkillGrantResult useResult = SkillGrantUtil.canPlayerUseSkillBook(serverPlayer, stack);
            if (useResult.success()) {
                serverPlayer.startUsingItem(usedHand);
                return InteractionResultHolder.success(stack);
            } else {
                serverPlayer.sendSystemMessage(Component.translatable(useResult.message()).withColor(0xfe7878));
            }
        }
        return InteractionResultHolder.fail(stack);
    }


    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            useSkillBook(player, stack);
        }
        return stack;
    }

    private void useSkillBook(Player player, ItemStack stack) {
        DataComponentUtil.getSkillGrantData(stack).ifPresent(skillGrantData -> {
            if(skillGrantData.skills().size() == 1 && player instanceof ServerPlayer serverPlayer) {
                applyEffects(skillGrantData.skills().getFirst(), skillGrantData.getApplicationType(), skillGrantData.applicationValue(), skillGrantData.experienceCost(), serverPlayer, stack);
            } else if (skillGrantData.skills().size() > 1 && player instanceof LocalPlayer) {
                ClientUtil.openSkillGrantScreen(skillGrantData);
            }
        });
    }

    public void applyEffects(String skill, ApplicationType applicationType, Long applicationValue, int experienceCost, ServerPlayer serverPlayer, ItemStack stack) {
        if(!serverPlayer.getAbilities().instabuild && experienceCost > serverPlayer.experienceLevel) {
            serverPlayer.sendSystemMessage(Component.translatable("pmmo_skill_books.message.not_enough_experience").withColor(0xfe7878));
            return;
        }

        long currentLevel = APIUtils.getLevel(skill, serverPlayer);
        long maxLevel = Config.server().levels().maxLevel();

        if(!NeoForge.EVENT_BUS.post(new SkillGrantEvent.Pre(serverPlayer, skill, applicationType.name(), applicationValue)).isCanceled()) {
            try {
                switch(applicationType) {
                    case ApplicationType.LEVEL -> {
                        if(currentLevel != maxLevel && currentLevel + applicationValue >= maxLevel) {
                            APIUtils.setLevel(skill, serverPlayer, Math.toIntExact(maxLevel));
                        } else {
                            APIUtils.addLevel(skill, serverPlayer, Math.toIntExact(applicationValue));
                        }
                    }
                    case ApplicationType.XP -> APIUtils.addXp(skill, serverPlayer, applicationValue);
                    case ApplicationType.SET -> {
                        if(applicationValue > maxLevel) {
                            APIUtils.setLevel(skill, serverPlayer, Math.toIntExact(maxLevel));
                        } else {
                            APIUtils.setLevel(skill, serverPlayer, Math.toIntExact(applicationValue));
                        }
                    }
                }

                PlayerMessenger.displayTranslatabelClientMessage(serverPlayer,
                        Component.translatable(SkillGrantUtil.getSkillBookEffectTranslationKey(applicationType, applicationValue), applicationValue, GUIUtil.getTranslatedSkillString(skill)));

                payCosts(serverPlayer, stack, experienceCost);
                NeoForge.EVENT_BUS.post(new SkillGrantEvent.Post(serverPlayer, skill, applicationType.name(), applicationValue));
            } catch(IllegalArgumentException | ArithmeticException ignored) {
                serverPlayer.sendSystemMessage(Component.translatable("pmmo_skill_books.message.use_book_error").withColor(0xfe7878));
            }
        }
    }

    public static void payCosts(ServerPlayer player, ItemStack stack, int experienceCost) {
        if(player instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.getAbilities().instabuild) {
                stack.shrink(1);
            }

            if(!serverPlayer.getAbilities().instabuild && experienceCost > 0) {
                player.setExperienceLevels(player.experienceLevel - experienceCost);
            }

            if(serverPlayer.level() instanceof ServerLevel serverLevel) {
                for(int i = 0; i < 30; ++i) {
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, serverPlayer.getX() + serverLevel.random.nextDouble(), serverPlayer.getY() + 1, serverPlayer.getZ() + serverLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                }
            }

        }
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
        DataComponentUtil.getSkillGrantData(stack).ifPresent(skillGrantData -> {
            Component information = Component.translatable(SkillGrantUtil.getSkillBookEffectTranslationKey(skillGrantData.getApplicationType(), skillGrantData.applicationValue()), skillGrantData.applicationValue(), skillGrantData.getSkillNames()).withColor(0x35acff);
            tooltipComponents.add(information);

            if(skillGrantData.experienceCost() > 0) {
                if(skillGrantData.experienceCost() == 1) {
                    tooltipComponents.add(Component.translatable("pmmo_skill_books.hovertext.experience_cost", skillGrantData.experienceCost()).withColor(0xff8d8d));
                } else {
                    tooltipComponents.add(Component.translatable("pmmo_skill_books.hovertext.experience_cost_plural", skillGrantData.experienceCost()).withColor(0xff8d8d));

                }
            }
        });
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

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
