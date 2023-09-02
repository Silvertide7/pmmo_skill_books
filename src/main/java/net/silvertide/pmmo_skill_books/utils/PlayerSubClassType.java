package net.silvertide.pmmo_skill_books.utils;

public enum PlayerSubClassType {
    ASSASSIN,
    VALKYRIE;

    @Override
    public String toString() {
        return switch (this) {
            case ASSASSIN -> "assassin";
            case VALKYRIE -> "valkyrie";
        };
    }
}
