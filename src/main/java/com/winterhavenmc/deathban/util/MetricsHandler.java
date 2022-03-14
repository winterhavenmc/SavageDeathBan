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

package com.winterhavenmc.deathban.util;

import com.winterhavenmc.deathban.PluginMain;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;


public class MetricsHandler {

	public MetricsHandler(PluginMain plugin) {

		Metrics metrics = new Metrics(plugin, 14522);

		metrics.addCustomChart(new SimplePie("language", () -> plugin.getConfig().getString("language")));

		metrics.addCustomChart(new SimplePie("ban_ip", () -> plugin.getConfig().getString("ban-ip")));

		metrics.addCustomChart(new SimplePie("ban_time", () -> plugin.getConfig().getString("ban-time")));

		metrics.addCustomChart(new SimplePie("kick_delay", () -> plugin.getConfig().getString("kick-delay")));

	}

}
