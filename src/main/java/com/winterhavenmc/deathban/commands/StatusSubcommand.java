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
import com.winterhavenmc.deathban.util.Macro;
import com.winterhavenmc.deathban.util.MessageId;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.time.Duration;
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
			return plugin.messageBuilder.compose(sender, MessageId.COMMAND_FAIL_PERMISSION_STATUS).send();
		}

		displayStatusHeader(sender);
		displayPluginVersion(sender);
		displayDebugSetting(sender);
		displayLanguageSetting(sender);
		displayLocaleSetting(sender);
		displayTimezoneSetting(sender);
		displayBanTimeSetting(sender);
		displayKickDelaySetting(sender);
		displayBanIpSetting(sender);
		displayLogBansSetting(sender);
		displaySoundEffectsSetting(sender);
		displayEnabledWorldsSetting(sender);
		displayStatusFooter(sender);
		return true;
	}

	private void displayStatusHeader(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_HEADER)
//				.setMacro(Macro.PLUGIN, plugin)
				.send();
	}


	private void displayPluginVersion(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_PLUGIN_VERSION).send();
	}

	private void displayDebugSetting(final CommandSender sender)
	{
		if (plugin.getConfig().getBoolean("debug")) {
			sender.sendMessage(ChatColor.DARK_RED + "DEBUG: true");
		}
	}


	private void displayLanguageSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LANGUAGE_SETTING)
				.setMacro(Macro.SETTING, plugin.getConfig().getString("language"))
				.send();
	}


	private void displayLocaleSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LOCALE_SETTING)
				.setMacro(Macro.SETTING, plugin.messageBuilder.config().languageTag())
				.send();
	}


	private void displayTimezoneSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_TIMEZONE_SETTING)
				.setMacro(Macro.SETTING, plugin.messageBuilder.config().zoneId().getId())
				.send();
	}

	private void displayBanTimeSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BAN_TIME_SETTING)
				.setMacro(Macro.SETTING, Duration.ofMinutes(plugin.getConfig().getLong("ban-time")))
				.send();
	}

	private void displayKickDelaySetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_KICK_DELAY_SETTING)
				.setMacro(Macro.SETTING, Duration.ofSeconds(plugin.getConfig().getLong("kick-delay")))
				.send();
	}


	private void displayBanIpSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_BAN_IP_SETTING)
				.setMacro(Macro.SETTING, plugin.getConfig().getBoolean("ban-ip"))
				.send();
	}


	private void displayLogBansSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_LOG_BANS_SETTING)
				.setMacro(Macro.SETTING, plugin.getConfig().getBoolean("log-bans"))
				.send();
	}


	private void displaySoundEffectsSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_SOUND_EFFECTS_SETTING)
				.setMacro(Macro.SETTING, plugin.getConfig().getBoolean("sound-effects"))
				.send();
	}


	private void displayEnabledWorldsSetting(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_ENABLED_WORLDS_SETTING)
				.setMacro(Macro.SETTING, plugin.messageBuilder.worlds().enabledNames().toString())
				.send();
	}


	private void displayStatusFooter(final CommandSender sender)
	{
		plugin.messageBuilder.compose(sender, MessageId.COMMAND_STATUS_FOOTER).send();
	}

}
