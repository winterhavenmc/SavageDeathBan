package com.winterhavenmc.deathban.tasks;

import com.winterhavenmc.deathban.PluginMain;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KickPlayerTask extends BukkitRunnable {

	private final PluginMain plugin;
	private final Player player;


	public KickPlayerTask(final PluginMain plugin, final Player player) {
		this.plugin = plugin;
		this.player = player;
	}


	public void run() {
		// kick the player with configured message
		player.kickPlayer(plugin.getConfig().getString("kick-message"));
	}

}
