package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, PMMOSkillBooks.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModItems.skillBookItems.forEach(skillBook -> {
            addItem(() -> skillBook.registryObject().get(), "Skill Book - " + SkillBookUtil.capitalize(skillBook.skill()));
        });

        add("creative_tab.skill_books", "PMMO Skill Books");
    }
}
