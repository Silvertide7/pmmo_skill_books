package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;

import java.util.UUID;

public final class StackNBTUtil {
    private static final String ITEM_ATTUNEMENT_UUID_NBT_KEY = "attunement_uuid";
    private static final String ATTUNED_TO_UUID_NBT_KEY = "attuned_to_uuid";
    private static final String ATTUNED_TO_NAME_NBT_KEY = "attuned_to_name";
    private static final String MODIFICATION_SOULBOUND_NBT_KEY = "soulbound";
    private static final String MODIFICATION_INVULNERABLE_NBT_KEY = "invulnerable";
    private static final String MODIFICATION_UNBREAKABLE_NBT_KEY = "unbreakable";
    private static final String ATTRIBUTE_MODIFICATION_NBT_KEY = "attribute_modifications";



    // ATTUNEMENT TAG FUNCTIONS
    public static void putPlayerDataInArtifactoryTag(Player player, ItemStack stack) {
        setAttunedToUUID(stack, player.getUUID());
        setAttunedToName(stack, player.getDisplayName().getString());
    }

    public static void setItemAttunementUUID(ItemStack stack, UUID attunementUUID) {
        setArtifactoryUUID(stack, ITEM_ATTUNEMENT_UUID_NBT_KEY, attunementUUID);
    }

    public static void setSoulbound(ItemStack stack) {
        setArtifactoryBoolean(stack, MODIFICATION_SOULBOUND_NBT_KEY, true);
    }

    public static boolean isSoulbound(ItemStack stack){
        return artifactoryNBTContainsTag(stack, MODIFICATION_SOULBOUND_NBT_KEY) && getArtifactoryBoolean(stack, MODIFICATION_SOULBOUND_NBT_KEY);
    }

    public static void removeSoulbound(ItemStack stack) {
        removeArtifactoryTag(stack, MODIFICATION_SOULBOUND_NBT_KEY);
    }

    public static void setInvulnerable(ItemStack stack) {
        setArtifactoryBoolean(stack, MODIFICATION_INVULNERABLE_NBT_KEY, true);
    }

    public static void removeInvulnerable(ItemStack stack) {
        removeArtifactoryTag(stack, MODIFICATION_INVULNERABLE_NBT_KEY);
    }

    public static boolean isInvulnerable(ItemStack stack){
        return artifactoryNBTContainsTag(stack, MODIFICATION_INVULNERABLE_NBT_KEY) && getArtifactoryBoolean(stack, MODIFICATION_INVULNERABLE_NBT_KEY);
    }

    public static void removeArtifactoryUnbreakable(ItemStack stack) {
        removeArtifactoryTag(stack, MODIFICATION_UNBREAKABLE_NBT_KEY);
    }

    public static boolean containsAttunedToUUID(ItemStack stack) {
        return artifactoryNBTContainsTag(stack, ATTUNED_TO_UUID_NBT_KEY);
    }

    public static boolean containsItemAttunementUUID(ItemStack stack) {
        return artifactoryNBTContainsTag(stack, ITEM_ATTUNEMENT_UUID_NBT_KEY);
    }

    public static void setAttunedToUUID(ItemStack stack, UUID attunedToUUID) {
        setArtifactoryUUID(stack, ATTUNED_TO_UUID_NBT_KEY, attunedToUUID);
    }

    public static void setAttunedToName(ItemStack stack, String name) {
        setArtifactoryString(stack, ATTUNED_TO_NAME_NBT_KEY, name);
    }


    // Artifactory Tag Methods
    private static String getArtifactoryString(ItemStack stack, String tag) {
        return getOrCreateSkillBookNBT(stack).getString(tag);
    }

    private static void setArtifactoryString(ItemStack stack, String tag, String value) {
        CompoundTag artifactoryNBT = getOrCreateSkillBookNBT(stack);
        artifactoryNBT.putString(tag, value);
    }

    private static boolean getArtifactoryBoolean(ItemStack stack, String tag) {
        return getOrCreateSkillBookNBT(stack).getBoolean(tag);
    }

    private static void setArtifactoryBoolean(ItemStack stack, String tag, boolean value) {
        CompoundTag artifactoryNBT = getOrCreateSkillBookNBT(stack);
        artifactoryNBT.putBoolean(tag, value);
    }

    private static UUID getArtifactoryUUID(ItemStack stack, String tag) {
        return getOrCreateSkillBookNBT(stack).getUUID(tag);
    }

    private static void setArtifactoryUUID(ItemStack stack, String tag, UUID value) {
        CompoundTag artifactoryNBT = getOrCreateSkillBookNBT(stack);
        artifactoryNBT.putUUID(tag, value);
    }

    private static void removeArtifactoryTag(ItemStack stack, String tag) {
        CompoundTag artifactoryNBT = getOrCreateSkillBookNBT(stack);
        if(artifactoryNBT.contains(tag)) artifactoryNBT.remove(tag);
    }

    // Modification NBT Methods
    public static void addAttributeModificaftionTag(ItemStack stack, UUID attributeUUID, CompoundTag attributeModificationTag) {
        CompoundTag attributeModificationsCompoundTag = getOrCreateAttributeModificationNBT(stack);
        attributeModificationsCompoundTag.put(attributeUUID.toString(), attributeModificationTag);
    }

    public static CompoundTag getOrCreateAttributeModificationNBT(ItemStack stack) {
        CompoundTag artifactoryNBT = getOrCreateSkillBookNBT(stack);
        if(!artifactoryNBT.contains(ATTRIBUTE_MODIFICATION_NBT_KEY)) {
            artifactoryNBT.put(ATTRIBUTE_MODIFICATION_NBT_KEY, new CompoundTag());
        }
        return artifactoryNBT.getCompound(ATTRIBUTE_MODIFICATION_NBT_KEY);
    }

    public static void removeAttributeModifications(ItemStack stack) {
        removeArtifactoryTag(stack, ATTRIBUTE_MODIFICATION_NBT_KEY);
    }


    // Artifactory NBT Methods
    public static CompoundTag getOrCreateSkillBookNBT(ItemStack stack) {
        if(stackContainsTag(stack, PMMOSkillBooks.MOD_ID)) return stack.getOrCreateTag().getCompound(PMMOSkillBooks.MOD_ID);

        CompoundTag skillBookTag = new CompoundTag();

        replaceArtifactoryNBT(stack, skillBookTag);
        return skillBookTag;
    }
    private static boolean artifactoryNBTContainsTag(ItemStack stack, String tag) {
        if(!stackContainsTag(stack, PMMOSkillBooks.MOD_ID)) return false;
        return getOrCreateSkillBookNBT(stack).contains(tag);
    }

    private static void replaceArtifactoryNBT(ItemStack stack, CompoundTag artifactoryTag) {
        stack.getOrCreateTag().put(PMMOSkillBooks.MOD_ID, artifactoryTag);
    }

    // Helper Methods ---------
    private static boolean stackContainsTag(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(tag, Tag.TAG_COMPOUND);
    }
}