package net.silvertide.pmmo_skill_books.utils;

public enum SkillBookTrim {
    PLAIN,
    GOLD,
    EMERALD,
    DIAMOND;

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
