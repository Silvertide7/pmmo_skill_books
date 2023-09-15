package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookEffect {
    ADD_XP_5000,
    ADD_XP_10000,
    ADD_XP_25000,
    ADD_XP_50000,
    ADD_LEVEL_1,
    ADD_LEVEL_3,
    ADD_LEVEL_5,
    ADD_LEVEL_10,
    SET_LEVEL_1,
    SET_LEVEL_2,
    SET_LEVEL_3,
    SET_LEVEL_4,
    COMMAND_1,
    COMMAND_2,
    COMMAND_3,
    COMMAND_4;

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
            case SET_LEVEL_1 -> "set_level_1";
            case SET_LEVEL_2 -> "set_level_2";
            case SET_LEVEL_3 -> "set_level_3";
            case SET_LEVEL_4 -> "set_level_4";
            case COMMAND_1 -> "command_1";
            case COMMAND_2 -> "command_2";
            case COMMAND_3 -> "command_3";
            case COMMAND_4 -> "command_4";
        };
    }
}
