package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;

public class SetLevelSkillBookItem extends SkillBookItem {
    protected String skill;
    protected int levelToSet;
    // Abstract numLevels and effect out to here
    public SetLevelSkillBookItem(Properties properties, String skill, int levelToSet){
        super(properties);
        this.skill = skill;
        this.levelToSet = levelToSet;

    }
    @Override
    protected void useSkillBook(Player player) {
        APIUtils.setLevel(this.skill, player, this.levelToSet);
    }

    @Override
    protected String getEffectDescription() {
        return "Your " + this.skill + " skill is now level " + this.levelToSet;
    }
    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return this.skill + " level " + this.levelToSet;
    }
}
