package me.devsnox.playtime

import me.devsnox.playtime.commands.PlayTimeCommand
import me.devsnox.playtime.configuration.ConfigurationHandler
import me.devsnox.playtime.listeners.PlayerListener
import me.devsnox.playtime.playtime.PlayTimePlaceholder
import me.devsnox.playtime.playtime.TimeManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
class PlayTime : JavaPlugin() {

    private val timeManager: TimeManager by lazy {
        TimeManager(this, ConfigurationHandler.configurateCredentials(this))
    }

    override fun onEnable() {
        this.saveResource("credentials.yml", false)
        this.saveResource("messages.yml", false)

        ConfigurationHandler.configurateMessages(this)

        timeManager.startup()
        registerPlaceholderAPI()

        Bukkit.getPluginManager().registerEvents(PlayerListener(timeManager), this)

        getCommand("playtime").executor = PlayTimeCommand(timeManager)
    }

    override fun onDisable() {
        timeManager.shutdown()
    }

    private fun registerPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            PlayTimePlaceholder(this, timeManager, "spigotplaytime").hook()
            //TODO: Add message if PlaceholderAPI can't be found
        }
    }
}