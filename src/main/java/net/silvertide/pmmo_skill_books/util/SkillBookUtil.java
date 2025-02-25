package net.silvertide.pmmo_skill_books.util;

import io.netty.util.internal.StringUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.items.components.SkillBookData;

import java.util.Optional;

public class SkillBookUtil {
    private static final String SKILL_NBT_KEY = "skill";
    private static final String APPLICATION_TYPE_NBT_KEY = "application_type";
    private static final String APPLICATION_VALUE_NBT_KEY = "application_value";
    private static final String BOOK_COLOR_NBT_KEY = "book_color";
    private static final String TRIM_COLOR_NBT_KEY = "trim_color";

    public static Optional<SkillBookData> getSkillBookData(ItemStack stack) {
        return StackNBTUtil.getSkillBookNBT(stack).flatMap(stackNBT -> {
            String skill = stackNBT.getString(SKILL_NBT_KEY);
            String applicationType = stackNBT.getString(APPLICATION_TYPE_NBT_KEY);
            long applicationValue = stackNBT.getLong(APPLICATION_VALUE_NBT_KEY);
            String bookColor = stackNBT.getString(BOOK_COLOR_NBT_KEY);
            String trimColor = stackNBT.getString(TRIM_COLOR_NBT_KEY);

            if(StringUtil.isNullOrEmpty(skill) || StringUtil.isNullOrEmpty(applicationType) || applicationValue == 0L) {
                return Optional.empty();
            }

            return Optional.of(new SkillBookData(skill, applicationType, applicationValue, bookColor, trimColor));
        });
    }

    public static void putSkillBookData(ItemStack stack, SkillBookData skillBookData) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(SKILL_NBT_KEY, skillBookData.skill());
        compoundTag.putString(APPLICATION_TYPE_NBT_KEY, skillBookData.applicationType());
        compoundTag.putLong(APPLICATION_VALUE_NBT_KEY, skillBookData.applicationValue());
        compoundTag.putString(BOOK_COLOR_NBT_KEY, skillBookData.bookColor());
        compoundTag.putString(TRIM_COLOR_NBT_KEY, skillBookData.trimColor());
        StackNBTUtil.putSkillBookNBT(stack, compoundTag);
    }

    public static UseSkillBookResult canPlayerUseSkillBook(ServerPlayer player, ItemStack stack) {
        return getSkillBookData(stack).map(skillBookData -> {
            if(StringUtil.isNullOrEmpty(skillBookData.skill())){
                return new UseSkillBookResult(false, "pmmo_skill_books.message.no_skill_specified");
            }

            if(skillBookData.applicationValue() <= 0) {
                return new UseSkillBookResult(false, "pmmo_skill_books.message.value_zero_or_less");
            }

            if(StringUtil.isNullOrEmpty(skillBookData.applicationType())) {
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

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
