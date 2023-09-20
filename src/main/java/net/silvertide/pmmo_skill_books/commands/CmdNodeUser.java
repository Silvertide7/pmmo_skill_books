package net.silvertide.pmmo_skill_books.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import harmonised.pmmo.setup.datagen.LangProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.silvertide.pmmo_skill_books.utils.PlayerSubClassType;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;

public class CmdNodeUser {
    private static final String SKILL_ARG = "Class Name";

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("forget").then(Commands.argument(SKILL_ARG, StringArgumentType.word())
                .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(SkillBookUtil.getClassListAsStrings(false), builder)).executes(CmdNodeUser::forgetClass));
    }
    public static int forgetClass(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        String skillName = StringArgumentType.getString(ctx, SKILL_ARG);

        if(player == null) return 0;

        if(deleteSkill(player, skillName)){
            for(PlayerSubClassType subClass : SkillBookUtil.getPlayerMap().get(skillName).getSubClasses()) {
                deleteSkill(player, subClass.toString());
            }
            String playerMessage = "You have lost the class " + SkillBookUtil.capitalize(skillName) + " and any of it's subclasses.";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        } else {
            String playerMessage = "You are not a " + SkillBookUtil.capitalize(skillName) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }


        return 0;
    }

    private static boolean deleteSkill(ServerPlayer player, String skill) {
        IDataStorage data = Core.get(LogicalSide.SERVER).getData();
        if (data.getPlayerSkillLevel(skill, player.getUUID()) > 0) {
            data.setPlayerSkillLevel(skill, player.getUUID(), 0);
            return true;
        }
        return false;
    }
}


