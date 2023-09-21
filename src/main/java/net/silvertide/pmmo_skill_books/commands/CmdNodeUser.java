package net.silvertide.pmmo_skill_books.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.core.IDataStorage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.silvertide.pmmo_skill_books.utils.PrimaryClass;
import net.silvertide.pmmo_skill_books.utils.SkillBookUtil;
import net.silvertide.pmmo_skill_books.utils.SubClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdNodeUser {
    private static final String CLASS_ARG = "Class";
    private static final String SUBCLASS_ARG = "SubClass";

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {

        List<String> primaryClasses = new ArrayList<>();
        for(PrimaryClass primaryClass : PrimaryClass.values()) {
            primaryClasses.add(primaryClass.toString());
        }

        List<String> subClasses = new ArrayList<>();
        for(SubClass subClass : SubClass.values()) {
            subClasses.add(subClass.toString());
        }

        return Commands.literal("forget")
                .then(Commands.literal("class"))
                    .then(Commands.argument(CLASS_ARG, StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(primaryClasses, builder))
                            .executes(CmdNodeUser::forgetClass))
                .then(Commands.literal("subclass"))
                    .then(Commands.argument(SUBCLASS_ARG, StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(subClasses, builder))
                            .executes(CmdNodeUser::forgetSubClass));

    }
    public static int forgetClass(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        String className = StringArgumentType.getString(ctx, CLASS_ARG);

        if(player == null) return 0;

        if(deleteSkill(player, className)){
//            for(PlayerSubClassType subClass : SkillBookUtil.getPlayerClassMap().get(skillName).getSubClasses()) {
//                deleteSkill(player, subClass.toString());
//            }
            String playerMessage = "You have lost the class " + SkillBookUtil.capitalize(className) + " and any of it's subclasses.";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        } else {
            String playerMessage = "You are not a " + SkillBookUtil.capitalize(className) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }


        return 0;
    }

    public static int forgetSubClass(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        String className = StringArgumentType.getString(ctx, CLASS_ARG);

        if(player == null) return 0;

        if(deleteSkill(player, className)){
//            for(PlayerSubClassType subClass : SkillBookUtil.getPlayerClassMap().get(skillName).getSubClasses()) {
//                deleteSkill(player, subClass.toString());
//            }
            String playerMessage = "You have lost the class " + SkillBookUtil.capitalize(className) + " and any of it's subclasses.";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        } else {
            String playerMessage = "You are not a " + SkillBookUtil.capitalize(className) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }


        return 0;
    }

    private static boolean deleteSkill(ServerPlayer player, String className) {
        IDataStorage data = Core.get(LogicalSide.SERVER).getData();
        if (data.getPlayerSkillLevel(className, player.getUUID()) > 0) {
            data.setPlayerSkillLevel(className, player.getUUID(), 0);
            return true;
        }
        return false;
    }
}


