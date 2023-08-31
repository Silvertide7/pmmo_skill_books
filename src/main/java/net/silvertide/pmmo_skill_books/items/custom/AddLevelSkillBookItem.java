package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.util.UseSkillBookResult;
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
        return new UseSkillBookResult(true, "");
    }
    @Override
    protected void useSkillBook(Player player) {
        int currentLevel = APIUtils.getLevel(this.skill, player);
        APIUtils.setLevel(this.skill, player, this.levelsToAdd + currentLevel);
    }

    @Override
    protected String getEffectDescription() {
        return "You have gained " + this.levelsToAdd + " " + (this.levelsToAdd == 1 ? "level" : "levels") + " of " + this.skill + ".";
    }

    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return "Grants " + (int) this.levelsToAdd + " levels of " + this.skill + ".";
    }
}
