package net.silvertide.pmmo_skill_books.utils;

public enum PlayerSubClassType {
    VALKYRIE;


    @Override
    public String toString() {
        return switch (this) {
            case VALKYRIE -> "valkyrie";
        };
    }
}

