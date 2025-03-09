package net.silvertide.pmmo_skill_books.network.client_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.gui.SkillGrantScreen;
import org.jetbrains.annotations.NotNull;

public record CB_CloseSkillGrantScreen() implements CustomPacketPayload {
    public static final CB_CloseSkillGrantScreen INSTANCE = new CB_CloseSkillGrantScreen();
    public static final Type<CB_CloseSkillGrantScreen> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PMMOSkillBooks.MOD_ID, "cb_skill_grant_screen"));
    public static final StreamCodec<FriendlyByteBuf, CB_CloseSkillGrantScreen> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static void handle(CB_CloseSkillGrantScreen packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof SkillGrantScreen skillGrantScreen) {
                skillGrantScreen.onClose();
            }
        });
    }
    @Override
    public @NotNull Type<CB_CloseSkillGrantScreen> type() { return TYPE; }
}
