package com.winterhavenmc.deathban.eventhandlers;

import com.winterhavenmc.deathban.PluginMain;

import com.winterhavenmc.deathban.tasks.KickPlayerTask;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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

	// constant ban source string
	private static final String BAN_SOURCE = "SavageDeathBan";


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

		// get event player
		Player player = event.getEntity();

		// if player has exempt permission, do nothing and return
		if (player.hasPermission("deathban.exempt")) {
			return;
		}

		// if world is not enabled, do nothing and return
		if (!plugin.worldManager.isEnabled(player.getWorld())) {
			return;
		}

		// ban player
		banPlayer(player);

		// ban player ip if configured
		if (plugin.getConfig().getBoolean("ban-ip")) {
			banPlayerIp(player);
		}

		// put player in kick on respawn set
		kickSet.add(player.getUniqueId());
	}


	/**
	 * Event handler for PlayerRespawnEvent
	 *
	 * @param event the event handled by this method
	 */
	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {

		// get event player
		Player player = event.getPlayer();

		// if player is in kick on respawn set, kick after configured delay
		if (kickSet.contains(player.getUniqueId())) {
			new KickPlayerTask(plugin, player).runTaskLater(plugin, plugin.getConfig().getLong("kick-delay") * 20L);
		}

		// remove player from kick on respawn set
		kickSet.remove(player.getUniqueId());
	}


	/**
	 * Add player to ban list
	 *
	 * @param player the player to add to ban list
	 */
	private void banPlayer(final Player player) {

		// get expiration date
		Date expireDate = new Date(System.currentTimeMillis() + MINUTES.toMillis(plugin.getConfig().getLong("ban-time")));

		// get ban list
		BanList banList = plugin.getServer().getBanList(BanList.Type.NAME);

		// add player to ban list
		BanEntry banEntry = banList.addBan(player.getName(), plugin.getConfig().getString("ban-message"), expireDate, BAN_SOURCE);

		// save ban entry
		if (banEntry != null) {
			banEntry.save();
		}
	}


	/**
	 * Add player IP address to ban list
	 *
	 * @param player the player whose IP address to add to ban list
	 */
	private void banPlayerIp(final Player player) {

		// get player ip
		InetSocketAddress playerAddress = player.getAddress();

		// if player address is null, do nothing and return
		if (playerAddress == null) {
			return;
		}

		// get expiration date
		Date expireDate = new Date(System.currentTimeMillis() + MINUTES.toMillis(plugin.getConfig().getLong("ban-time")));

		// get ip ban list
		BanList ipBanList = plugin.getServer().getBanList(BanList.Type.IP);

		// add player ip to ban list
		BanEntry ipBanEntry = ipBanList.addBan(playerAddress.getHostString(), plugin.getConfig().getString("ban-message"), expireDate, BAN_SOURCE);

		// save ban entry
		if (ipBanEntry != null) {
			ipBanEntry.save();
		}
	}

}
