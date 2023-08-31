package net.silvertide.pmmo_skill_books.util;

public enum SkillBookColor {
    GREEN,
    BLUE,
    PURPLE,
    BLACK;

    @Override
    public String toString() {
        return switch (this) {
            case GREEN -> "green";
            case BLUE -> "blue";
            case PURPLE -> "purple";
            case BLACK -> "black";
        };
    }
}
