package net.silvertide.pmmo_skill_books.utils;
public class SkillBookUtil {
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

    public static final String[] CLASS_SKILLS = new String[]{
            "vision"
    };

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }


}
