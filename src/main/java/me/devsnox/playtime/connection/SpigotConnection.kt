package me.devsnox.playtime.connection

import me.devsnox.playtime.configuration.ConnectionConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */

@Deprecated("Will be replaced by Exposed")
class SpigotConnection {
    private var host: String
    private var port: Int
    private var database: String
    private var username: String
    private var password: String
    private var syncMySQL: SyncMySQL
    private var connection: Connection? = null

    constructor(host: String, port: Int, database: String, username: String, password: String) {
        this.host = host
        this.port = port
        this.database = database
        this.username = username
        this.password = password
        connect()
        syncMySQL = SyncMySQL(this, connection)
    }

    constructor(config: ConnectionConfig) {
        host = config.host
        port = config.port
        database = config.database
        username = config.username
        password = config.password
        connect()
        syncMySQL = SyncMySQL(this, connection)
    }

    fun connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            connection!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    val isConnected: Boolean
        get() = connection != null

    fun sync(): SyncMySQL {
        try {
            if (!connection!!.isValid(2)) {
                disconnect()
                connect()
            }
        } catch (e: SQLException) {
            disconnect()
            connect()
        }
        return syncMySQL
    }
}