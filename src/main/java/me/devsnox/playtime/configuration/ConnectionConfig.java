package me.devsnox.playtime.configuration;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class ConnectionConfig {

    private File directory;
    private File config;

    private YamlConfiguration yamlConfiguration;

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public ConnectionConfig(String directory, String file) {
        this.directory = new File(directory);
        this.config = new File(file);
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(config);
        if (!this.directory.exists()) {
            this.directory.mkdir();
            write();
        } else if (!config.exists()) {
            write();
        }

        this.host = yamlConfiguration.getString("mysql.host");
        this.port = yamlConfiguration.getInt("mysql.port");
        this.database = yamlConfiguration.getString("mysql.database");
        this.username = yamlConfiguration.getString("mysql.username");
        this.password = yamlConfiguration.getString("mysql.password");
    }

    private void write() {
        try {
            config.createNewFile();
            this.yamlConfiguration.set("mysql.host", "localhost");
            this.yamlConfiguration.set("mysql.port", 3306);
            this.yamlConfiguration.set("mysql.database", "database");
            this.yamlConfiguration.set("mysql.username", "root");
            this.yamlConfiguration.set("mysql.password", "");
            this.yamlConfiguration.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
