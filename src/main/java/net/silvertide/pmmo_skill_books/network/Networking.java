package net.silvertide.pmmo_skill_books.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.network.client_packets.CB_CloseSkillGrantScreen;
import net.silvertide.pmmo_skill_books.network.server_packets.SB_GrantSkill;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = PMMOSkillBooks.MOD_ID)
public class Networking {
    @SubscribeEvent
    public static void registerMessages(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PMMOSkillBooks.MOD_ID);

        registrar
                .playToClient(CB_CloseSkillGrantScreen.TYPE, CB_CloseSkillGrantScreen.STREAM_CODEC, CB_CloseSkillGrantScreen::handle)
                .playToServer(SB_GrantSkill.TYPE, SB_GrantSkill.STREAM_CODEC, SB_GrantSkill::handle);
    }
}
