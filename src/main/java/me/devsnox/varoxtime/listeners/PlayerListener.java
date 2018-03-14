package me.devsnox.varoxtime.listeners;

import me.devsnox.varoxtime.varoxtime.TimeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
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
