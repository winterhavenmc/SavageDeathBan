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
