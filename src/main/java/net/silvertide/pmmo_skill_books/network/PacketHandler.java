package net.silvertide.pmmo_skill_books.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.network.client_packets.CB_CloseSkillGrantScreen;
import net.silvertide.pmmo_skill_books.network.server_packets.SB_GrantSkill;

public class PacketHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PMMOSkillBooks.MOD_ID, "main"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // CLIENT BOUND
        net.messageBuilder(CB_CloseSkillGrantScreen.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CB_CloseSkillGrantScreen::new)
                .encoder(CB_CloseSkillGrantScreen::encode)
                .consumerMainThread(CB_CloseSkillGrantScreen::handle)
                .add();

        // SERVER BOUND
        net.messageBuilder(SB_GrantSkill.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SB_GrantSkill::new)
                .encoder(SB_GrantSkill::encode)
                .consumerMainThread(SB_GrantSkill::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
