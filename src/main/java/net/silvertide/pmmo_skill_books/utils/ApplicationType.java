package net.silvertide.pmmo_skill_books.utils;

public enum ApplicationType {
    LEVEL,
    XP;

    @Override
    public String toString() {
        return switch (this) {
            case LEVEL -> "level";
            case XP -> "xp";
        };
    }
}
