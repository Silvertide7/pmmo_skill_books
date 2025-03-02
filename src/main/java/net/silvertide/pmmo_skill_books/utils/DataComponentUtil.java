package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.registry.DataComponentRegistry;

import java.util.List;
import java.util.Optional;

public final class DataComponentUtil {
    private DataComponentUtil() {}

    public static Optional<SkillGrantData> getSkillGrantData(ItemStack stack) {
        return Optional.ofNullable(stack.get(DataComponentRegistry.SKILL_GRANT_DATA));
    }

    public static void setSkillGrantData(ItemStack stack, SkillGrantData
            attunementData) {
        stack.set(DataComponentRegistry.SKILL_GRANT_DATA, attunementData);
    }

    public static void addSkillGrantData(ItemStack stack, String name, List<String> skills, String applicationType, Long value, int experienceCost, String textureType, String bookColor, String trimColor) {
        SkillGrantData data = new SkillGrantData(name, skills, applicationType, value, experienceCost, textureType, bookColor, trimColor);
        setSkillGrantData(stack, data);
    }
}