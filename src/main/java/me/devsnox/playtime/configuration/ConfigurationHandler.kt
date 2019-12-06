package me.devsnox.playtime.configuration

import me.devsnox.playtime.utils.Messages
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

object ConfigurationHandler {

    fun configurateMessages(plugin: Plugin) {
        val configuration = YamlConfiguration.loadConfiguration(
                File(plugin.dataFolder.toString() + File.separator + "messages.yml"))
        val prefixEnabled = configuration.getBoolean("prefix-enabled")

        Messages.values().iterator().forEach {
            val stringBuilder = StringBuilder(ChatColor.translateAlternateColorCodes('&', configuration.getString(it.formatedName())))

            if (it != Messages.PREFIX && prefixEnabled)
                stringBuilder.insert(0, Messages.PREFIX.asString())
            else stringBuilder.append(ChatColor.RESET.toString() + " ")

            Messages.valueOf(it.name).set(stringBuilder.toString())
        }
    }
}