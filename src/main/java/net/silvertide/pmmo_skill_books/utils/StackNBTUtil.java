package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;

import java.util.Optional;

public final class StackNBTUtil {
    public static Optional<CompoundTag> getSkillBookNBT(ItemStack stack) {
        if(stackContainsTag(stack, PMMOSkillBooks.MOD_ID)) {
            return Optional.of(stack.getOrCreateTag().getCompound(PMMOSkillBooks.MOD_ID));
        }
        return Optional.empty();
    }

    public static void putSkillBookNBT(ItemStack stack, CompoundTag skillBookDataNBT) {
        stack.getOrCreateTag().put(PMMOSkillBooks.MOD_ID, skillBookDataNBT);
    }

    // Helper Methods ---------
    private static boolean stackContainsTag(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(tag, Tag.TAG_COMPOUND);
    }
}