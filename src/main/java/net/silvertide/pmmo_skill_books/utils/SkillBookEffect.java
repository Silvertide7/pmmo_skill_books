package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookEffect {
    ADD_XP_5000,
    ADD_XP_10000,
    ADD_XP_25000,
    ADD_XP_50000,
    ADD_LEVEL_1,
    ADD_LEVEL_3,
    ADD_LEVEL_5,
    ADD_LEVEL_10;

    @Override
    public String toString() {
        return switch (this) {
            case ADD_XP_5000 -> "add_xp_5000";
            case ADD_XP_10000 -> "add_xp_10000";
            case ADD_XP_25000 -> "add_xp_25000";
            case ADD_XP_50000 -> "add_xp_50000";
            case ADD_LEVEL_1 -> "add_level_1";
            case ADD_LEVEL_3 -> "add_level_2";
            case ADD_LEVEL_5 -> "add_level_3";
            case ADD_LEVEL_10 -> "add_level_4";
        };
    }
}
