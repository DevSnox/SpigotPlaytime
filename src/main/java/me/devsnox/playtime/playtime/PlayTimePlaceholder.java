package me.devsnox.playtime.playtime;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
public class PlayTimePlaceholder extends EZPlaceholderHook {

    private TimeManager timeManager;

    public PlayTimePlaceholder(Plugin plugin, TimeManager timeManager, String identifier) {
        super(plugin, identifier);
        this.timeManager = timeManager;
    }

    //TODO: Add support for all available time-units (days, hours, minutes and seconds)

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("time_hours")) {
            return String.valueOf(TimeUnit.MILLISECONDS.toHours(this.timeManager.getPlayedTime(player.getUniqueId()).getTime()));
        }
        return "0";
    }
}
