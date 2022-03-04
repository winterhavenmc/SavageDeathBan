package com.winterhavenmc.deathban.eventhandlers;

import com.winterhavenmc.deathban.PluginMain;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.MINUTES;


public class PlayerEventHandler implements Listener {

	// reference to plugin main class
	private final PluginMain plugin;

	private final Set<UUID> kickSet = new HashSet<>();


	public PlayerEventHandler(final PluginMain plugin) {

		// set reference to plugin main class
		this.plugin = plugin;

		// register events in this class
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {

		plugin.getLogger().info("PlayerDeathEvent handled.");

		// if player has exempt permission, do nothing and return
		if (event.getEntity().hasPermission("deathban.exempt")) {
			plugin.getLogger().info("Player has exempt permission.");
			return;
		}

		// if world is not enabled, do nothing and return
		if (!plugin.worldManager.isEnabled(event.getEntity().getWorld())) {
			plugin.getLogger().info("Player world is not enabled.");
			return;
		}

		// get expiration date
		Date expireDate = new Date(System.currentTimeMillis() + MINUTES.toMillis(plugin.getConfig().getLong("ban-time")));

		// get ban list
		BanList banList = plugin.getServer().getBanList(BanList.Type.NAME);

		// add player to ban list
		BanEntry banEntry = banList.addBan(event.getEntity().getName(), plugin.getConfig().getString("ban-message"), expireDate, "SavageDeathBan");

		// save ban entry
		if (banEntry != null) {
			banEntry.save();
		}

		// put player in kick set
		kickSet.add(event.getEntity().getUniqueId());



	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {

		if (kickSet.contains(event.getPlayer().getUniqueId())) {
			kickSet.remove(event.getPlayer().getUniqueId());
			new BukkitRunnable() {
				@Override
				public void run() {
					event.getPlayer().kickPlayer(plugin.getConfig().getString("kick-message"));
				}
			}.runTaskLater(plugin, 20L);
		}

	}

}
