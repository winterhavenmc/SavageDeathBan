/*
 * Copyright (c) 2022 Tim Savage.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.winterhavenmc.deathban.commands;

import com.winterhavenmc.deathban.PluginMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import java.util.*;


/**
 * Implements command executor for SavageDeathBan commands.
 */
public final class CommandManager implements TabExecutor {

	// map of subcommands
	private final SubcommandRegistry subcommandRegistry = new SubcommandRegistry();


	/**
	 * constructor method for {@code CommandManager} class
	 *
	 * @param plugin reference to main class
	 */
	public CommandManager(final PluginMain plugin) {

		// register this class as command executor
		Objects.requireNonNull(plugin.getCommand("deathban")).setExecutor(this);

		// register subcommands
		for (SubcommandType subcommandType : SubcommandType.values()) {
			subcommandRegistry.register(subcommandType.create(plugin));
		}

		// register help command
		subcommandRegistry.register(new HelpSubcommand(plugin, subcommandRegistry));
	}


	/**
	 * Tab completer for SavageDeathBan commands
	 */
	@Override
	public List<String> onTabComplete(final @Nonnull CommandSender sender, final @Nonnull Command command,
	                                  final @Nonnull String alias, @Nonnull final String[] args) {

		// if more than one argument, use tab completer of subcommand
		if (args.length > 1) {

			// get subcommand from map
			Subcommand subcommand = subcommandRegistry.getSubcommand(args[0]);

			// if no subcommand returned from map, return empty list
			if (subcommand == null) {
				return Collections.emptyList();
			}

			// return subcommand tab completer output
			return subcommand.onTabComplete(sender, command, alias, args);
		}

		// return list of subcommands for which sender has permission
		return matchCommands(sender, args[0]);
	}


	/**
	 * Command Executor for SavageDeathBan
	 */
	@Override
	public boolean onCommand(final @Nonnull CommandSender sender, final @Nonnull Command command,
							 final @Nonnull String label, @Nonnull final String[] args) {

		// convert args array to list
		List<String> argsList = new ArrayList<>(Arrays.asList(args));

		String subcommandName;

		// get subcommand, remove from front of list
		if (argsList.size() > 0) {
			subcommandName = argsList.remove(0);
		}

		// if no arguments, set command to help
		else {
			subcommandName = "help";
		}

		// get subcommand from map by name
		Subcommand subcommand = subcommandRegistry.getSubcommand(subcommandName);

		// if subcommand is null, get help command from map
		if (subcommand == null) {
			subcommand = subcommandRegistry.getSubcommand("help");
		}

		// execute subcommand
		 return subcommand.onCommand(sender, argsList);
	}


	/**
	 * Get matching list of subcommands for which sender has permission
	 * @param sender the command sender
	 * @param matchString the string prefix to match against command names
	 * @return List of String - command names that match prefix and sender has permission
	 */
	private List<String> matchCommands(final CommandSender sender, final String matchString) {

		List<String> returnList = new ArrayList<>();

		for (String subcommandName : subcommandRegistry.getKeys()) {
			if (sender.hasPermission(subcommandRegistry.getSubcommand(subcommandName).getPermission())
					&& subcommandName.startsWith(matchString.toLowerCase())) {
				returnList.add(subcommandName);
			}
		}
		return returnList;
	}

}
