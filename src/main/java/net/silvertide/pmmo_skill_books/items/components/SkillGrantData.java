package net.silvertide.pmmo_skill_books.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.Color;
import net.silvertide.pmmo_skill_books.data.Rank;
import net.silvertide.pmmo_skill_books.data.TextureType;
import net.silvertide.pmmo_skill_books.utils.GUIUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record SkillGrantData(String name, List<String> skills, String applicationType, Long applicationValue, int experienceCost, String textureType, String rank, String color) {
    public static final Codec<SkillGrantData> CODEC;
    public static final StreamCodec<FriendlyByteBuf, SkillGrantData> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.STRING.fieldOf("name").forGetter(SkillGrantData::name),
                        Codec.list(Codec.STRING).fieldOf("skills").forGetter(SkillGrantData::skills),
                        Codec.STRING.fieldOf("application_type").forGetter(SkillGrantData::applicationType),
                        Codec.LONG.fieldOf("application_value").forGetter(SkillGrantData::applicationValue),
                        Codec.INT.fieldOf("experience_cost").forGetter(SkillGrantData::experienceCost),
                        Codec.STRING.fieldOf("texture_type").forGetter(SkillGrantData::textureType),
                        Codec.STRING.fieldOf("rank").forGetter(SkillGrantData::rank),
                        Codec.STRING.fieldOf("color").forGetter(SkillGrantData::color))
                .apply(instance, SkillGrantData::new));

        STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull SkillGrantData decode(@NotNull FriendlyByteBuf buf) {
                String name = buf.readUtf();
                List<String> skillsFromBuf = new ArrayList<>();
                int numSkills = buf.readVarInt();

                for(int i = 0; i < numSkills; i++) {
                    skillsFromBuf.add(buf.readUtf());
                }

                return new SkillGrantData(name, skillsFromBuf, buf.readUtf(), buf.readLong(), buf.readInt(), buf.readUtf(), buf.readUtf(), buf.readUtf());
            }
            @Override
            public void encode(FriendlyByteBuf buf, SkillGrantData insigniaData) {
                buf.writeUtf(insigniaData.name());

                buf.writeVarInt(insigniaData.skills().size());
                for(String skill : insigniaData.skills()) {
                    buf.writeUtf(skill);
                }

                buf.writeUtf(insigniaData.applicationType());
                buf.writeLong(insigniaData.applicationValue());
                buf.writeInt(insigniaData.experienceCost());
                buf.writeUtf(insigniaData.textureType());
                buf.writeUtf(insigniaData.rank());
                buf.writeUtf(insigniaData.color());
            }
        };
    }

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
            return Color.valueOf(rank().toUpperCase());
        } catch(IllegalArgumentException exception){
            return Color.RED;
        }
    }

    public Rank getRank() {
        try {
            return Rank.valueOf(color().toUpperCase());
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
