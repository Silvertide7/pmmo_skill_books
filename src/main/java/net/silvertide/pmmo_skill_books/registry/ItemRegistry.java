package net.silvertide.pmmo_skill_books.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.SkillGrantItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOSkillBooks.MOD_ID);
    public static void register(IEventBus eventBus){ ITEMS.register(eventBus); }

    public static final RegistryObject<Item> SKILL_GRANT = ITEMS.register("skill_grant", () -> new SkillGrantItem());
}
