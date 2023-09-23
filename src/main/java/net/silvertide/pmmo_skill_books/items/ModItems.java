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
    public static final List<ClassBook> classBookItems = new ArrayList<>();

    static {
        // ---- COMMAND BOOKS ----
        String wallRunHorKey = "parcool_wall_running_hor";
        String wallRunHorCommand = "/parcool limitation set @s possibility HorizontalWallRun true";
        RegistryObject<Item> COMMAND_BOOK_HOR_WALL_RUN = ITEMS.register(SkillBook.buildKey(SkillBookEffect.COMMAND_4, wallRunHorKey),
                () -> new CommandSkillBookItem(new SkillBookItem.Properties().description("Horizontal Wall Running"), wallRunHorCommand, "You have learned the secrets of horizontal wall running."));
        customBookItems.add(new SkillBook(wallRunHorKey, SkillBookEffect.COMMAND_4, COMMAND_BOOK_HOR_WALL_RUN));

        String wallRunVerKey = "parcool_wall_running_ver";
        String wallRunVerCommand = "/parcool limitation set @s possibility VerticalWallRun true";
        RegistryObject<Item> COMMAND_BOOK_VERT_WALL_RUN = ITEMS.register(SkillBook.buildKey(SkillBookEffect.COMMAND_4, wallRunVerKey),
                () -> new CommandSkillBookItem(new SkillBookItem.Properties().description("Vertical Wall Running"), wallRunVerCommand, "You have learned the secrets of vertical wall running."));
        customBookItems.add(new SkillBook(wallRunVerKey, SkillBookEffect.COMMAND_3, COMMAND_BOOK_VERT_WALL_RUN));

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

        // ---- SUBCLASS BOOKS ----
        for(String classSkill : SkillBookUtil.CLASS_SKILLS) {
            // --- Set Level Class Skill Books
            // Set Level 1
            String setLevel1 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_1, classSkill);
            RegistryObject<Item> CLASS_SKILL_SET_LEVEL_1 = ITEMS.register(setLevel1,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(20), classSkill, 1));
            skillBookItems.add(new SkillBook(classSkill, SkillBookEffect.ADD_LEVEL_1, CLASS_SKILL_SET_LEVEL_1));

            // Set Level 2
            String setLevel2 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_3, classSkill);
            RegistryObject<Item> CLASS_SKILL_SET_LEVEL_2 = ITEMS.register(setLevel2,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(25).rarity(Rarity.UNCOMMON), classSkill, 2));
            skillBookItems.add(new SkillBook(classSkill, SkillBookEffect.ADD_LEVEL_3, CLASS_SKILL_SET_LEVEL_2));

            // Set Level 3
            String setLevel3 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_5, classSkill);
            RegistryObject<Item> CLASS_SKILL_SET_LEVEL_3 = ITEMS.register(setLevel3,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(30).rarity(Rarity.RARE), classSkill, 3));
            skillBookItems.add(new SkillBook(classSkill, SkillBookEffect.ADD_LEVEL_5, CLASS_SKILL_SET_LEVEL_3));

            // Set Level 4
            String setLevel4 = SkillBook.buildKey(SkillBookEffect.ADD_LEVEL_10, classSkill);
            RegistryObject<Item> CLASS_SKILL_SET_LEVEL_4 = ITEMS.register(setLevel4,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(35).rarity(Rarity.EPIC), classSkill, 4));
            skillBookItems.add(new SkillBook(classSkill, SkillBookEffect.ADD_LEVEL_10, CLASS_SKILL_SET_LEVEL_4));
        }

        // ---- CLASS BOOKS ----
        for(IPlayerClass IPlayerClass : PrimaryClass.values()) {
            // --- Set Level Class Skill Books
            // Set Level 1
            String setLevel1 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_1, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_1 = ITEMS.register(setLevel1,
                    () -> new SetLevelClassBookItem(new SkillBookItem.Properties().xpLevelsRequired(20), IPlayerClass.toString(), 1));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_1, CLASS_SET_LEVEL_1));

            // Set Level 2
            String setLevel2 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_2, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_2 = ITEMS.register(setLevel2,
                    () -> new SetLevelClassBookItem(new SkillBookItem.Properties().xpLevelsRequired(25).rarity(Rarity.UNCOMMON), IPlayerClass.toString(), 2));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_2, CLASS_SET_LEVEL_2));

            // Set Level 3
            String setLevel3 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_3, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_3 = ITEMS.register(setLevel3,
                    () -> new SetLevelClassBookItem(new SkillBookItem.Properties().xpLevelsRequired(30).rarity(Rarity.RARE), IPlayerClass.toString(), 3));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_3, CLASS_SET_LEVEL_3));

            // Set Level 4
            String setLevel4 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_4, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_4 = ITEMS.register(setLevel4,
                    () -> new SetLevelClassBookItem(new SkillBookItem.Properties().xpLevelsRequired(35).rarity(Rarity.EPIC), IPlayerClass.toString(), 4));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_4, CLASS_SET_LEVEL_4));
        }

        // ---- SUBCLASS BOOKS ----
        for(IPlayerClass IPlayerClass : SubClass.values()) {
            // --- Set Level Class Skill Books
            // Set Level 1
            String setLevel1 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_1, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_1 = ITEMS.register(setLevel1,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(20), IPlayerClass.toString(), 1));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_1, CLASS_SET_LEVEL_1));

            // Set Level 2
            String setLevel2 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_2, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_2 = ITEMS.register(setLevel2,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(25).rarity(Rarity.UNCOMMON), IPlayerClass.toString(), 2));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_2, CLASS_SET_LEVEL_2));

            // Set Level 3
            String setLevel3 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_3, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_3 = ITEMS.register(setLevel3,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(30).rarity(Rarity.RARE), IPlayerClass.toString(), 3));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_3, CLASS_SET_LEVEL_3));

            // Set Level 4
            String setLevel4 = ClassBook.buildKey(SkillBookEffect.SET_LEVEL_4, IPlayerClass);
            RegistryObject<Item> CLASS_SET_LEVEL_4 = ITEMS.register(setLevel4,
                    () -> new SetLevelSkillBookItem(new SkillBookItem.Properties().xpLevelsRequired(35).rarity(Rarity.EPIC), IPlayerClass.toString(), 4));
            classBookItems.add(new ClassBook(IPlayerClass, SkillBookEffect.SET_LEVEL_4, CLASS_SET_LEVEL_4));
        }
    }

    public static void register(IEventBus eventBus){
        // Trigger the ModItem constructor to run before ITEMS is registered.
        ITEMS.register(eventBus);
    }

}
