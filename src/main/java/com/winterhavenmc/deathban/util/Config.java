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


import org.bukkit.configuration.Configuration;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

public enum Config
{
    LANGUAGE("en-US"),
	LOCALE(Locale.US),
	TIMEZONE(ZoneId.of("Americas/Chicago")),
    BAN_IP(false),
    BAN_TIME(1440),
    KICK_DELAY(1),
    LOG_BANS(true),
    SOUND_EFFECTS(true),
	ENABLED_WORLDS(List.of()),
	;

    private final Object defaultValue;


    Config(Object value) {
        this.defaultValue = value;
    }

    public String getKey() {
        return this.name().toLowerCase().replace('_', '-');
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }


	public String getString(final Configuration configuration)
	{
		return configuration.getString(this.getKey());
	}


	public boolean getBoolean(final Configuration configuration)
	{
		return configuration.getBoolean(this.getKey());
	}


	public long getLong(final Configuration configuration)
	{
		return configuration.getLong(this.getKey());
	}


	public List<String> getStringList(final Configuration configuration)
	{
		return configuration.getStringList(this.getKey());
	}

}
