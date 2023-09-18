package net.silvertide.pmmo_skill_books.utils;

import java.util.ArrayList;
import java.util.List;

public class SkillBookUtil {

    public static final PlayerClass[] PLAYER_CLASSES = new PlayerClass[] {
        new PlayerClass(PlayerClassType.ARTIFICER),
                new PlayerClass(PlayerClassType.BARBARIAN),
                new PlayerClass(PlayerClassType.BARD),
                new PlayerClass(PlayerClassType.CLERIC),
                new PlayerClass(PlayerClassType.DRUID),
                new PlayerClass(PlayerClassType.FIGHTER),
                new PlayerClass(PlayerClassType.MONK),
                new PlayerClass(PlayerClassType.PALADIN)
                        .addSubClass(PlayerSubClassType.VALKYRIE),
                new PlayerClass(PlayerClassType.RANGER),
                new PlayerClass(PlayerClassType.ROGUE),
                new PlayerClass(PlayerClassType.SORCERER),
                new PlayerClass(PlayerClassType.WARLOCK),
                new PlayerClass(PlayerClassType.WIZARD),
    };

    public static final String[] SKILLS = new String[]{
        "magic",
        "slayer",
        "fishing",
        "combat",
        "alchemy",
        "mining",
        "engineering",
        "endurance",
        "building",
        "smithing",
        "swimming",
        "woodcutting",
        "gunslinging",
        "crafting",
        "excavation",
        "farming",
        "flying",
        "cooking",
        "agility",
        "sailing",
        "hunter",
        "archery",
        "taming",
        "strength",
        "dexterity",
        "constitution",
        "intelligence",
        "wisdom",
        "charisma"
    };

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String[] getClassListAsStrings(boolean includeSubclasses) {
        List<String> classStrings = new ArrayList<>();
        for(PlayerClass playerClass : PLAYER_CLASSES) {
            classStrings.add(playerClass.getType().toString());
            if(includeSubclasses) {
                for(PlayerSubClassType subClassType : playerClass.getSubClasses()) {
                    classStrings.add(subClassType.toString());
                }
            }
        }
        return classStrings.toArray(String[]::new);
    }
}
