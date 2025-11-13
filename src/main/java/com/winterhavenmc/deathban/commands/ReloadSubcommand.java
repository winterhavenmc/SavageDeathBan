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
import com.winterhavenmc.deathban.util.MessageId;

import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;


/**
 * Reload command implementation<br>
 * reloads plugin configuration
 */
final class ReloadSubcommand extends AbstractSubcommand implements Subcommand
{
	private final PluginMain plugin;


	/**
	 * Class constructor
	 *
	 * @param plugin reference to plugin main class instance
	 */
	ReloadSubcommand(final PluginMain plugin)
	{
		this.plugin = Objects.requireNonNull(plugin);
		this.name = "reload";
		this.usageString = "/deathban reload";
		this.description = MessageId.COMMAND_HELP_RELOAD;
		this.permissionNode = "deathban.reload";
	}


	@Override
	public boolean onCommand(final CommandSender sender, final List<String> args)
	{
		// if sender does not have permission to reload config, send error message and return true
		if (!sender.hasPermission(permissionNode))
		{
			plugin.messageBuilder.compose(sender, MessageId.COMMAND_FAIL_PERMISSION_RELOAD).send();
			return true;
		}

		// reinstall config file if necessary
		plugin.saveDefaultConfig();

		// reload main configuration
		plugin.reloadConfig();

		// reload messages
		plugin.messageBuilder.reload();

		// send reload success message
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_SUCCESS_RELOAD).send();

		// return true to suppress bukkit usage message
		return true;
	}

}
