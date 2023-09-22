package net.silvertide.pmmo_skill_books.utils;

import org.jetbrains.annotations.Nullable;

public enum SubClass implements IPlayerClass {
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
    @Nullable
    public static SubClass fromString(String className) {
        for(SubClass subClass : SubClass.values()) {
            if (className.equals(subClass.toString())) return subClass;
        }
        return null;
    }
}

