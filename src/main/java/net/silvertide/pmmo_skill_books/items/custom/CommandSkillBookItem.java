package net.silvertide.pmmo_skill_books.items.custom;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.silvertide.pmmo_skill_books.util.UseSkillBookResult;
import net.silvertide.pmmo_skill_books.items.SkillBookItem;

public class CommandSkillBookItem extends SkillBookItem {
    protected String command;
    protected String effectDescription;
    public CommandSkillBookItem(Properties properties, String command, String effectDescription){
        super(properties);
        this.effectDescription = effectDescription;
        this.command = command;
    }

    @Override
    protected UseSkillBookResult playerCanUseSkillBook(Player player) {
        return new UseSkillBookResult(true, "");
    }

    @Override
    protected void useSkillBook(Player player) {
        CommandSourceStack cmdSrc = player.createCommandSourceStack();
        player.getServer().getCommands().performPrefixedCommand(cmdSrc, command);
    }

    @Override
    protected String getEffectDescription() {
        return this.effectDescription;
    }

    @Override
    protected String getHoverTextDescription()  {
        if(this.description != null) return this.description;
        return "Needs description.";
    }
}
