package com.winterhavenmc.deathban;

import com.winterhavenmc.deathban.commands.CommandManager;
import com.winterhavenmc.deathban.eventhandlers.PlayerEventHandler;
import com.winterhavenmc.deathban.util.MetricsHandler;
import com.winterhavenmc.util.soundconfig.SoundConfiguration;
import com.winterhavenmc.util.soundconfig.YamlSoundConfiguration;
import com.winterhavenmc.util.worldmanager.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMain extends JavaPlugin {

	// world manager
	public WorldManager worldManager;

	// sound configuration
	public SoundConfiguration soundConfig;

	@Override
	public void onEnable() {

		// install default config.yml if not present
		saveDefaultConfig();

		// instantiate world manager
		worldManager = new WorldManager(this);

		// instantiate sound configuration
		soundConfig = new YamlSoundConfiguration(this);

		// instantiate command manager
		new CommandManager(this);

		// instantiate player event handler
		new PlayerEventHandler(this);

		// instantiate metrics handler
		new MetricsHandler(this);

	}

}
