package com.mrh0.createaddition.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.Player;

public class CCApiCommand {

	public static void register(CommandDispatcher<CommandSourceStack> commandSourceStackCommandDispatcher, boolean ignoredB) {
		commandSourceStackCommandDispatcher .register(Commands.literal("cca_api").requires(source -> source.hasPermission(0))
				.executes(context -> {
							Player p =  context.getSource().getPlayerOrException();
							String link = "https://github.com/mrh0/createaddition/blob/main/COMPUTERCRAFT.md";
							MutableComponent text = new TranslatableComponent("createaddition.command.cca_api.link");
							text.withStyle(style -> style.applyFormats(ChatFormatting.AQUA, ChatFormatting.UNDERLINE)
									.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(link)))
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link)));
							p.sendMessage(text, Player.createPlayerUUID(p.getGameProfile()));
							return 1;
						}
				));
	}
}
