package net.silvertide.pmmo_skill_books.data;

import net.minecraft.network.chat.MutableComponent;

public record UseSkillGrantResult(boolean success, MutableComponent message) {}
