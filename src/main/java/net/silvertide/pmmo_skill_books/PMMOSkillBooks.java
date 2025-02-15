package net.silvertide.pmmo_skill_books;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.silvertide.pmmo_skill_books.registry.DataComponentRegistry;
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
}
