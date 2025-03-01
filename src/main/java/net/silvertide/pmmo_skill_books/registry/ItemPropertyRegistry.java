package net.silvertide.pmmo_skill_books.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.data.TextureType;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;

public class ItemPropertyRegistry {
    public static void addCustomItemProperties() {
        ItemProperties.register(ItemRegistry.SKILL_GRANT.get(), PMMOSkillBooks.id("configuration"),
                (stack, level, entity, seed) -> DataComponentUtil.getSkillGrantData(stack).map(skillGrantData -> {
                    float configuration = 0f;

                    // Default is SKILL_BOOK
                    if (skillGrantData.getTextureType() != null && skillGrantData.getTextureType() == TextureType.INSIGNIA) {
                        configuration += 100;
                    }

                    switch(skillGrantData.getColor()) {
                        case TEAL -> configuration += 1;
                        case ORANGE -> configuration += 2;
                        case LIGHT_PURPLE -> configuration += 3;
                        case WHITE -> configuration += 4;
                        case GREEN -> configuration += 5;
                        case RED -> configuration += 6;
                        case BLUE -> configuration += 7;
                        case YELLOW -> configuration += 8;
                        case LIGHT_GREEN -> configuration += 9;
                        case BLACK -> configuration += 10;
                        case PINK -> configuration += 11;
                        case PURPLE -> configuration += 12;
                        case LIGHT_BLUE -> configuration += 13;
                    }

                    // Default is PLAIN
                    switch(skillGrantData.getRank()) {
                        case IRON -> configuration += 20;
                        case GOLD -> configuration += 40;
                        case EMERALD -> configuration += 60;
                        case DIAMOND -> configuration += 80;
                    }
                    return configuration;
                }).orElse(0f));
    }
}
