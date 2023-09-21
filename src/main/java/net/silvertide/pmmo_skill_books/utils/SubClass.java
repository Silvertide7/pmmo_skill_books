package net.silvertide.pmmo_skill_books.utils;

public enum SubClass implements PlayerClass{
    VALKYRIE,
    OATHLESS,
    ASSASSIN;
    @Override
    public String toString() {
        return switch (this) {
            case VALKYRIE -> "valkyrie";
            case OATHLESS -> "oathless";
            case ASSASSIN -> "assassin";
        };
    }
}

