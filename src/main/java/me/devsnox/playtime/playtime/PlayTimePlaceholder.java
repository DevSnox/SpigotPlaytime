package me.devsnox.playtime.playtime;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class PlayTimePlaceholder extends PlaceholderExpansion {

    private final Plugin plugin;
    private final String identifier;
    private final TimeManager timeManager;

    public PlayTimePlaceholder(final Plugin plugin, final TimeManager timeManager, final String identifier) {
        this.plugin = plugin;
        this.identifier = identifier;
        this.timeManager = timeManager;
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (identifier.equalsIgnoreCase("time_hours")) {
            return String.valueOf(TimeUnit.MILLISECONDS.toHours(this.timeManager.getPlayedTime(player.getUniqueId()).getTime()));
        }
        return "0";
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getAuthor() {
        return this.plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }
}
