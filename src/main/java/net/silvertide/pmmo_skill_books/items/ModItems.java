package net.silvertide.pmmo_skill_books.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.custom.AddLevelSkillBookItem;
import net.silvertide.pmmo_skill_books.items.custom.AddXPSkillBookItem;
import net.silvertide.pmmo_skill_books.items.custom.SetLevelSkillBookItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOSkillBooks.MOD_ID);
    public static final RegistryObject<Item> SKILL_BOOK = ITEMS.register("skill_book_set_level",
            () -> new SetLevelSkillBookItem(new SkillBookItem.Properties(),"rogue", 2));
    public static final RegistryObject<Item> SKILL_BOOK_2 = ITEMS.register("skill_book_add_xp",
            () -> new AddXPSkillBookItem(new SkillBookItem.Properties(),"rogue", 5000));
    public static final RegistryObject<Item> SKILL_BOOK_3 = ITEMS.register("skill_book_add_level",
            () -> new AddLevelSkillBookItem(new SkillBookItem.Properties(),"rogue", 4));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
