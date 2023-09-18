package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.utils.SkillBook;
import net.silvertide.pmmo_skill_books.utils.SkillBookColor;
import net.silvertide.pmmo_skill_books.utils.SkillBookEffect;
import net.silvertide.pmmo_skill_books.utils.SkillBookTrim;
import net.silvertide.pmmo_skill_books.items.ModItems;

import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PMMOSkillBooks.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        List<SkillBook> skillBookItems = ModItems.getModItems().getSkillBookItems();
        skillBookItems.forEach(skillBook -> {
            skillBookItem(skillBook.registryObject(), getSkillBookColor(skillBook.effect()), getSkillBookTrim(skillBook.effect()));
        });
    }

    private SkillBookTrim getSkillBookTrim(SkillBookEffect effect) {
        return switch(effect){
            case ADD_XP_5000, ADD_LEVEL_1, SET_LEVEL_1, COMMAND_1 -> SkillBookTrim.PLAIN;
            case ADD_XP_10000, ADD_LEVEL_3, SET_LEVEL_2, COMMAND_2 -> SkillBookTrim.GOLD;
            case ADD_XP_25000, ADD_LEVEL_5, SET_LEVEL_3, COMMAND_3 -> SkillBookTrim.EMERALD;
            case ADD_XP_50000, ADD_LEVEL_10, SET_LEVEL_4, COMMAND_4 -> SkillBookTrim.DIAMOND;
        };
    }

    private SkillBookColor getSkillBookColor(SkillBookEffect effect){
        return switch(effect){
            case ADD_XP_5000, ADD_XP_10000, ADD_XP_25000, ADD_XP_50000 -> SkillBookColor.GREEN;
            case ADD_LEVEL_1, ADD_LEVEL_3, ADD_LEVEL_5, ADD_LEVEL_10 -> SkillBookColor.PURPLE;
            case SET_LEVEL_1, SET_LEVEL_2, SET_LEVEL_3, SET_LEVEL_4 -> SkillBookColor.BLACK;
            case COMMAND_1, COMMAND_2, COMMAND_3, COMMAND_4 -> SkillBookColor.BLUE;
        };
    }

    private ItemModelBuilder skillBookItem(RegistryObject<Item> item, SkillBookColor color, SkillBookTrim trim) {
        String texturePath = "skill_book_" + color;
        if (trim != SkillBookTrim.PLAIN) {
            texturePath = texturePath + "_" + trim;
        }
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(PMMOSkillBooks.MOD_ID, "item/" + texturePath));
    }
}
