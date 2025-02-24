package net.silvertide.pmmo_skill_books.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.custom.*;
import net.silvertide.pmmo_skill_books.utils.*;

import java.util.ArrayList;
import java.util.List;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOSkillBooks.MOD_ID);
    public static final List<SkillBook> skillBookItems = new ArrayList<>();
    public static final List<SkillBook> customBookItems = new ArrayList<>();

    static {
        // ---- SKILL BOOKS ----
        for(String skill : SkillBookUtil.SKILLS) {
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

    public static void register(IEventBus eventBus){
        // Trigger the ModItem constructor to run before ITEMS is registered.
        ITEMS.register(eventBus);
    }

}
