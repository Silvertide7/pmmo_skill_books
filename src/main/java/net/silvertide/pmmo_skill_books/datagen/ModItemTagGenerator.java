package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends TagsProvider {
    protected ModItemTagGenerator(PackOutput packOutput, ResourceKey resourceKey, CompletableFuture completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, resourceKey, completableFuture, PMMOSkillBooks.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
