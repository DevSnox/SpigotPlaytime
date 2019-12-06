package me.devsnox.playtime.playtime

import me.clip.placeholderapi.external.EZPlaceholderHook
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
class PlayTimePlaceholder(plugin: Plugin?, private val timeManager: TimeManager, identifier: String?) : EZPlaceholderHook(plugin, identifier) {

    //TODO: Add support for all available time-units (days, hours, minutes and seconds)
    override fun onPlaceholderRequest(player: Player, identifier: String): String {
        return if (identifier.equals("time_hours", ignoreCase = true)) {
            TimeUnit.MILLISECONDS.toHours(timeManager.getPlayedTime(player.uniqueId).time).toString()
        } else "0"
    }
}