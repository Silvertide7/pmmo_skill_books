package net.silvertide.pmmo_skill_books.utils;

public enum PlayerClassType {
    ROGUE,
    FIGHTER,
    WIZARD,
    WARLOCK;

    @Override
    public String toString() {
        return switch (this) {
            case ROGUE -> "rogue";
            case FIGHTER -> "fighter";
            case WIZARD -> "wizard";
            case WARLOCK -> "warlock";
        };
    }
}
