package me.devsnox.playtime.listeners;

import me.devsnox.playtime.playtime.TimeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
public class PlayerListener implements Listener {

    private TimeManager timeManager;

    public PlayerListener(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(this.timeManager.exists(player.getUniqueId())) {
            this.timeManager.loadPlayer(player.getUniqueId());
        } else {
            this.timeManager.createPlayer(player.getUniqueId());
            this.timeManager.loadPlayer(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(this.timeManager.isLoaded(player.getUniqueId())) {
            this.timeManager.unloadPlayer(player.getUniqueId());
        }
    }
}
