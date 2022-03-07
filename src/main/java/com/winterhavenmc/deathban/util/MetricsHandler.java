package com.winterhavenmc.deathban.util;

import com.winterhavenmc.deathban.PluginMain;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

import static com.winterhavenmc.util.TimeUnit.MINUTES;
import static com.winterhavenmc.util.TimeUnit.SECONDS;


public class MetricsHandler {

	public MetricsHandler(PluginMain plugin) {

		Metrics metrics = new Metrics(plugin, 14522);

		metrics.addCustomChart(new SimplePie("language", () -> String.valueOf(plugin.getConfig().getString("language"))));

		metrics.addCustomChart(new SimplePie("ban_ip", () -> String.valueOf(plugin.getConfig().getString("ban-ip"))));

		metrics.addCustomChart(new SimplePie("ban_time", () -> plugin.messageBuilder.getTimeString(MINUTES.toMillis(plugin.getConfig().getLong("ban-time")))));

		metrics.addCustomChart(new SimplePie("kick_delay", () -> plugin.messageBuilder.getTimeString(SECONDS.toMillis(plugin.getConfig().getLong("kick-delay")))));

	}

}
