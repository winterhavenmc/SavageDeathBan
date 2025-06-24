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
		displayStatusBanner(sender);
		displayPluginVersion(sender);
		displayDebugSetting(sender);
		displayLanguage(sender);
		displayLocale(sender);
		displayBanTime(sender);
		displayKickDelay(sender);
		displayBanIp(sender);
		displaySoundEffects(sender);
		displayLogBans(sender);
		displayEnabledWorlds(sender);

		// always return true to suppress bukkit usage message
		return true;
	}


	private void displayStatusBanner(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BANNER)
				.setMacro(Macro.PLUGIN, plugin.getDescription().getName())
				.send();
	}


	private void displayPluginVersion(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_PLUGIN_VERSION)
				.setMacro(Macro.VERSION, plugin.getDescription().getVersion())
				.send();
	}


	private void displayDebugSetting(final CommandSender sender)
	{
		if (Config.DEBUG.getBoolean(plugin.getConfig()))
		{
			sender.sendMessage(ChatColor.DARK_RED + "DEBUG: true");
		}
	}


	private void displayLanguage(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LANGUAGE)
				.setMacro(Macro.LANGUAGE, plugin.getConfig().getString("language"))
				.send();
	}


	private void displayLocale(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LOCALE)
				.setMacro(Macro.LOCALE, plugin.getConfig().getString("locale"))
				.send();
	}


	private void displayBanTime(final CommandSender sender)
	{
		long banTime = Config.BAN_TIME.getLong(plugin);
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BAN_TIME)
				.setMacro(Macro.DURATION, Duration.ofMinutes(banTime), ChronoUnit.MINUTES)
				.send();
	}


	private void displayKickDelay(final CommandSender sender)
	{
		long kickDelay = Config.KICK_DELAY.getLong(plugin);
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_KICK_DELAY)
				.setMacro(Macro.DURATION, Duration.ofSeconds(kickDelay), ChronoUnit.SECONDS)
				.send();
	}


	private void displayBanIp(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BAN_IP)
				.setMacro(Macro.BAN_IP_SETTING, Config.BAN_IP.getBoolean(plugin.getConfig()))
				.send();
	}


	private void displaySoundEffects(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_SOUND_EFFECTS)
				.setMacro(Macro.SOUND_EFFECTS_SETTING, Config.SOUND_EFFECTS.getBoolean(plugin.getConfig()))
				.send();
	}


	private void displayLogBans(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LOG_BANS)
				.setMacro(Macro.LOG_BANS_SETTING, Config.LOG_BANS.getBoolean(plugin.getConfig()))
				.send();
	}


	private void displayEnabledWorlds(final CommandSender sender)
	{
		String worldList = plugin.worldManager.getEnabledWorldNames().toString();
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_ENABLED_WORLDS)
				.setMacro(Macro.ENABLED_WORLDS, worldList)
				.send();
	}

}
