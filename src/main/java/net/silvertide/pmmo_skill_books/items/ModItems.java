package net.silvertide.pmmo_skill_books.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.custom.AddLevelSkillBookItem;
import net.silvertide.pmmo_skill_books.items.custom.AddXPSkillBookItem;
import net.silvertide.pmmo_skill_books.items.custom.CommandSkillBookItem;
import net.silvertide.pmmo_skill_books.items.custom.SetLevelSkillBookItem;
import net.silvertide.pmmo_skill_books.utils.SkillBook;
import net.silvertide.pmmo_skill_books.utils.SkillBookEffect;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;

import java.util.ArrayList;
import java.util.List;


public class ModItems {
    private static ModItems singleton = null;
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOSkillBooks.MOD_ID);
    private List<SkillBook> skillBookItems;

    private ModItems() {
        skillBookItems = new ArrayList<>();
        registerSkills(ITEMS, SkillBookUtil.getSkillList(), skillBookItems);
        registerClasses(ITEMS, SkillBookUtil.getClassList(), skillBookItems);
        registerCustomBooks(ITEMS, skillBookItems);
    }

    public static ModItems getModItems(){
        if(singleton == null) {
            singleton = new ModItems();
        }
        return singleton;
    }

    public List<SkillBook> getSkillBookItems() {
        return this.skillBookItems;
    }

private void registerCustomBooks(DeferredRegister<Item> ITEMS, List<SkillBook> skillBookItems) {
        String commandKey = "set_time_day";
        String command = "/time set day";
        RegistryObject<Item> SKILL_BOOK_COMMAND = ITEMS.register(SkillBook.buildKey(SkillBookEffect.COMMAND_1, commandKey),
            () -> new CommandSkillBookItem(new SkillBookItem.Properties().description("Sets time to day."),command, "Let there be light!"));
        skillBookItems.add(new SkillBook(commandKey, SkillBookEffect.COMMAND_1, SKILL_BOOK_COMMAND));
    }

    private void registerSkills(DeferredRegister<Item> ITEMS, String[] skillList, List<SkillBook> skillBookItems) {
        for(String skill : skillList) {
            // --- ADD XP SKILL BOOKS
            // Add a 5000xp skill book for each skill
            String add5000Key = SkillBook.buildKey(SkillBookEffect.ADD_XP_5000, skill);
            RegistryObject<Item> SKILL_ADD_5000_XP = ITEMS.register(add5000Key,
                    () -> new AddXPSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0), skill, 5000));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_XP_5000, SKILL_ADD_5000_XP));

            // Add a 5000xp skill book for each skill
            String add10000key = SkillBook.buildKey(SkillBookEffect.ADD_XP_10000, skill);
            RegistryObject<Item> SKILL_ADD_10000_XP = ITEMS.register(add10000key,
                    () -> new AddXPSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.UNCOMMON), skill, 10000));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_XP_10000, SKILL_ADD_10000_XP));

            // Add a 5000xp skill book for each skill
            String add25000key = SkillBook.buildKey(SkillBookEffect.ADD_XP_25000, skill);
            RegistryObject<Item> SKILL_ADD_25000_XP = ITEMS.register(add25000key,
                    () -> new AddXPSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.RARE), skill, 25000));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_XP_25000, SKILL_ADD_25000_XP));

            // Add a 50000xp skill book for each skill
            String add50000key = SkillBook.buildKey(SkillBookEffect.ADD_XP_50000, skill);
            RegistryObject<Item> SKILL_ADD_50000_XP = ITEMS.register(add50000key,
                    () -> new AddXPSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.EPIC), skill, 50000));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_XP_50000, SKILL_ADD_50000_XP));

            // Add a 5000xp skill book for each skill
            String addLevel1 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_1, skill);
            RegistryObject<Item> SKILL_ADD_1_LEVEL= ITEMS.register(addLevel1,
                    () -> new AddLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0), skill, 1));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_LEVEL_1, SKILL_ADD_1_LEVEL));

            // Add a 5000xp skill book for each skill
            String addLevel3 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_3, skill);
            RegistryObject<Item> SKILL_ADD_3_LEVEL = ITEMS.register(addLevel3,
                    () -> new AddLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.UNCOMMON), skill, 3));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_LEVEL_3, SKILL_ADD_3_LEVEL));

            // Add a 5000xp skill book for each skill
            String addLevel5 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_5, skill);
            RegistryObject<Item> SKILL_ADD_5_LEVEL = ITEMS.register(addLevel5,
                    () -> new AddLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.RARE), skill, 5));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_LEVEL_5, SKILL_ADD_5_LEVEL));

            // Add a 50000xp skill book for each skill
            String addLevel10 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_10, skill);
            RegistryObject<Item> SKILL_ADD_10_LEVEL = ITEMS.register(addLevel10,
                    () -> new AddLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(0).rarity(Rarity.EPIC), skill, 10));
            skillBookItems.add(new SkillBook(skill, SkillBookEffect.ADD_LEVEL_10, SKILL_ADD_10_LEVEL));
        }
    }

    private void registerClasses(DeferredRegister<Item> ITEMS, String[] classList, List<SkillBook> skillBookItems) {
        for(String playerClass : classList) {
            // --- Set Level Class Skill Books
            // Set Level 1
            String setLevel1 = SkillBook.buildKey(SkillBookEffect.SET_LEVEL_1, playerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_1 = ITEMS.register(setLevel1,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(5), playerClass, 1));
            skillBookItems.add(new SkillBook(playerClass, SkillBookEffect.SET_LEVEL_1, CLASS_SET_LEVEL_1));

            // Set Level 2
            String setLevel2 = SkillBook.buildKey(SkillBookEffect.SET_LEVEL_2, playerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_2 = ITEMS.register(setLevel2,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(10).rarity(Rarity.UNCOMMON), playerClass, 2));
            skillBookItems.add(new SkillBook(playerClass, SkillBookEffect.SET_LEVEL_2, CLASS_SET_LEVEL_2));

            // Set Level 3
            String setLevel3 = SkillBook.buildKey(SkillBookEffect.SET_LEVEL_3, playerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_3 = ITEMS.register(setLevel3,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(15).rarity(Rarity.RARE), playerClass, 3));
            skillBookItems.add(new SkillBook(playerClass, SkillBookEffect.SET_LEVEL_3, CLASS_SET_LEVEL_3));

            // Set Level 4
            String setLevel4 = SkillBook.buildKey(SkillBookEffect.SET_LEVEL_4, playerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_4 = ITEMS.register(setLevel4,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(20).rarity(Rarity.EPIC), playerClass, 4));
            skillBookItems.add(new SkillBook(playerClass, SkillBookEffect.SET_LEVEL_4, CLASS_SET_LEVEL_4));
        }
    }

    public static void register(IEventBus eventBus){
        // Trigger the ModItem constructor to run before ITEMS is registered.
        ModItems modItems = ModItems.getModItems();
        ITEMS.register(eventBus);
    }

}
