package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookColor {
    BLUE,
    BLACK;

    @Override
    public String toString() {
        return switch (this) {
            case BLUE -> "blue";
            case BLACK -> "black";
        };
    }
}
