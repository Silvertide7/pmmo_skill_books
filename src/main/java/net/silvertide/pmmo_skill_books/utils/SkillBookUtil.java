package net.silvertide.pmmo_skill_books.utils;

public class SkillBookUtil {

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String[] getClassList() {
        return new String[]{
                "artificer",
                "barbarian",
                "bard",
                "cleric",
                "druid",
                "fighter",
                "monk",
                "paladin",
                "ranger",
                "rogue",
                "sorcerer",
                "warlock",
                "wizard",
        };
    }
    public static String[] getSkillList() {
        return new String[] {
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
                "taming"
        };
    }
}
