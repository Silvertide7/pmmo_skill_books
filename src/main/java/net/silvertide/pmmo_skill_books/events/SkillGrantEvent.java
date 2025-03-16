package net.silvertide.pmmo_skill_books.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

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

    public static class Pre extends SkillGrantEvent implements ICancellableEvent {
        public Pre(Player player, String skill, String applicationType, Long applicationValue) {
            super(player, skill, applicationType, applicationValue);
        }
    }

    public static class Post extends SkillGrantEvent {
        public Post(Player player, String skill, String applicationType, Long applicationValue) {
            super(player, skill, applicationType, applicationValue);
        }
    }
}
