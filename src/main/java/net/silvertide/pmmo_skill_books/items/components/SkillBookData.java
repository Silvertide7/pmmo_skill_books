package net.silvertide.pmmo_skill_books.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.SkillBookColor;
import net.silvertide.pmmo_skill_books.data.SkillBookTrim;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record SkillBookData(String skill, String applicationType, Long applicationValue, String bookColor, String trimColor) {
    public static final Codec<SkillBookData> CODEC;
    public static final StreamCodec<FriendlyByteBuf, SkillBookData> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("skill").forGetter(SkillBookData::skill),
                Codec.STRING.fieldOf("application_type").forGetter(SkillBookData::applicationType),
                Codec.LONG.fieldOf("application_value").forGetter(SkillBookData::applicationValue),
                Codec.STRING.fieldOf("book_color").forGetter(SkillBookData::bookColor),
                Codec.STRING.fieldOf("trim_color").forGetter(SkillBookData::trimColor))
            .apply(instance, SkillBookData::new));

        STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull SkillBookData decode(@NotNull FriendlyByteBuf buf) {
                return new SkillBookData(buf.readUtf(), buf.readUtf(), buf.readLong(), buf.readUtf(), buf.readUtf());
            }
            @Override
            public void encode(FriendlyByteBuf buf, SkillBookData skillBookData) {
                buf.writeUtf(skillBookData.skill());
                buf.writeUtf(skillBookData.applicationType());
                buf.writeLong(skillBookData.applicationValue());
                buf.writeUtf(skillBookData.bookColor());
                buf.writeUtf(skillBookData.trimColor());
            }
        };
    }

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
