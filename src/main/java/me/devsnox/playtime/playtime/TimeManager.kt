package me.devsnox.playtime.playtime

import me.devsnox.playtime.configuration.ConnectionConfig
import me.devsnox.playtime.connection.SpigotConnection
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */

//TODO: Change the name of the sql table
//TODO: Use exposed for database handling
class TimeManager(plugin: Plugin, connectionConfig: ConnectionConfig?) {

    private val connection: SpigotConnection = SpigotConnection(connectionConfig)
    private val players: MutableMap<UUID, TimePlayer> = mutableMapOf()

    init {
        object : BukkitRunnable() {
            override fun run() {
                for (timePlayer in players.values)
                    timePlayer.time = timePlayer.time + 5000L
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 5L * 20L)
        object : BukkitRunnable() {
            override fun run() {
                for (uuid in players.keys) savePlayer(uuid)
            }
        }.runTaskTimerAsynchronously(plugin, 20L * 60L, 20L * 60L)
    }

    fun loadPlayer(uuid: UUID) {
        players[uuid] = this.getPlayedTime(uuid)
    }

    fun unloadPlayer(uuid: UUID) {
        savePlayer(uuid)
        players -= uuid
    }

    fun isLoaded(uuid: UUID?): Boolean {
        return players.containsKey(uuid)
    }

    fun savePlayer(uuid: UUID) {
        connection.sync().preparedUpdate("UPDATE varoxtime_players SET TIME='"
                + players[uuid]?.time + "' WHERE UUID='" + uuid + "'")
    }

    fun createPlayer(uuid: UUID) {
        connection.sync().preparedUpdate("INSERT INTO varoxtime_players(UUID, TIME) VALUES('$uuid', '0')")
    }

    fun getPlayedTime(uuid: UUID): TimePlayer = if (players.containsKey(uuid)) {
        players.getValue(uuid)
    } else {
        var playtime: Long = 0
        val resultSet = connection.sync().query("SELECT TIME FROM varoxtime_players WHERE UUID='$uuid'")
        if (resultSet.next()) playtime = resultSet.getLong("TIME")
        TimePlayer(uuid, playtime)
    }

    fun exists(uuid: UUID): Boolean {
        val resultSet = connection.sync().query("SELECT TIME FROM varoxtime_players WHERE UUID='$uuid'")
        if (resultSet.next()) return true
        return false
    }

    fun startup() {
        connection.connect()
        connection.sync().preparedUpdate("CREATE TABLE IF NOT EXISTS varoxtime_players(UUID varchar(36), TIME bigint)")
    }

    fun shutdown() {
        connection.disconnect()
    }
}