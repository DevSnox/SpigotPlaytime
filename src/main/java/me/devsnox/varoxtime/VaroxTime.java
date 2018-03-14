package me.devsnox.varoxtime;

import me.devsnox.varoxtime.commands.PlayTimeCommand;
import me.devsnox.varoxtime.listeners.PlayerListener;
import me.devsnox.varoxtime.varoxtime.TimeManager;
import me.devsnox.varoxtime.varoxtime.VaroxTimePlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class VaroxTime extends JavaPlugin {

    private TimeManager timeManager;

    @Override
    public void onEnable() {
        this.timeManager = new TimeManager(this);

        this.timeManager.startup();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this.timeManager), this);

        this.getCommand("playtime").setExecutor(new PlayTimeCommand(this.timeManager));

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new VaroxTimePlaceholder(this, this.timeManager).hook();
        }
    }

    @Override
    public void onDisable() {
        this.timeManager.shutdown();
    }
}
