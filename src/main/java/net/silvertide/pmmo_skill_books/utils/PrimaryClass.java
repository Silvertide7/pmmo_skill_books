package net.silvertide.pmmo_skill_books.utils;

import org.jetbrains.annotations.Nullable;

public enum PrimaryClass implements IPlayerClass {
    ARTIFICER,
    BARBARIAN,
    BARD,
    CLERIC,
    DRUID,
    FIGHTER,
    MONK,
    PALADIN,
    RANGER,
    ROGUE,
    SORCERER,
    WARLOCK,
    WIZARD;
    @Override
    public String toString() {
        return switch (this) {
            case ARTIFICER -> "artificer";
            case BARBARIAN -> "barbarian";
            case BARD -> "bard";
            case CLERIC -> "cleric";
            case DRUID -> "druid";
            case FIGHTER -> "fighter";
            case MONK -> "monk";
            case PALADIN -> "paladin";
            case RANGER -> "ranger";
            case ROGUE -> "rogue";
            case SORCERER -> "sorcerer";
            case WARLOCK -> "warlock";
            case WIZARD -> "wizard";
        };
    }
    @Nullable
    public static PrimaryClass fromString(String className) {
        for(PrimaryClass primaryClass : PrimaryClass.values()) {
            if (className.equals(primaryClass.toString())) return primaryClass;
        }
        return null;
    }
}

