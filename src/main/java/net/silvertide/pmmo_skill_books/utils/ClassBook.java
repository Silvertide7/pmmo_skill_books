package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public record ClassBook(PlayerClass playerClass, SkillBookEffect effect, RegistryObject<Item> registryObject) {
    public String getKey() {
        return effect.toString() + "_" + playerClass.toString();
    }

    public static String buildKey(SkillBookEffect effect, PlayerClass playerClass) {
        return effect.toString() + "_" + playerClass.toString();
    }
}
