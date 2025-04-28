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
import com.winterhavenmc.deathban.messages.Macro;
import com.winterhavenmc.deathban.messages.MessageId;
import com.winterhavenmc.deathban.sounds.SoundId;
import com.winterhavenmc.deathban.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


/**
 * Status command implementation<br>
 * Display plugin settings
 */
final class StatusSubcommand extends AbstractSubcommand implements Subcommand
{
	private final PluginMain plugin;


	/**
	 * Class constructor
	 *
	 * @param plugin reference to plugin main class instance
	 */
	StatusSubcommand(final PluginMain plugin)
	{
		this.plugin = Objects.requireNonNull(plugin);
		this.name = "status";
		this.usageString = "/deathban status";
		this.description = MessageId.COMMAND_HELP_STATUS;
		this.permissionNode = "deathban.status";
	}


	@Override
	public boolean onCommand(final CommandSender sender, final List<String> args)
	{
		// if command sender does not have permission to view status, output error message and return true
		if (!sender.hasPermission(permissionNode))
		{
			plugin.messageBuilder.compose(sender, MessageId.COMMAND_FAIL_PERMISSION_STATUS).send();
			plugin.soundConfig.playSound(sender, SoundId.COMMAND_FAIL_PERMISSION);
			return true;
		}

		// output config settings
		String versionString = plugin.getDescription().getVersion();

		sender.sendMessage(ChatColor.DARK_AQUA
				+ "[" + plugin.getName() + "] " + ChatColor.AQUA + "Version: " + ChatColor.RESET + versionString);

		if (Config.DEBUG.getBoolean(plugin))
		{
			sender.sendMessage(ChatColor.DARK_RED + "DEBUG: true");
		}

		long banTime = Config.BAN_TIME.getLong(plugin);
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BAN_TIME)
				.setMacro(Macro.DURATION, Duration.ofMinutes(banTime), ChronoUnit.SECONDS)
				.send();

		long kickDelay = Config.KICK_DELAY.getLong(plugin);
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_KICK_DELAY)
				.setMacro(Macro.DURATION, Duration.ofMinutes(kickDelay), ChronoUnit.SECONDS)
				.send();

		sender.sendMessage(ChatColor.GREEN + "Ban IP: "
				+ ChatColor.RESET + Config.BAN_IP.getBoolean(plugin));

		sender.sendMessage(ChatColor.GREEN + "Sound effects: "
				+ ChatColor.RESET + Config.SOUND_EFFECTS.getBoolean(plugin));

		sender.sendMessage(ChatColor.GREEN + "Log bans: "
				+ ChatColor.RESET + Config.LOG_BANS.getBoolean(plugin));

		sender.sendMessage(ChatColor.GREEN + "Enabled Worlds: "
				+ ChatColor.RESET + plugin.worldManager.getEnabledWorldNames().toString());

		// always return true to suppress bukkit usage message
		return true;
	}

}
