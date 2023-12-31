package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.config.Config;
import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;
import net.silvertide.pmmo_skill_books.utils.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;

public class AddLevelSkillBookItem extends SkillBookItem {
    protected String skill;
    protected int levelsToAdd;
    public AddLevelSkillBookItem(Properties properties, String skill, int levelsToAdd){
        super(properties);
        this.skill = skill;
        this.levelsToAdd = levelsToAdd;
    }
    @Override
    protected UseSkillBookResult playerCanUseSkillBook(Player player) {
        if ( APIUtils.getLevel(this.skill, player) >= Config.MAX_LEVEL.get() ) return new UseSkillBookResult(false, SkillBookUtil.capitalize(this.skill) + " is at max level.");
        return new UseSkillBookResult(true, "");
    }
    @Override
    protected void useSkillBook(Player player) {
        int currentLevel = APIUtils.getLevel(this.skill, player);
        int maxLevel = Config.MAX_LEVEL.get();
        if((currentLevel + this.levelsToAdd) >= maxLevel) {
            APIUtils.setLevel(this.skill, player, maxLevel);
        } else {
            APIUtils.addLevel(this.skill, player, this.levelsToAdd);
        }
    }

    @Override
    protected String getEffectDescription() {
        return "You have gained " + this.levelsToAdd + " " + (this.levelsToAdd == 1 ? "level" : "levels") + " of " + SkillBookUtil.capitalize(this.skill) + ".";
    }

    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return "+" + this.levelsToAdd + " " + SkillBookUtil.capitalize(this.skill) + (this.levelsToAdd == 1 ? " Level" : " Levels");
    }
}
