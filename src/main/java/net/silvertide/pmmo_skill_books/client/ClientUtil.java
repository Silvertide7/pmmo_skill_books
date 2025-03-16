package net.silvertide.pmmo_skill_books.client;

import net.minecraft.client.Minecraft;
import net.silvertide.pmmo_skill_books.gui.SkillGrantScreen;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;

public final class ClientUtil {
    private ClientUtil() {}

    public static void openSkillGrantScreen(SkillGrantData skillGrantData) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(new SkillGrantScreen(skillGrantData));
    }
}
