package net.silvertide.pmmo_skill_books;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silvertide.pmmo_skill_books.items.ModItems;
import net.silvertide.pmmo_skill_books.tabs.ModCreativeModeTabs;
import org.slf4j.Logger;

@Mod(PMMOSkillBooks.MOD_ID)
public class PMMOSkillBooks
{
    public static final String MOD_ID = "pmmo_skill_books";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PMMOSkillBooks()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
    }
}
