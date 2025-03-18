package net.silvertide.pmmo_skill_books.network.server_packets;


import harmonised.pmmo.network.Networking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.items.SkillGrantItem;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.network.PacketHandler;
import net.silvertide.pmmo_skill_books.network.client_packets.CB_CloseSkillGrantScreen;
import net.silvertide.pmmo_skill_books.utils.PlayerMessenger;
import net.silvertide.pmmo_skill_books.utils.SkillGrantUtil;

import java.util.Optional;
import java.util.function.Supplier;

public class SB_GrantSkill {
    private final String skill;
    private final String applicationType;
    private final Long applicationValue;
    private final int experienceCost;

    public SB_GrantSkill(String skill, String applicationType, long applicationValue, int experienceCost) {
        this.skill = skill;
        this.applicationType = applicationType;
        this.applicationValue = applicationValue;
        this.experienceCost = experienceCost;
    }

    public SB_GrantSkill(FriendlyByteBuf buf) {
        this.skill = buf.readUtf();
        this.applicationType = buf.readUtf();
        this.applicationValue = buf.readLong();
        this.experienceCost = buf.readInt();
    }

    public String getSkill() {
        return skill;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public Long getApplicationValue() {
        return applicationValue;
    }

    public int getExperienceCost() {
        return experienceCost;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.skill);
        buf.writeUtf(this.applicationType);
        buf.writeLong(this.applicationValue);
        buf.writeInt(this.experienceCost);
    }

    public static void handle(SB_GrantSkill packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            if(serverPlayer != null) {
                ValidationResult validationResult = getFailureMessage(serverPlayer, packet);
                if(validationResult.success()) {
                    ItemStack stack = validationResult.stackInHand();
                    if(stack.getItem() instanceof SkillGrantItem skillGrantItem) {
                        skillGrantItem.applyEffects(packet.getSkill(), ApplicationType.valueOf(packet.getApplicationType().toUpperCase()), packet.getApplicationValue(), packet.getExperienceCost(), serverPlayer, stack);
                        PacketHandler.sendToClient(serverPlayer, new CB_CloseSkillGrantScreen());
                    }
                } else {
                    PlayerMessenger.displayTranslatabelClientMessage(serverPlayer, validationResult.failureMessage());
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    private static ValidationResult getFailureMessage(ServerPlayer player, SB_GrantSkill packet) {
        if(StringUtil.isNullOrEmpty(packet.getSkill())) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_skill"));
        }

        try {
            ApplicationType.valueOf(packet.getApplicationType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_type"));
        }

        if(packet.getApplicationValue() <= 0) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_value"));
        }

        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if(mainHandStack.isEmpty() && offHandStack.isEmpty()) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
        }

        ItemStack foundStack = null;
        // Check main hand stack
        Optional<SkillGrantData> mainHandSkillGrantData = SkillGrantUtil.getSkillGrantData(mainHandStack);
        if(mainHandStack.getItem() instanceof SkillGrantItem && mainHandSkillGrantData.isPresent() && skillGrantDataMatchesPacket(packet, mainHandSkillGrantData.get())) {
            foundStack = mainHandStack;
        }

        // Check offhand stack if stack still not found.
        if(foundStack == null) {
            Optional<SkillGrantData> offHandStackData = SkillGrantUtil.getSkillGrantData(offHandStack);
            if(offHandStack.getItem() instanceof SkillGrantItem && offHandStackData.isPresent() && skillGrantDataMatchesPacket(packet, offHandStackData.get())) {
                foundStack = offHandStack;
            }
        }

        if(foundStack == null) {
            return new ValidationResult(false,null, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
        }

        return new ValidationResult(true, foundStack, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
    }

    private static boolean skillGrantDataMatchesPacket(SB_GrantSkill packet, SkillGrantData skillGrantData) {
        boolean hasSkillOnItem = skillGrantData.skills().stream().anyMatch(skill -> packet.getSkill().equals(skill));
        boolean typeMatches = skillGrantData.applicationType().equals(packet.getApplicationType());
        boolean valueMatches = skillGrantData.applicationValue().equals(packet.getApplicationValue());
        boolean xpMatches = skillGrantData.experienceCost() == packet.getExperienceCost();
        return hasSkillOnItem && typeMatches && valueMatches && xpMatches;
    }

    private record ValidationResult(boolean success, ItemStack stackInHand, Component failureMessage){}
}
