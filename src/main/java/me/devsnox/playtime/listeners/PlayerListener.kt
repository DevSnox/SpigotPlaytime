package me.devsnox.playtime.listeners

import me.devsnox.playtime.playtime.TimeManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
class PlayerListener(private val timeManager: TimeManager) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        if (!timeManager.exists(uuid)) timeManager.createPlayer(uuid)
        timeManager.loadPlayer(uuid)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        if (!timeManager.isLoaded(uuid)) return
        timeManager.unloadPlayer(uuid)
    }

}