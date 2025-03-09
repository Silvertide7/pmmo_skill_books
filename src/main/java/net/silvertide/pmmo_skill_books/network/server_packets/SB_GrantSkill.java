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

public record SB_GrantSkill(String skill, String applicationType, long applicationValue, int experienceCost, boolean inMainHand) implements CustomPacketPayload {
    public static final Type<SB_GrantSkill> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOSkillBooks.MOD_ID, "sb_grant_skill"));
    public static final StreamCodec<FriendlyByteBuf, SB_GrantSkill> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public SB_GrantSkill decode(FriendlyByteBuf buf) {
            return new SB_GrantSkill(
                    buf.readUtf(),
                    buf.readUtf(),
                    buf.readLong(),
                    buf.readInt(),
                    buf.readBoolean());
        }
        @Override
        public void encode(FriendlyByteBuf buf, SB_GrantSkill grantSkill) {
            buf.writeUtf(grantSkill.skill());
            buf.writeUtf(grantSkill.applicationType());
            buf.writeLong(grantSkill.applicationValue());
            buf.writeInt(grantSkill.experienceCost());
            buf.writeBoolean(grantSkill.inMainHand());
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

        ItemStack stack = player.getItemInHand(packet.inMainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
        if(stack.isEmpty()) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
        }

        Optional<SkillGrantData> skillGrantData = DataComponentUtil.getSkillGrantData(stack);
        if(!(stack.getItem() instanceof SkillGrantItem) || skillGrantData.isEmpty()) {
            return new ValidationResult(false,null, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
        }

        boolean hasSkillOnItem = skillGrantData.get().skills().stream().anyMatch(skill -> packet.skill().equals(skill));
        boolean typeMatches = skillGrantData.get().applicationType().equals(packet.applicationType());
        boolean valueMatches = skillGrantData.get().applicationValue().equals(packet.applicationValue());
        boolean xpMatches = skillGrantData.get().experienceCost() == packet.experienceCost();

        if(!hasSkillOnItem || !typeMatches || !valueMatches || !xpMatches) {
            return new ValidationResult(false, null, Component.translatable("pmmo_skill_book.network.wrong_grant_item"));
        }

        return new ValidationResult(true, stack, Component.translatable("pmmo_skill_book.network.no_item_in_hand"));
    }

    private record ValidationResult(boolean success, ItemStack stackInHand, Component failureMessage){}
}
