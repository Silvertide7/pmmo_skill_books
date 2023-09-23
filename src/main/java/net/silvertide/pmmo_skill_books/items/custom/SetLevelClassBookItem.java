package net.silvertide.pmmo_skill_books.items.custom;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.Config;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;
import net.silvertide.pmmo_skill_books.utils.*;

import java.util.List;

public class SetLevelClassBookItem extends SkillBookItem {
    protected String skill;
    protected int levelToSet;
    public SetLevelClassBookItem(Properties properties, String skill, int levelToSet) {
        super(properties);
        this.skill = skill;
        this.levelToSet = levelToSet;
    }

    @Override
    protected UseSkillBookResult playerCanUseSkillBook(Player player) {
        int currentLevel = APIUtils.getLevel(this.skill, player);
        if(currentLevel == this.levelToSet) {
            return new UseSkillBookResult(false, "You are already a level " + this.levelToSet + " " + SkillBookUtil.capitalize(this.skill) + ".");
        } else if (currentLevel > this.levelToSet) {
            return new UseSkillBookResult(false, "Your current " + SkillBookUtil.capitalize(this.skill) + " level is higher than this book.");
        } else if(currentLevel == Config.MAX_LEVEL.get()) {
            return new UseSkillBookResult(false, "You are a max level " + SkillBookUtil.capitalize(this.skill) + ".");
        } else if(this.levelToSet > 1 && currentLevel+1 != this.levelToSet) {
            return new UseSkillBookResult(false, "You must reach level " + (this.levelToSet-1) + " " + SkillBookUtil.capitalize(this.skill) + " before you can move to level " + this.levelToSet + ".");
        } else {
            // Check Primary Class requirements
            PrimaryClass primaryClass = PrimaryClass.fromString(this.skill);
            if(primaryClass != null) {
                List<PrimaryClass> currPrimaryClasses = PlayerClassUtil.getCurrentPrimaryClasses(player);
                if(currPrimaryClasses.size() >= 2) {
                    StringBuilder classNames = new StringBuilder();
                    for(int i = 0; i < currPrimaryClasses.size(); i++) {
                        classNames.append(SkillBookUtil.capitalize(currPrimaryClasses.get(i).toString()));
                        if(i != currPrimaryClasses.size() - 1) {
                            classNames.append(" - ");
                        }
                    }
                    return new UseSkillBookResult(false, "You already have 2 primary classes. " + classNames);
                }
            }
            // Check Sub Class requirements
            SubClass subClass = SubClass.fromString(this.skill);
            if(subClass != null) {
                PrimaryClass depClass = PlayerClassUtil.getPrimaryClass(subClass);
                if(APIUtils.getLevel(depClass.toString(), player) == 0) {
                    return new UseSkillBookResult(false, "You must be a " + SkillBookUtil.capitalize(depClass.toString()) + " to use take this subclass.");
                }

                SubClass conflictingSubclass = PlayerClassUtil.getConflictingSubclass(player, this.skill);
                if(conflictingSubclass != null) {
                    PrimaryClass pClass = PlayerClassUtil.getPrimaryClass(conflictingSubclass);
                    return new UseSkillBookResult(false, "You already have a " + SkillBookUtil.capitalize(pClass.toString()) + " subclass - " + SkillBookUtil.capitalize(conflictingSubclass.toString()));
                }
            }
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
