package me.devsnox.playtime.playtime;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class PlayTimePlaceholder extends EZPlaceholderHook {

    private TimeManager timeManager;

    public PlayTimePlaceholder(Plugin plugin, TimeManager timeManager, String identifier) {
        super(plugin, identifier);
        this.timeManager = timeManager;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("time_hours")) {
            return String.valueOf(TimeUnit.MILLISECONDS.toHours(this.timeManager.getPlayedTime(player.getUniqueId()).getTime()));
        }
        return "0";
    }
}
