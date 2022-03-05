package com.winterhavenmc.deathban.eventhandlers;

import com.winterhavenmc.deathban.PluginMain;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.MINUTES;


/**
 * Class that implements event handlers for player related events
 */
public class PlayerEventHandler implements Listener {

	// reference to plugin main class
	private final PluginMain plugin;

	// set of player uuids that will be kicked on respawn
	private final Set<UUID> kickSet = new HashSet<>();


	/**
	 * Class constructor
	 *
	 * @param plugin reference to plugin main class
	 */
	public PlayerEventHandler(final PluginMain plugin) {

		// set reference to plugin main class
		this.plugin = plugin;

		// register events in this class
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	/**
	 * Event handler for PlayerDeathEvent
	 *
	 * @param event the event handled by this method
	 */
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

		// get event player
		Player player = event.getEntity();

		// get expiration date
		Date expireDate = new Date(System.currentTimeMillis() + MINUTES.toMillis(plugin.getConfig().getLong("ban-time")));

		// get ban list
		BanList banList = plugin.getServer().getBanList(BanList.Type.NAME);

		// add player to ban list
		BanEntry banEntry = banList.addBan(player.getName(), plugin.getConfig().getString("ban-message"), expireDate, "SavageDeathBan");

		// save ban entry
		if (banEntry != null) {
			banEntry.save();
		}

		// ban player ip if configured
		if (plugin.getConfig().getBoolean("ban-ip")) {

			// get player ip
			InetSocketAddress playerAddress = player.getAddress();

			// if player address is null, do nothing and return
			if (playerAddress == null) {
				return;
			}

			// get ip ban list
			BanList ipBanList = plugin.getServer().getBanList(BanList.Type.IP);

			// add player ip to ban list
			BanEntry ipBanEntry = ipBanList.addBan(playerAddress.getHostString(), plugin.getConfig().getString("ban-message"), expireDate, "SavageDeathBan");

			// save ban entry
			if (ipBanEntry != null) {
				ipBanEntry.save();
			}
		}

		// put player in kick set
		kickSet.add(event.getEntity().getUniqueId());
	}


	/**
	 * Event handler for PlayerRespawnEvent
	 *
	 * @param event the event handled by this method
	 */
	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {

		// if player is in kick set, perform action
		if (kickSet.contains(event.getPlayer().getUniqueId())) {

			// remove player from kick set
			kickSet.remove(event.getPlayer().getUniqueId());

			// run task to kick player after delay
			new BukkitRunnable() {
				@Override
				public void run() {
					// kick the player with configured message
					event.getPlayer().kickPlayer(plugin.getConfig().getString("kick-message"));
				}
			}.runTaskLater(plugin, plugin.getConfig().getLong("kick-delay") * 20L);
		}
	}

}
