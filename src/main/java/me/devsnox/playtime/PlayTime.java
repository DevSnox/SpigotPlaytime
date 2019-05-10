package me.devsnox.playtime;

import me.devsnox.playtime.commands.PlayTimeCommand;
import me.devsnox.playtime.listeners.PlayerListener;
import me.devsnox.playtime.playtime.PlayTimePlaceholder;
import me.devsnox.playtime.playtime.TimeManager;
import me.devsnox.playtime.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
    }

    @Override
    public void onDisable() {
        this.timeManager.shutdown();
    }

    private void loadConfiguration() {
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(
                new File(this.getDataFolder() + File.separator + "config.yml"));

        final boolean prefixEnabled = configuration.getBoolean("prefix-enabled");

        for (final Messages message : Messages.values()) {
            final StringBuilder stringBuilder
                    = new StringBuilder(ChatColor.translateAlternateColorCodes('&', configuration.getString(message.formatedName())));

            if (message != Messages.PREFIX && prefixEnabled) {
                stringBuilder.insert(0, Messages.PREFIX.asString());
            } else {
                stringBuilder.append(ChatColor.RESET + " ");
            }

            Messages.valueOf(message.name()).set(stringBuilder.toString());
        }
    }

    private void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this.timeManager), this);
        this.getCommand("playtime").setExecutor(new PlayTimeCommand(this.timeManager));

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayTimePlaceholder(this, this.timeManager, "varoxtime").hook();
            new PlayTimePlaceholder(this, this.timeManager, "spigotplaytime").hook();
        }
    }
}
