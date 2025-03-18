package net.silvertide.pmmo_skill_books.network.client_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.silvertide.pmmo_skill_books.gui.SkillGrantScreen;

import java.util.function.Supplier;

public class CB_CloseSkillGrantScreen {
    public CB_CloseSkillGrantScreen() {}
    public CB_CloseSkillGrantScreen(FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}
    public static void handle(CB_CloseSkillGrantScreen msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof SkillGrantScreen skillGrantScreen) {
                skillGrantScreen.onClose();
            }
        });
        context.setPacketHandled(true);
    }
}
