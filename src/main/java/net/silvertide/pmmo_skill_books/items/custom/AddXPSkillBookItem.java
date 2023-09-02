package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;
import net.silvertide.pmmo_skill_books.utils.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;

public class AddXPSkillBookItem extends SkillBookItem {
    protected String skill;
    private long xpToAdd;
    public AddXPSkillBookItem(Properties properties, String skill, long xpToAdd){
        super(properties);
        this.skill = skill;
        this.xpToAdd = xpToAdd;
    }
    @Override
    protected UseSkillBookResult playerCanUseSkillBook(Player player) {
        return new UseSkillBookResult(true, "");
    }
    @Override
    protected void useSkillBook(Player player) {
        APIUtils.addXp(this.skill, player, this.xpToAdd);
    }

    @Override
    protected String getEffectDescription() {
        return "You have gained " + this.xpToAdd + " " + SkillBookUtil.capitalize(this.skill) + " experience.";
    }

    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return "+" + this.xpToAdd + " " + SkillBookUtil.capitalize(this.skill) + " Experience";
    }
}
