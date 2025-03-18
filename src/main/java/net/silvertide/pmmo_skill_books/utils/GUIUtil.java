package net.silvertide.pmmo_skill_books.utils;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public final class GUIUtil {
    private static final Random SOUND_RNG = new Random();
    private GUIUtil() {}
    public static boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return isHovering(0, 0, pX, pY, pWidth, pHeight, pMouseX, pMouseY);
    }
    public static boolean isHovering(int i, int j, int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        pMouseX -= i;
        pMouseY -= j;
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    public static int getClassRankHorizOffset(int level) {
        return (Math.min(4, level) - 1) * 22;

    }

    public static void drawScaledString(GuiGraphics guiGraphics, float textScale, Font font, String text, int textX, int textY, int color) {
        if(text == null || textScale == 0.0F || "".equals(text)) return;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(textScale, textScale, textScale);

        int scaledTextX = (int) (textX / textScale);
        int scaledTextY = (int) (textY / textScale);
        guiGraphics.drawString(font, text, scaledTextX, scaledTextY, color);

        guiGraphics.pose().popPose();
    }

    public static String trimTextToWidth(String text, Font font, int width) {
        String result = text;
        while(font.width(result) > width) {
            float ratio = (float) width / font.width(result);
            int stringLength = (int) (result.length() * ratio);
            result = result.substring(0, stringLength - 3);
            result += "...";
        }
        return result;
    }

    public static void drawScaledWordWrap(GuiGraphics guiGraphics, float textScale, Font font, Component buttonTextComp, int textX, int textY, int lineWidth, int color) {
        if("".equals(buttonTextComp.getString())) return;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(textScale, textScale, textScale);

        int scaledTextX = (int) (textX / textScale);
        int scaledTextY = (int) (textY / textScale);
        guiGraphics.drawWordWrap(font, buttonTextComp, scaledTextX, scaledTextY, (int) (lineWidth / textScale), color);

        guiGraphics.pose().popPose();
    }

    public static void drawScaledCenteredWordWrap(GuiGraphics guiGraphics, float textScale, Font font, Component buttonTextComp, int textX, int textY, int lineWidth, int color) {
        if("".equals(buttonTextComp.getString())) return;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(textScale, textScale, textScale);

        float fontWidth = font.width(buttonTextComp) * textScale;
        float fontHeight = font.lineHeight * textScale;

        float xOffset = fontWidth < lineWidth ? fontWidth : lineWidth;

        int scaledTextX = (int) ((textX - xOffset / 2) / textScale);
        int scaledTextY = (int) ((textY - fontHeight / 3) / textScale);
        guiGraphics.drawWordWrap(font, buttonTextComp, scaledTextX, scaledTextY, (int) (lineWidth / textScale), color);

        guiGraphics.pose().popPose();
    }


    public static void drawLeftAlignedScaledWordWrap(GuiGraphics guiGraphics, float textScale, Font font, Component buttonTextComp, int textX, int textY, int lineWidth, int color) {
        if("".equals(buttonTextComp.getString())) return;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(textScale, textScale, textScale);

        float fontWidth = font.width(buttonTextComp) * textScale;

        int scaledTextX = (int) ((textX - fontWidth) / textScale);
        int scaledTextY = (int) ((textY) / textScale);
        guiGraphics.drawWordWrap(font, buttonTextComp, scaledTextX, scaledTextY, (int) (lineWidth / textScale), color);

        guiGraphics.pose().popPose();
    }

    public static String prettifyEnumCaps(Enum<?> enumValue) {
        if (enumValue == null) {
            return "";
        }
        return enumValue.name().replace("_", " ");
    }

    public static String prettifyEnum(Enum<?> enumValue) {
        if (enumValue == null) {
            return "";
        }
        String[] words = enumValue.name().toLowerCase().split("_");
        return Arrays.stream(words)
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));

    }

    public static String getTranslatedSkillString(String skill) {
        Component translated = Component.translatable("pmmo." + skill);
        return translated.getString();
    }

    public static String prettifyName(String resourceLocation) {
        String prettifiedLocation = resourceLocation;
        // Split the path by modid:moditem
        if(prettifiedLocation.contains(":")) {
            prettifiedLocation = resourceLocation.split(":")[1];
        }

        // Added this to deal with attributes like general.movement_speed
        if(prettifiedLocation.contains(".")) {
            String[] periodSeperated = prettifiedLocation.split("\\.");
            prettifiedLocation = periodSeperated[periodSeperated.length - 1];
        }

        // Separate into multiple words
        if(prettifiedLocation.contains("_")) {
            StringBuilder result = new StringBuilder();
            String[] words = prettifiedLocation.split("_");
            for (int i = 0; i < words.length; i++) {
                // Capitalize the first letter and make the rest lowercase
                String capitalizedWord = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
                // Append the capitalized word to the result
                result.append(capitalizedWord);
                // Add a space if this is not the last word
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
            prettifiedLocation = result.toString();
        } else if(!prettifiedLocation.contains(" ")) {
            prettifiedLocation = prettifiedLocation.substring(0, 1).toUpperCase() + prettifiedLocation.substring(1).toLowerCase();
        }

        return prettifiedLocation;
    }

    public static void spawnParticals(ServerLevel serverLevel, Player player, ParticleOptions particle, int numParticles){
        Level level = player.level();
        for(int i = 0; i < numParticles; i++){
            serverLevel.sendParticles(particle, player.getX() + level.random.nextDouble() - 0.5, player.getY() + 1.0, player.getZ() + level.random.nextDouble() - 0.5, 1, 0.0D, 0.0D, 0.0D, 1.0D);
        }
    }

    public static void playSound(Level level, double x, double y, double z, SoundEvent soundEvent){
        level.playSound(null, x, y, z, soundEvent, SoundSource.PLAYERS, 20, 0.95f+SOUND_RNG.nextFloat()*0.1f);
    }

    public static void playSound(Level level, Player player, SoundEvent soundEvent){
        playSound(level, player.getX(), player.getY(), player.getZ(), soundEvent);
    }

}
