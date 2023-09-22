package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public record SkillBook(String skill, SkillBookEffect effect, RegistryObject<Item> registryObject) {
    public static String buildKey(SkillBookEffect effect, String skill) {
        return effect.toString() + "_" + skill;
    }
}
