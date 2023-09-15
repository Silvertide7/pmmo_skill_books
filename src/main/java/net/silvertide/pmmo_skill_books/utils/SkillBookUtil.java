package net.silvertide.pmmo_skill_books.utils;

public class SkillBookUtil {

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String[] getClassList() {
        return new String[]{"rogue", "fighter"};
    }
    public static String[] getSkillList() {
        return new String[]{"mining", "swimming"};
    }
}
