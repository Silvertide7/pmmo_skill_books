package net.silvertide.pmmo_skill_books.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.ModItems;
import net.silvertide.pmmo_skill_books.utils.PrimaryClass;
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

        ModItems.customBookItems.forEach(skillBook -> {
            addItem(() -> skillBook.registryObject().get(), "Special Book");
        });

        ModItems.classBookItems.forEach(classBook -> {
            String className = classBook.playerClass().toString();
            String bookName = classBook.playerClass() instanceof PrimaryClass ? "Class Book - " : "Subclass Book - ";
            addItem(() -> classBook.registryObject().get(), bookName + SkillBookUtil.capitalize(className));
        });

        add("creative_tab.skill_books", "PMMO Skill Books");
    }
}
