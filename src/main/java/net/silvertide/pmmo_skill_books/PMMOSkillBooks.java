package net.silvertide.pmmo_skill_books;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silvertide.pmmo_skill_books.registry.ItemPropertyRegistry;
import net.silvertide.pmmo_skill_books.registry.ItemRegistry;
import net.silvertide.pmmo_skill_books.registry.TabRegistry;
import org.slf4j.Logger;

@Mod(PMMOSkillBooks.MOD_ID)
public class PMMOSkillBooks
{
    public static final String MOD_ID = "pmmo_skill_books";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOSkillBooks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.register(modEventBus);
        TabRegistry.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent clientSetupEvent) {
            clientSetupEvent.enqueueWork(() -> ItemProperties.register(ItemRegistry.SKILL_GRANT.get(), ItemPropertyRegistry.SKILL_GRANT_PROPERTY, (stack, level, entity, seed) -> ItemPropertyRegistry.getSkillGrantConfiguration(stack)));
        }
    }

    public static ResourceLocation id(String location) {
        return new ResourceLocation(MOD_ID, location);
    }
}
