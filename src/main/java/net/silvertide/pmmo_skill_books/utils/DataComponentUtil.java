package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.items.components.SkillBookData;
import net.silvertide.pmmo_skill_books.registry.DataComponentRegistry;

import java.util.Optional;

public final class DataComponentUtil {
    private DataComponentUtil() {}

    // ACCESS METHODS
    public static Optional<SkillBookData> getSkillBookData(ItemStack stack) {
        return Optional.ofNullable(stack.get(DataComponentRegistry.SKILL_BOOK_DATA));
    }

    public static void setSkillBookData(ItemStack stack, SkillBookData
            attunementData) {
        stack.set(DataComponentRegistry.SKILL_BOOK_DATA, attunementData);
    }

    public static void addSkillBookData(ItemStack stack, String skill, String applicationType, Long value, String bookColor, String trimColor) {
        SkillBookData data = new SkillBookData(skill, applicationType, value, bookColor, trimColor);
        setSkillBookData(stack, data);
    }
}