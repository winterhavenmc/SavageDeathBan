/*
 * Copyright (c) 2022-2025 Tim Savage.
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

package com.winterhavenmc.deathban.util;

public enum MessageId
{
	COMMAND_FAIL_PERMISSION_HELP,
	COMMAND_FAIL_PERMISSION_RELOAD,
	COMMAND_FAIL_PERMISSION_STATUS,
	COMMAND_INVALID,

	COMMAND_HELP_HELP,
	COMMAND_HELP_RELOAD,
	COMMAND_HELP_STATUS,
	COMMAND_HELP_USAGE_HEADER,

	COMMAND_SUCCESS_RELOAD,

	ACTION_PLAYER_KICK,
	ACTION_PLAYER_BAN,

	LOG_PLAYER_BAN,
	LOG_PLAYER_IP_BAN,

	COMMAND_STATUS_HEADER,
	COMMAND_STATUS_PLUGIN_VERSION,
	COMMAND_STATUS_LANGUAGE_SETTING,
	COMMAND_STATUS_TIMEZONE_SETTING,
	COMMAND_STATUS_LOCALE_SETTING,
	COMMAND_STATUS_BAN_TIME_SETTING,
	COMMAND_STATUS_KICK_DELAY_SETTING,
	COMMAND_STATUS_BAN_IP_SETTING,
	COMMAND_STATUS_LOG_BANS_SETTING,
	COMMAND_STATUS_SOUND_EFFECTS_SETTING,
	COMMAND_STATUS_ENABLED_WORLDS_SETTING,
	COMMAND_STATUS_FOOTER,

}
