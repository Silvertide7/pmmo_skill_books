package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.items.components.SkillBookData;

public final class SkillBookUtil {
    public SkillBookUtil() {}

    public static UseSkillBookResult canPlayerUseSkillBook(ServerPlayer player, ItemStack stack) {
        return DataComponentUtil.getSkillBookData(stack).map(skillBookData -> {
            if(StringUtil.isBlank(skillBookData.skill())){
                return new UseSkillBookResult(false, "Skill book has no skill specified.");
            }

            if(skillBookData.applicationValue() <= 0) {
                return new UseSkillBookResult(false, "Skill value must be greater than 0.");
            }

            if(StringUtil.isBlank(skillBookData.applicationType())) {
                return new UseSkillBookResult(false, "Skill book has no application type [level, xp].");
            }

            try {
                ApplicationType.valueOf(skillBookData.applicationType().toUpperCase());
            } catch(IllegalArgumentException ex) {
                return new UseSkillBookResult(false, "Application type must be level or xp.");
            }

            if (PMMOUtil.isPlayerAtMaxLevel(player, skillBookData.skill())) {
                return new UseSkillBookResult(false, SkillBookUtil.capitalize(skillBookData.skill()) + " is at max level.");
            }
            return new UseSkillBookResult(true, "");
        }).orElse(new UseSkillBookResult(false, "No skill book data found"));

    }

    public static String getSkillBookEffectDescription(SkillBookData skillBookData) {
        try {
            String applicationType = "experience";
            if(skillBookData.getApplicationType().equals(ApplicationType.LEVEL)) {
                applicationType = skillBookData.applicationValue() > 1 ? "levels" : "level";
            }
            return "+" + skillBookData.applicationValue() + " " + capitalizeWord(skillBookData.skill()) + " " + capitalizeWord(applicationType);
        } catch (IllegalArgumentException ignored) {
            return "Invalid application type, must be level or xp";
        }
    }

    public static String capitalizeWord(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static final String[] PMMO_SKILLS = new String[] {
        "magic",
        "slayer",
        "fishing",
        "combat",
        "alchemy",
        "mining",
        "engineering",
        "endurance",
        "building",
        "smithing",
        "swimming",
        "woodcutting",
        "gunslinging",
        "crafting",
        "excavation",
        "farming",
        "flying",
        "cooking",
        "agility",
        "sailing",
        "hunter",
        "archery",
        "taming"
    };
}
