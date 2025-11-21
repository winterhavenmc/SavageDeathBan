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

package com.winterhavenmc.deathban;

import com.winterhavenmc.deathban.commands.CommandDispatcher;
import com.winterhavenmc.deathban.eventhandlers.PlayerEventHandler;
import com.winterhavenmc.deathban.util.MetricsHandler;

import com.winterhavenmc.library.messagebuilder.MessageBuilder;

import org.bukkit.plugin.java.JavaPlugin;


public class PluginMain extends JavaPlugin
{
	// message builder
	public MessageBuilder messageBuilder;


	@Override
	public void onEnable()
	{
		// install default config.yml if not present
		saveDefaultConfig();

		// instantiate message builder
		messageBuilder = MessageBuilder.create(this);

		// instantiate command manager
		new CommandDispatcher(this);

		// instantiate player event handler
		new PlayerEventHandler(this);

		// instantiate metrics handler
		new MetricsHandler(this);
	}

}
