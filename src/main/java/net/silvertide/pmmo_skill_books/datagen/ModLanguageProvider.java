package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.ModItems;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;

import java.util.Arrays;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, PMMOSkillBooks.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModItems.getModItems().getSkillBookItems().forEach(skillBook -> {
            if(Arrays.stream(SkillBookUtil.getClassListAsStrings(true)).anyMatch(str -> str.equals(skillBook.skill()))){
                addItem(() -> skillBook.registryObject().get(), "Class Book");
            } else if(Arrays.stream(SkillBookUtil.SKILLS).anyMatch(str -> str.equals(skillBook.skill()))){
                addItem(() -> skillBook.registryObject().get(), "Skill Book");
            } else {
                addItem(() -> skillBook.registryObject().get(), "Special Book");
            }
        });

        add("creative_tab.skill_books", "PMMO Skill Books");
    }
}
