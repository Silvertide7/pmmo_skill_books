package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public record SkillBook(String skill, SkillBookEffect effect, DeferredHolder<Item, Item> registryObject) {
    public static String buildKey(SkillBookEffect effect, String skill) {
        return effect.toString() + "_" + skill;
    }
}
