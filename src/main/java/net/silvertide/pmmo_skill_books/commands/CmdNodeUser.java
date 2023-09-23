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
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.silvertide.pmmo_skill_books.utils.*;

import java.util.ArrayList;
import java.util.List;

public class CmdNodeUser {
    private static final String CLASS_ARG = "Class";
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        List<String> classes = new ArrayList<>();
        for(PrimaryClass primaryClass : PrimaryClass.values()) {
            classes.add(primaryClass.toString());
        }
        for(SubClass subClass : SubClass.values()) {
            classes.add(subClass.toString());
        }
        return Commands.literal("forget").then(Commands.literal("class")).then(Commands.argument(CLASS_ARG, StringArgumentType.word()).suggests((ctx, builder) -> SharedSuggestionProvider.suggest(classes, builder)).executes(CmdNodeUser::forgetClass));
    }
    public static int forgetClass(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        String className = StringArgumentType.getString(ctx, CLASS_ARG);

        if(player == null) return 0;

        PrimaryClass primaryClass = PrimaryClass.fromString(className);
        SubClass subClass = SubClass.fromString(className);
        if(primaryClass != null) {
            deletePrimaryClass(ctx, primaryClass, player);
        } else if (subClass != null) {
            deleteSubClass(ctx, subClass, player);
        } else {
            String playerMessage = SkillBookUtil.capitalize(className) + " is not a Class.";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }

        return 0;
    }

    private static void deleteSubClass(CommandContext<CommandSourceStack> ctx, SubClass subClass, ServerPlayer player) {
        if(deleteClass(player, subClass)) {
            String playerMessage = "You have lost the sub class " + SkillBookUtil.capitalize(subClass.toString()) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        } else {
            String playerMessage = "You do not have the subclass " + SkillBookUtil.capitalize(subClass.toString()) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }
    }

    private static void deletePrimaryClass(CommandContext<CommandSourceStack> ctx, PrimaryClass primaryClass, ServerPlayer player) {
        if(deleteClass(player, primaryClass)) {
            List<SubClass> subClasses = PlayerClassUtil.getSubClasses(primaryClass);
            if(subClasses != null){
                for(SubClass sClass : subClasses) {
                    deleteClass(player, sClass);
                }
            }

            String playerMessage = "You have lost the class " + SkillBookUtil.capitalize(primaryClass.toString()) + " and any of it's subclasses.";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        } else {
            String playerMessage = "You are not a " + SkillBookUtil.capitalize(primaryClass.toString()) + ".";
            ctx.getSource().sendSuccess(() -> Component.literal(playerMessage), true);
        }
    }

    private static boolean deleteClass(ServerPlayer player, IPlayerClass className) {
        IDataStorage data = Core.get(LogicalSide.SERVER).getData();
        if (data.getPlayerSkillLevel(className.toString(), player.getUUID()) > 0) {
            data.setPlayerSkillLevel(className.toString(), player.getUUID(), 0);
            return true;
        }
        return false;
    }
}


