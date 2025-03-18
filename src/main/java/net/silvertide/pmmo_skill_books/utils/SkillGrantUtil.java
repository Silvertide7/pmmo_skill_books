package net.silvertide.pmmo_skill_books.utils;

import io.netty.util.internal.StringUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.data.ApplicationType;
import net.silvertide.pmmo_skill_books.data.UseSkillGrantResult;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillGrantUtil {
    private static final String NAME_NBT_KEY = "name";
    private static final String SKILLS_NBT_KEY = "skills";
    private static final String APPLICATION_TYPE_NBT_KEY = "application_type";
    private static final String APPLICATION_VALUE_NBT_KEY = "application_value";
    private static final String EXPERIENCE_COST_NBT_KEY = "experience_cost";
    private static final String TEXTURE_TYPE_NBT_KEY = "texture_type";
    private static final String RANK_NBT_KEY = "rank";
    private static final String COLOR_NBT_KEY = "color";

    public static Optional<SkillGrantData> getSkillGrantData(ItemStack stack) {
        return StackNBTUtil.getSkillBookNBT(stack).flatMap(stackNBT -> {
            List<String> skillList = new ArrayList<>();
            String name = stackNBT.getString(NAME_NBT_KEY);

            // Get a LisTag with string tags
            ListTag listTag = stackNBT.getList(SKILLS_NBT_KEY, 8);
            if(listTag.isEmpty()) {
                return Optional.empty();
            }

            for (int i = 0; i < listTag.size(); i++) {
                skillList.add(listTag.getString(i));
            }

            String applicationType = stackNBT.getString(APPLICATION_TYPE_NBT_KEY);
            long applicationValue = stackNBT.getLong(APPLICATION_VALUE_NBT_KEY);
            int experienceCost = stackNBT.getInt(EXPERIENCE_COST_NBT_KEY);
            String textureType = stackNBT.getString(TEXTURE_TYPE_NBT_KEY);
            String rank = stackNBT.getString(RANK_NBT_KEY);
            String color = stackNBT.getString(COLOR_NBT_KEY);

            if(skillList.isEmpty() || StringUtil.isNullOrEmpty(applicationType) || applicationValue == 0L) {
                return Optional.empty();
            }

            return Optional.of(new SkillGrantData(name, skillList, applicationType, applicationValue,experienceCost, textureType, rank, color));
        });
    }

    public static void putSkillGrantData(ItemStack stack, SkillGrantData skillGrantData) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(NAME_NBT_KEY, skillGrantData.name());

        ListTag skillListTag = new ListTag();
        for(String skill : skillGrantData.skills()) {
            skillListTag.add(StringTag.valueOf(skill));
        }
        compoundTag.put(SKILLS_NBT_KEY, skillListTag);

        compoundTag.putString(APPLICATION_TYPE_NBT_KEY, skillGrantData.applicationType());
        compoundTag.putLong(APPLICATION_VALUE_NBT_KEY, skillGrantData.applicationValue());
        compoundTag.putInt(EXPERIENCE_COST_NBT_KEY, skillGrantData.experienceCost());
        compoundTag.putString(TEXTURE_TYPE_NBT_KEY, skillGrantData.textureType());
        compoundTag.putString(RANK_NBT_KEY, skillGrantData.rank());
        compoundTag.putString(COLOR_NBT_KEY, skillGrantData.color());
        StackNBTUtil.putSkillBookNBT(stack, compoundTag);
    }

    public static UseSkillGrantResult canPlayerUseSkillBook(ServerPlayer player, ItemStack stack) {
        return SkillGrantUtil.getSkillGrantData(stack).map(skillGrantData -> {
            if(skillGrantData.skills().isEmpty()) {
                return new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.no_skill_specified"));
            }

            if(skillGrantData.applicationValue() <= 0) {
                return new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.value_zero_or_less"));
            }

            if(StringUtil.isNullOrEmpty(skillGrantData.applicationType())) {
                return new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.no_application_type"));
            }

            try {
                ApplicationType.valueOf(skillGrantData.applicationType().toUpperCase());
            } catch(IllegalArgumentException ex) {
                return new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.wrong_application_type"));
            }

            if(!player.getAbilities().instabuild && skillGrantData.experienceCost() > 0 && player.experienceLevel < skillGrantData.experienceCost()) {
                return new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.not_enough_experience", skillGrantData.experienceCost()));
            }

            return new UseSkillGrantResult(true, Component.empty());
        }).orElse(new UseSkillGrantResult(false, Component.translatable("pmmo_skill_books.message.no_data_found")));

    }

    public static String getSkillBookEffectTranslationKey(ApplicationType applicationType, Long applicationValue) {
        try {
            String translateKey = "pmmo_skill_books.message.experience_effect";
            if(ApplicationType.LEVEL.equals(applicationType)) {
                translateKey = applicationValue > 1 ? "pmmo_skill_books.message.levels_effect" : "pmmo_skill_books.message.level_effect";
            } else if(ApplicationType.SET.equals(applicationType)) {
                translateKey = "pmmo_skill_books.message.set_effect";
            }
            return translateKey;
        } catch (IllegalArgumentException ignored) {
            return "pmmo_skill_books.message.wrong_effect";
        }
    }
}
