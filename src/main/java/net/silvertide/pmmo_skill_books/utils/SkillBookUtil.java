package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillGrantResult;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;

public final class SkillBookUtil {
    private SkillBookUtil() {}

    public static UseSkillGrantResult canPlayerUseSkillBook(ServerPlayer player, ItemStack stack) {
        return DataComponentUtil.getSkillGrantData(stack).map(skillGrantData -> {
            if(skillGrantData.skills().isEmpty()) {
                return new UseSkillGrantResult(false, "pmmo_skill_books.message.no_skill_specified");
            }

            if(skillGrantData.applicationValue() <= 0) {
                return new UseSkillGrantResult(false, "pmmo_skill_books.message.value_zero_or_less");
            }

            if(StringUtil.isBlank(skillGrantData.applicationType())) {
                return new UseSkillGrantResult(false, "pmmo_skill_books.message.no_application_type");
            }

            try {
                ApplicationType.valueOf(skillGrantData.applicationType().toUpperCase());
            } catch(IllegalArgumentException ex) {
                return new UseSkillGrantResult(false, "pmmo_skill_books.message.wrong_application_type");
            }

            if(skillGrantData.experienceCost() > 0 && player.experienceLevel < skillGrantData.experienceCost()) {
                return new UseSkillGrantResult(false, "pmmo_skill_books.message.not_enough_experience");
            }

            return new UseSkillGrantResult(true, "");
        }).orElse(new UseSkillGrantResult(false, "pmmo_skill_books.message.no_data_found"));

    }

    public static String getSkillBookEffectTranslationKey(ApplicationType applicationType, Long applicationValue) {
        try {
            String translateKey = "pmmo_skill_books.message.experience_effect";
            if(ApplicationType.LEVEL.equals(applicationType)) {
                translateKey = applicationValue > 1 ? "pmmo_skill_books.message.levels_effect" : "pmmo_skill_books.message.level_effect";
            }
            return translateKey;
        } catch (IllegalArgumentException ignored) {
            return "pmmo_skill_books.message.wrong_effect";
        }
    }

//    public static UseInsigniaResult canPlayerUseInsignia(ServerPlayer player, ItemStack stack) {
//        return DataComponentUtil.getInsigniaData(stack).map(insigniaData -> {
//            if(insigniaData.skills().isEmpty()){
//                return new UseInsigniaResult(false, "pmmo_classes.message.no_skills_specified");
//            }
//
//            if(insigniaData.applicationValue() <= 0) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.value_zero_or_less");
//            }
//
//            if(StringUtil.isBlank(insigniaData.applicationType())) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.no_application_type");
//            }
//
//            try {
//                ApplicationType.valueOf(insigniaData.applicationType().toUpperCase());
//            } catch(IllegalArgumentException ex) {
//                return new UseInsigniaResult(false, "pmmo_classes.message.wrong_application_type");
//            }
//
//            return new UseInsigniaResult(true, "");
//        }).orElse(new UseInsigniaResult(false, "pmmo_classes.message.no_data_found"));
//
//    }
//
//    public static String getSkillBookEffectTranslationKey(InsigniaData insigniaData) {
//        try {
//            String translateKey = "pmmo_classes.message.set_effect";
//            if(insigniaData.getApplicationType().equals(ApplicationType.ADD)) {
//                translateKey = insigniaData.applicationValue() > 1 ? "pmmo_classes.message.add_levels_effect" : "pmmo_classes.message.add_level_effect";
//            }
//            return translateKey;
//        } catch (IllegalArgumentException ignored) {
//            return "pmmo_classes.message.wrong_effect";
//        }
//    }
}
