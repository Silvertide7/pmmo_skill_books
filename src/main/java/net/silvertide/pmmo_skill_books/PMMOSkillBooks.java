package net.silvertide.pmmo_skill_books;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.silvertide.pmmo_skill_books.registry.DataComponentRegistry;
import net.silvertide.pmmo_skill_books.registry.ItemPropertyRegistry;
import net.silvertide.pmmo_skill_books.registry.ItemRegistry;
import net.silvertide.pmmo_skill_books.registry.TabRegistry;
import org.slf4j.Logger;

@Mod(PMMOSkillBooks.MOD_ID)
public class PMMOSkillBooks
{
    public static final String MOD_ID = "pmmo_skill_books";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOSkillBooks(IEventBus modEventBus, ModContainer modContainer)
    {
        DataComponentRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        TabRegistry.register(modEventBus);
    }

    @EventBusSubscriber(modid = MOD_ID, bus= EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent clientSetupEvent) {
            ItemPropertyRegistry.addCustomItemProperties();
        }
    }

    public static ResourceLocation id(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }
}
