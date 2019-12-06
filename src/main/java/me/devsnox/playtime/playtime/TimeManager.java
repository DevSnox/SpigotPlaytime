package me.devsnox.playtime.playtime;

import lombok.Getter;
import lombok.Setter;
import me.devsnox.playtime.configuration.ConnectionConfig;
import me.devsnox.playtime.connection.SpigotConnection;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class TimeManager {

    @Getter @Setter
    private Plugin plugin;

    @Getter @Setter
    private SpigotConnection connection;

    @Getter @Setter
    private HashMap<UUID, TimePlayer> players;

    public TimeManager(final Plugin plugin, ConnectionConfig connectionConfig) {
        this.plugin = plugin;


        this.connection = new SpigotConnection(connectionConfig);

        this.players = new HashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (TimePlayer timePlayer : players.values()) {
                    timePlayer.setTime(timePlayer.getTime() + 5000L);
                }
            }
        }.runTaskTimerAsynchronously(this.plugin, 0L, 5L * 20L);

        new BukkitRunnable() {

            @Override
            public void run() {
                for(UUID uuid : players.keySet()) {
                    savePlayer(uuid);
                }
            }
        }.runTaskTimerAsynchronously(this.plugin, 20L * 60L, 20L * 60L);
    }

    public void loadPlayer(UUID uuid) {
        long playtime = 0;

        ResultSet resultSet = this.connection.sync().query("SELECT TIME FROM varoxtime_players WHERE UUID='" + uuid + "'");

        try {
            if(resultSet.next()) {
                playtime = resultSet.getLong("TIME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.players.put(uuid, new TimePlayer(uuid, playtime));
    }

    public void unloadPlayer(UUID uuid) {
        this.savePlayer(uuid);
        this.players.remove(uuid);
    }

    public boolean isLoaded(UUID uuid) {
        return this.players.containsKey(uuid);
    }

    public void savePlayer(UUID uuid) {
        this.connection.sync().preparedUpdate("UPDATE varoxtime_players SET TIME='"
                + this.players.get(uuid).getTime() + "' WHERE UUID='" + uuid + "'");
    }

    public void createPlayer(UUID uuid)  {
        this.connection.sync().preparedUpdate("INSERT INTO varoxtime_players(UUID, TIME) VALUES('" + uuid + "', '" + 0 + "')");
    }

    public TimePlayer getPlayedTime(UUID uuid) {
        if(this.players.containsKey(uuid)) {
            return this.players.get(uuid);
        } else {
            long playtime = 0;

            ResultSet resultSet = this.connection.sync().query("SELECT TIME FROM varoxtime_players WHERE UUID='" + uuid + "'");

            try {
                if(resultSet.next()) {
                    playtime = resultSet.getLong("TIME");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new TimePlayer(uuid, playtime);
        }
    }

    public boolean exists(UUID uuid) {
        ResultSet resultSet = this.connection.sync().query("SELECT TIME FROM varoxtime_players WHERE UUID='" + uuid + "'");

        try {
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void startup() {
        this.connection.connect();

        this.connection.sync().preparedUpdate("CREATE TABLE IF NOT EXISTS varoxtime_players(UUID varchar(36), TIME bigint)");
    }

    public void shutdown() {
        this.connection.disconnect();
    }
}
