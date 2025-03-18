package net.silvertide.pmmo_skill_books.items.components;

import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.Color;
import net.silvertide.pmmo_skill_books.data.Rank;
import net.silvertide.pmmo_skill_books.data.TextureType;
import net.silvertide.pmmo_skill_books.utils.GUIUtil;

import java.util.List;
import java.util.Objects;

public record SkillGrantData(String name, List<String> skills, String applicationType, Long applicationValue, int experienceCost, String textureType, String rank, String color) {

    public String getSkillNames() {
        if(this.skills().size() == 1) {
            return GUIUtil.getTranslatedSkillString(this.skills().get(0));
        } else {
            StringBuilder result = new StringBuilder("[");
            for(int i = 0; i < this.skills().size(); i++) {
                result.append(GUIUtil.getTranslatedSkillString(this.skills().get(i)));

                if(i == this.skills().size() - 2) {
                    result.append(", or ");
                } else if (i != this.skills().size() - 1) {
                    result.append(", ");
                } else {

                }
            }
            result.append("]");
            return result.toString();
        }
    }

    public ApplicationType getApplicationType() throws IllegalArgumentException {
        return ApplicationType.valueOf(applicationType().toUpperCase());
    }

    public TextureType getTextureType() {
        try {
            return TextureType.valueOf(textureType().toUpperCase());
        } catch(IllegalArgumentException exception){
            return TextureType.SKILLBOOK;
        }
    }

    public Color getColor() {
        try {
            return Color.valueOf(color().toUpperCase());
        } catch(IllegalArgumentException exception){
            return Color.BLUE;
        }
    }

    public Rank getRank() {
        try {
            return Rank.valueOf(rank().toUpperCase());
        } catch(IllegalArgumentException exception){
            return Rank.PLAIN;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillGrantData other = (SkillGrantData) o;
        return Objects.equals(skills(), other.skills())
                && Objects.equals(name(), other.name())
                && Objects.equals(applicationType(), other.applicationType())
                && Objects.equals(applicationValue(), other.applicationValue())
                && Objects.equals(experienceCost(), other.experienceCost())
                && Objects.equals(rank(), other.rank())
                && Objects.equals(color(), other.color());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), skills(), applicationType(), applicationValue(), experienceCost(), rank(), color());
    }
}