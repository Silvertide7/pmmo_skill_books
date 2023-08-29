package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PMMOSkillBooks.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        skillBookItem(ModItems.SKILL_BOOK);
        skillBookItem(ModItems.SKILL_BOOK_2);
        skillBookItem(ModItems.SKILL_BOOK_3);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
            new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(PMMOSkillBooks.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder skillBookItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(PMMOSkillBooks.MOD_ID, "item/skill_book"));
    }
}
