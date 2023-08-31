package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PMMOSkillBooks.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
//        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES).add(ModBlocks.ALEXANDRITE_ORE.get()).addTag(Tags.Blocks.ORES);
//
//        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
//                .add(ModBlocks.ALEXANDRITE_BLOCK.get(),
//                        ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),
//                        ModBlocks.ALEXANDRITE_ORE.get(),
//                        ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
//                        ModBlocks.END_STONE_ALEXANDRITE_ORE.get(),
//                        ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
//                        ModBlocks.SOUND_BLOCK.get());
//
//        this.tag(BlockTags.NEEDS_IRON_TOOL)
//                .add(ModBlocks.ALEXANDRITE_BLOCK.get(),
//                        ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),
//                        ModBlocks.ALEXANDRITE_ORE.get(),
//                        ModBlocks.SOUND_BLOCK.get());
//
//        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
//                .add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
//                        ModBlocks.END_STONE_ALEXANDRITE_ORE.get(),
//                        ModBlocks.NETHER_ALEXANDRITE_ORE.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
