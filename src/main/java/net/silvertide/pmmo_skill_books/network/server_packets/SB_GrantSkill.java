package net.silvertide.pmmo_skill_books.network.server_packets;

import harmonised.pmmo.network.Networking;
import io.netty.util.internal.StringUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.items.SkillGrantItem;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.network.client_packets.CB_CloseSkillGrantScreen;
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;
import net.silvertide.pmmo_skill_books.utils.PlayerMessenger;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record SB_GrantSkill(String skill, String applicationType, long applicationValue, int experienceCost) implements CustomPacketPayload {
    public static final Type<SB_GrantSkill> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOSkillBooks.MOD_ID, "sb_grant_skill"));
    public static final StreamCodec<FriendlyByteBuf, SB_GrantSkill> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public SB_GrantSkill decode(FriendlyByteBuf buf) {
            return new SB_GrantSkill(
                    buf.readUtf(),
                    buf.readUtf(),
                    buf.readLong(),
                    buf.readInt());
        }
        @Override
        public void encode(FriendlyByteBuf buf, SB_GrantSkill grantSkill) {
            buf.writeUtf(grantSkill.skill());
            buf.writeUtf(grantSkill.applicationType());
            buf.writeLong(grantSkill.applicationValue());
            buf.writeInt(grantSkill.experienceCost());
        }
    };

    public static void handle(SB_GrantSkill packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.player() instanceof ServerPlayer serverPlayer) {
                ValidationResult validationResult = getFailureMessage(serverPlayer, packet);
                if(validationResult.success()) {
                    ItemStack stack = validationResult.stackInHand();
                    if(stack.getItem() instanceof SkillGrantItem skillGrantItem) {
                        skillGrantItem.applyEffects(packet.skill(), ApplicationType.valueOf(packet.applicationType().toUpperCase()), packet.applicationValue(), packet.experienceCost(), serverPlayer, stack);
                        Networking.sendToClient(new CB_CloseSkillGrantScreen(), serverPlayer);
                    }
                } else {
                    PlayerMessenger.displayTranslatabelClientMessage(serverPlayer, validationResult.failureMessage());
                }
            }
        });
    }

    @Override
    public @NotNull Type<SB_GrantSkill> type() { return TYPE; }

    private static ValidationResult getFailureMessage(ServerPlayer player, SB_GrantSkill packet) {
        if(StringUtil.isNullOrEmpty(packet.skill())) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_skill"));
        }

        try {
            ApplicationType.valueOf(packet.applicationType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_type"));
        }

        if(packet.applicationValue() <= 0) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_value"));
        }

        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if(mainHandStack.isEmpty() && offHandStack.isEmpty()) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
        }

        ItemStack foundStack = null;
        // Check main hand stack
        Optional<SkillGrantData> mainHandSkillGrantData = DataComponentUtil.getSkillGrantData(mainHandStack);
        if(mainHandStack.getItem() instanceof SkillGrantItem && mainHandSkillGrantData.isPresent() && skillGrantDataMatchesPacket(packet, mainHandSkillGrantData.get())) {
            foundStack = mainHandStack;
        }

        // Check offhand stack if stack still not found.
        if(foundStack == null) {
            Optional<SkillGrantData> offHandStackData = DataComponentUtil.getSkillGrantData(offHandStack);
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
        boolean hasSkillOnItem = skillGrantData.skills().stream().anyMatch(skill -> packet.skill().equals(skill));
        boolean typeMatches = skillGrantData.applicationType().equals(packet.applicationType());
        boolean valueMatches = skillGrantData.applicationValue().equals(packet.applicationValue());
        boolean xpMatches = skillGrantData.experienceCost() == packet.experienceCost();
        return hasSkillOnItem && typeMatches && valueMatches && xpMatches;
    }

    private record ValidationResult(boolean success, ItemStack stackInHand, Component failureMessage){}
}
