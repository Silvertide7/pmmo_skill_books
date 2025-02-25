package net.silvertide.pmmo_skill_books.items.components;

import net.minecraft.network.chat.Component;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.SkillBookColor;
import net.silvertide.pmmo_skill_books.data.SkillBookTrim;

import java.util.Objects;

public record SkillBookData(String skill, String applicationType, Long applicationValue, String bookColor, String trimColor) {

    public ApplicationType getApplicationType() throws IllegalArgumentException {
        return ApplicationType.valueOf(applicationType().toUpperCase());
    }

    public String getSkillName() {
        return Component.translatable("pmmo." + skill()).getString();
    }

    public SkillBookColor getColor() {
        try {
            return SkillBookColor.valueOf(bookColor().toUpperCase());
        } catch(IllegalArgumentException exception){
            return SkillBookColor.BLUE;
        }
    }

    public SkillBookTrim getTrim() {
        try {
            return SkillBookTrim.valueOf(trimColor().toUpperCase());
        } catch(IllegalArgumentException exception){
            return SkillBookTrim.PLAIN;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillBookData other = (SkillBookData) o;
        return Objects.equals(skill(), other.skill())
                && Objects.equals(applicationType(), other.applicationType())
                && Objects.equals(applicationValue(), other.applicationValue())
                && Objects.equals(bookColor(), other.bookColor())
                && Objects.equals(trimColor(), other.trimColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill(), applicationType(), applicationValue(), bookColor(), trimColor());
    }
}