package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.utils.*;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;

public class SetLevelSkillBookItem extends SkillBookItem {
    protected String skill;
    protected int levelToSet;
    public SetLevelSkillBookItem(Properties properties, String skill, int levelToSet) {
        super(properties);
        this.skill = skill;
        this.levelToSet = levelToSet;
    }

    @Override
    protected UseSkillBookResult playerCanUseSkillBook(Player player) {
        int currentLevel = APIUtils.getLevel(this.skill, player);
        if(currentLevel == this.levelToSet) {
            return new UseSkillBookResult(false, "You already have level " + this.levelToSet + " " + SkillBookUtil.capitalize(this.skill) + ".");
        } else if (currentLevel > this.levelToSet) {
            return new UseSkillBookResult(false, "Your current " + SkillBookUtil.capitalize(this.skill) + " level is higher than this book.");
        } else if(currentLevel == Config.MAX_LEVEL.get()) {
            return new UseSkillBookResult(false, "You have max level " + SkillBookUtil.capitalize(this.skill) + ".");
        } else if(this.levelToSet > 1 && currentLevel+1 != this.levelToSet) {
            return new UseSkillBookResult(false, "You must reach level " + (this.levelToSet-1) + " " + SkillBookUtil.capitalize(this.skill) + " before you can move to level " + this.levelToSet + ".");
        } else {
            return new UseSkillBookResult(true, "");
        }
    }

    @Override
    protected void useSkillBook(Player player) {
        APIUtils.setLevel(this.skill, player, this.levelToSet);
    }

    @Override
    protected String getEffectDescription() {
        return "You are now a level " + this.levelToSet + " " +  SkillBookUtil.capitalize(this.skill) + ".";
    }
    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return SkillBookUtil.capitalize(this.skill) + " - Level " + this.levelToSet;
    }
}
