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
