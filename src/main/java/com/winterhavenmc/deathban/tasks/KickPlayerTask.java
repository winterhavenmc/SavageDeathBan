package com.winterhavenmc.deathban.tasks;

import com.winterhavenmc.deathban.PluginMain;
import com.winterhavenmc.deathban.messages.Macro;
import com.winterhavenmc.deathban.messages.MessageId;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.winterhavenmc.util.TimeUnit.SECONDS;

public class KickPlayerTask extends BukkitRunnable {

	private final PluginMain plugin;
	private final Player player;


	public KickPlayerTask(final PluginMain plugin, final Player player) {
		this.plugin = plugin;
		this.player = player;
	}


	public void run() {
		// get kick message from language file
		String message = plugin.messageBuilder.compose(player, MessageId.ACTION_PLAYER_KICK)
				.setMacro(Macro.DURATION, SECONDS.toMillis(plugin.getConfig().getLong("ban-time")))
				.toString();

		// kick the player with configured message
		player.kickPlayer(message);
	}

}
