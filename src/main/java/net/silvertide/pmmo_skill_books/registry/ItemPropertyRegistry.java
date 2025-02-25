package net.silvertide.pmmo_skill_books.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.util.SkillBookUtil;

public class ItemPropertyRegistry {
    public static void addCustomItemProperties() {
        ItemProperties.register(ItemRegistry.SKILL_BOOK.get(), PMMOSkillBooks.id("configuration"),
                (stack, level, entity, seed) -> SkillBookUtil.getSkillBookData(stack).map(skillBookData -> {
                    float configuration = 1f;

                    switch(skillBookData.getColor()) {
                        case GREEN -> configuration += 10;
                        case PURPLE -> configuration += 20;
                        case BLACK -> configuration += 30;
                    }

                    switch(skillBookData.getTrim()) {
                        case GOLD -> configuration += 1;
                        case EMERALD -> configuration += 2;
                        case DIAMOND -> configuration += 3;
                    }
                    return configuration;
                }).orElse(1f));
    }
}
