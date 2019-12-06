package me.devsnox.playtime.connection;

import me.devsnox.playtime.configuration.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
@Deprecated
public class SpigotConnection {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    private SyncMySQL syncMySQL;

    private Connection connection;


    public SpigotConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        connect();
        this.syncMySQL = new SyncMySQL(this, connection);
    }

    public SpigotConnection(ConnectionConfig config) {
        this.host = config.getHost();
        this.port = config.getPort();
        this.database = config.getDatabase();
        this.username = config.getUsername();
        this.password = config.getPassword();

        connect();

        this.syncMySQL = new SyncMySQL(this, connection);
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public SyncMySQL sync() {
        try {
            if (!this.connection.isValid(2)) {
                disconnect();
                connect();
            }
        } catch (SQLException e) {
            disconnect();
            connect();
        }
        return syncMySQL;
    }
}
