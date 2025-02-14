package net.silvertide.pmmo_skill_books.utils;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import net.minecraft.server.level.ServerPlayer;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;

public final class PMMOUtil {
    private PMMOUtil() {}

    public static boolean isPlayerAtMaxLevel(ServerPlayer player, String skill) {
        boolean result = false;
        try {
            result = APIUtils.getLevel(skill, player) >= Config.server().levels().maxLevel();
        } catch (IllegalArgumentException ignored) {
            PMMOSkillBooks.LOGGER.error("PMMOSkillBooks - isPlayerAtMaxLevel - IllegalArgumentException");
        }
        return result;
    }
}