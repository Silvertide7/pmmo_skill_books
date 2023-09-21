package net.silvertide.pmmo_skill_books;

import com.mojang.logging.LogUtils;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.ReqType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silvertide.pmmo_skill_books.commands.CmdRoot;
import net.silvertide.pmmo_skill_books.items.ModItems;
import net.silvertide.pmmo_skill_books.tabs.ModCreativeModeTabs;
import org.slf4j.Logger;

import java.util.function.BiPredicate;

@Mod(PMMOSkillBooks.MOD_ID)
public class PMMOSkillBooks
{
    public static final String MOD_ID = "pmmo_skill_books";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PMMOSkillBooks()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        BiPredicate<Player, ItemStack> ROGUE_WEAPONS = (player, stack) -> {
            int wizardLevel = APIUtils.getLevel("wizard", player);
            int rogueLevel = APIUtils.getLevel("rogue", player);
            return rogueLevel > wizardLevel;
        };

        APIUtils.registerActionPredicate(new ResourceLocation("minecraft:netherite_sword"), ReqType.WEAPON, ROGUE_WEAPONS);
        APIUtils.registerActionPredicate(new ResourceLocation("minecraft:diamond_sword"), ReqType.WEAPON, ROGUE_WEAPONS);
        APIUtils.registerActionPredicate(new ResourceLocation("mysticalagriculture:supremium_sword"), ReqType.WEAPON, ROGUE_WEAPONS);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }

    @Mod.EventBusSubscriber(modid=PMMOSkillBooks.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class CommonSetup {
        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            CmdRoot.register(event.getDispatcher());
        }
    }
}
