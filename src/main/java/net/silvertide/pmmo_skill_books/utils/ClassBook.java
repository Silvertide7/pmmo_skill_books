package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public record ClassBook(IPlayerClass IPlayerClass, SkillBookEffect effect, RegistryObject<Item> registryObject) {
    public static String buildKey(SkillBookEffect effect, IPlayerClass IPlayerClass) {
        return effect.toString() + "_" + IPlayerClass.toString();
    }
}
