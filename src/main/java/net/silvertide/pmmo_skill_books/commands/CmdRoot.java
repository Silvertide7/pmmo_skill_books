package net.silvertide.pmmo_skill_books.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CmdRoot {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pmmo_skill_books")
                .then(CmdNodeUser.register(dispatcher)));
    }
}
