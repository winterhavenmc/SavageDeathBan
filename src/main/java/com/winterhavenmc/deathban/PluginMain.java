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

import com.winterhavenmc.deathban.commands.CommandManager;
import com.winterhavenmc.deathban.eventhandlers.PlayerEventHandler;
import com.winterhavenmc.deathban.messages.Macro;
import com.winterhavenmc.deathban.messages.MessageId;
import com.winterhavenmc.deathban.util.MetricsHandler;

import com.winterhavenmc.util.messagebuilder.MessageBuilder;
import com.winterhavenmc.util.soundconfig.SoundConfiguration;
import com.winterhavenmc.util.soundconfig.YamlSoundConfiguration;
import com.winterhavenmc.util.worldmanager.WorldManager;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;


public class PluginMain extends JavaPlugin {

	// message builder
	public MessageBuilder<MessageId, Macro> messageBuilder;

	// sound configuration
	public SoundConfiguration soundConfig;

	// world manager
	public WorldManager worldManager;


	// constructor for testing
	@SuppressWarnings("unused")
	public PluginMain() {
		super();
	}


	// constructor for testing
	@SuppressWarnings("unused")
	protected PluginMain(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
		super(loader, descriptionFile, dataFolder, file);
	}


	@Override
	public void onEnable() {

		// install default config.yml if not present
		saveDefaultConfig();

		// instantiate message builder
		messageBuilder = new MessageBuilder<>(this);

		// instantiate sound configuration
		soundConfig = new YamlSoundConfiguration(this);

		// instantiate world manager
		worldManager = new WorldManager(this);

		// instantiate command manager
		new CommandManager(this);

		// instantiate player event handler
		new PlayerEventHandler(this);

		// instantiate metrics handler
		new MetricsHandler(this);

	}

}
