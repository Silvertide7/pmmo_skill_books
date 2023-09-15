package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;
import net.silvertide.pmmo_skill_books.utils.UseSkillBookResult;
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
        if(currentLevel >= this.levelToSet) {
            return new UseSkillBookResult(false, "Your current level of " + this.skill + " is higher than skill book.");
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
        return "Your " + SkillBookUtil.capitalize(this.skill) + " skill is now level " + this.levelToSet;
    }
    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return SkillBookUtil.capitalize(this.skill) + " - Level " + this.levelToSet;
    }
}
