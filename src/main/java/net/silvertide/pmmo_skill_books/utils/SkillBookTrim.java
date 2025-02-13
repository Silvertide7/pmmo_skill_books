package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookTrim {
    PLAIN(0),
    GOLD(1),
    EMERALD(2),
    DIAMOND(3);

    private int id;

    SkillBookTrim(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return switch (this) {
            case PLAIN -> "";
            case GOLD -> "gold";
            case EMERALD -> "emerald";
            case DIAMOND -> "diamond";
        };
    }
}
