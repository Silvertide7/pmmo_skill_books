package net.silvertide.pmmo_skill_books.events.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * SkillGrantEvent is fired before / after a {@link Player} gains a new skill from a skill grant item.<br>
 * <br>
 * The Pre event is not Cancelable.<br>
 * <br>
 * This event does not have a result.<br>
 * <br>
 * This event is fired on the Neoforge Event Bus (GAME).<br>
 **/
public abstract class SkillGrantEvent extends PlayerEvent {
    private final String skill;
    private final String applicationType;
    private final Long applicationValue;

    public SkillGrantEvent(Player player, String skill, String applicationType, Long applicationValue) {
        super(player);
        this.skill = skill;
        this.applicationType = applicationType;
        this.applicationValue = applicationValue;
    }
    public String getSkill() {
        return this.skill;
    }

    public String getApplicationType() { return this.applicationType; }
    public Long getApplicationValue() { return this.applicationValue; }

    public static class Pre extends SkillGrantEvent {
        public Pre(Player player, String skill, String applicationType, Long applicationValue) {
            super(player, skill, applicationType, applicationValue);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends SkillGrantEvent {
        public Post(Player player, String skill, String applicationType, Long applicationValue) {
            super(player, skill, applicationType, applicationValue);
        }

        @Override
        public boolean isCancelable() {
            return false;
        }
    }
}
