package me.devsnox.playtime;

import me.devsnox.playtime.commands.PlayTimeCommand;
import me.devsnox.playtime.listeners.PlayerListener;
import me.devsnox.playtime.varoxtime.TimeManager;
import me.devsnox.playtime.varoxtime.PlayTimePlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class PlayTime extends JavaPlugin {

    private TimeManager timeManager;

    @Override
    public void onEnable() {
        this.timeManager = new TimeManager(this);

        this.timeManager.startup();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this.timeManager), this);

        this.getCommand("playtime").setExecutor(new PlayTimeCommand(this.timeManager));

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayTimePlaceholder(this, this.timeManager).hook();
        }
    }

    @Override
    public void onDisable() {
        this.timeManager.shutdown();
    }
}
