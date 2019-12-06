package me.devsnox.playtime

import me.devsnox.playtime.commands.PlayTimeCommand
import me.devsnox.playtime.configuration.ConfigurationHandler
import me.devsnox.playtime.listeners.PlayerListener
import me.devsnox.playtime.playtime.PlayTimePlaceholder
import me.devsnox.playtime.playtime.TimeManager
import me.devsnox.playtime.utils.Messages
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
class PlayTime : JavaPlugin() {

    private val timeManager: TimeManager by lazy { TimeManager(this) }

    override fun onEnable() {
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
            PlayTimePlaceholder(this, timeManager, "varoxtime").hook()
            PlayTimePlaceholder(this, timeManager, "spigotplaytime").hook()
        }
    }
}