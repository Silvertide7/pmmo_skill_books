package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookColor {
    GREEN(0),
    BLUE(1),
    PURPLE(2),
    BLACK(3);

    private int id;

    SkillBookColor(int id) {
        this.id = id;
    }

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
