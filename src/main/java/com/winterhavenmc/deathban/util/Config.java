/*
 * Copyright (c) 2022-2024 Tim Savage.
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


import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Locale;


public enum Config
{
	DEBUG(Boolean.FALSE),
	LANGUAGE(Locale.US.toLanguageTag()),
	ENABLED_WORLDS(List.of()),
	DISABLED_WORLDS(List.of()),
	BAN_IP(Boolean.FALSE),
	BAN_TIME(1440),
	KICK_DELAY(1),
	LOG_BANS(Boolean.TRUE),
	SOUND_EFFECTS(Boolean.TRUE),
//    BAN_REASON("SavageDeathBan")
	;

	private final Object defaultObject;


	<T> Config(T defaultObject)
	{
		this.defaultObject = defaultObject;
	}


	public String asKey()
	{
		return this.toLowerKebabCase();
	}


	public String getDefaultValue()
	{
		return this.defaultObject.toString();
	}


	/**
	 * Get value as boolean for corresponding key in current configuration
	 *
	 * @param plugin {@code JavaPlugin} reference to the plugin instance
	 * @return {@code boolean} the referenced value in the current configuration
	 */
	public boolean getBoolean(final Plugin plugin)
	{
		return plugin.getConfig().getBoolean(asKey());
	}


	/**
	 * Get value as int for corresponding key in current configuration
	 *
	 * @param plugin {@code JavaPlugin} reference to the plugin instance
	 * @return {@code int} the referenced value in the current configuration
	 */
	public int getInt(final Plugin plugin)
	{
		return plugin.getConfig().getInt(asKey());
	}


	/**
	 * Get value as String for corresponding key in current configuration
	 *
	 * @param plugin {@code JavaPlugin} reference to the plugin instance
	 * @return {@code String} the referenced value in the current configuration
	 */
	public String getString(final Plugin plugin)
	{
		return plugin.getConfig().getString(asKey());
	}


	/**
	 * Get value as List of String for corresponding key in current configuration
	 *
	 * @param plugin {@code JavaPlugin} reference to the plugin instance
	 * @return {@code List<String>} the referenced value in the current configuration
	 */
	public List<String> getStringList(final Plugin plugin)
	{
		return plugin.getConfig().getStringList(asKey());
	}


	/**
	 * Get value as int for corresponding key in current configuration
	 *
	 * @param plugin {@code JavaPlugin} reference to the plugin instance
	 * @return {@code long} the referenced value in the current configuration
	 */
	public long getLong(final Plugin plugin)
	{
		return plugin.getConfig().getLong(asKey());
	}


	/**
	 * Convert Enum member name to lower kebab case
	 *
	 * @return {@code String} the Enum member name as lower kebab case
	 */
	private String toLowerKebabCase()
	{
		return this.name().toLowerCase().replace('_', '-');
	}
}
