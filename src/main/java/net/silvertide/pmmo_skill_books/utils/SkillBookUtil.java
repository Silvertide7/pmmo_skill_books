package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.items.components.SkillBookData;

public final class SkillBookUtil {
    private SkillBookUtil() {}

    public static UseSkillBookResult canPlayerUseSkillBook(ServerPlayer player, ItemStack stack) {
        return DataComponentUtil.getSkillBookData(stack).map(skillBookData -> {
            if(StringUtil.isBlank(skillBookData.skill())){
                return new UseSkillBookResult(false, "pmmo_skill_books.message.no_skill_specified");
            }

            if(skillBookData.applicationValue() <= 0) {
                return new UseSkillBookResult(false, "pmmo_skill_books.message.value_zero_or_less");
            }

            if(StringUtil.isBlank(skillBookData.applicationType())) {
                return new UseSkillBookResult(false, "pmmo_skill_books.message.no_application_type");
            }

            try {
                ApplicationType.valueOf(skillBookData.applicationType().toUpperCase());
            } catch(IllegalArgumentException ex) {
                return new UseSkillBookResult(false, "pmmo_skill_books.message.wrong_application_type");
            }

            if (PMMOUtil.isPlayerAtMaxLevel(player, skillBookData.skill())) {
                return new UseSkillBookResult(false, "pmmo_skill_books.message.max_level");
            }
            return new UseSkillBookResult(true, "");
        }).orElse(new UseSkillBookResult(false, "pmmo_skill_books.message.no_data_found"));

    }

    public static String getSkillBookEffectTranslationKey(SkillBookData skillBookData) {
        try {
            String translateKey = "pmmo_skill_books.message.experience_effect";
            if(skillBookData.getApplicationType().equals(ApplicationType.LEVEL)) {
                translateKey = skillBookData.applicationValue() > 1 ? "pmmo_skill_books.message.levels_effect" : "pmmo_skill_books.message.level_effect";
            }
            return translateKey;
        } catch (IllegalArgumentException ignored) {
            return "pmmo_skill_books.message.wrong_effect";
        }
    }
}
