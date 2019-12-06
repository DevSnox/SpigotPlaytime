package me.devsnox.playtime.commands

import me.devsnox.playtime.playtime.TimeManager
import me.devsnox.playtime.utils.Messages
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
class PlayTimeCommand(private val timeManager: TimeManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (args.isEmpty()) {

            if (sender !is Player) {
                sender.sendMessage(Messages.ERROR_ONLY_PLAYERS.asString())
                return true
            }

            if (!sender.hasPermission("spigotplaytime.command.playtime")) {
                //TODO: Add no permissions message
                return true
            }

            sender.sendMessage(replaceValues(Messages.PLAYTIME.asString(),
                        timeManager.getPlayedTime(sender.uniqueId).time))

            return true

        } else if (args.size == 1) {
            if (!sender.hasPermission("spigotplaytime.command.playtime.other")) {

                //TODO: Add no permissions message

                return true
            }

            //TODO: Better solution for getting the targets UUID

            if (timeManager.exists(Bukkit.getOfflinePlayer(args[0]).uniqueId)) {
                sender.sendMessage(replaceValues(Messages.PLAYTIME_OTHER.asString()
                        .replace("%target%".toRegex(), args[0]),
                        timeManager.getPlayedTime(Bukkit.getOfflinePlayer(args[0]).uniqueId).time))
            } else {
                sender.sendMessage(Messages.ERROR_UNKNOWN_PLAYER.asString())
            }

            return true
        } else {
            sender.sendMessage(Messages.ERROR_COMMAND_FORMAT_INVALID.asString())
            return true
        }

        return false
    }

    private fun replaceValues(input: String, milliseconds: Long): String {
        var input = input
        var milliseconds = milliseconds

        val timeUnits = arrayOf(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES, TimeUnit.SECONDS)

        timeUnits.forEach {
            val formatedName = it.toString().toLowerCase()

            if (formatedName in input) {
                val value = it.convert(milliseconds, TimeUnit.MILLISECONDS)

                milliseconds -= TimeUnit.MILLISECONDS.convert(value, it)

                input = input.replaceFirst("%" + formatedName + "%".toRegex(), value.toString())
            }
        }

        return input
    }
}