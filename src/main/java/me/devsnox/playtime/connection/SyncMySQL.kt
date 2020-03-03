package me.devsnox.playtime.connection

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
@Deprecated("")
class SyncMySQL(private val spigotConnection: SpigotConnection, private val connection: Connection?) {
    fun update(statment: String?) {
        refresh()
        if (statment != null) {
            try {
                val st = connection!!.createStatement()
                st.executeUpdate(statment)
                st.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun preparedUpdate(statment: String?) {
        refresh()
        if (statment != null) {
            try {
                val st = connection!!.prepareStatement(statment)
                st.executeUpdate(statment)
                st.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun preparedUpdate(statment: PreparedStatement?) {
        refresh()
        if (statment != null) {
            try {
                statment.executeUpdate()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun query(statment: String?): ResultSet? {
        refresh()
        return if (connection != null) {
            if (statment == null) {
                null
            } else {
                var resultSet: ResultSet? = null
                try {
                    val st = connection.createStatement()
                    resultSet = st.executeQuery(statment)
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
                resultSet
            }
        } else null
    }

    private fun refresh() {
        try {
            if (!connection!!.isValid(2) || connection.metaData == null) {
                spigotConnection.disconnect()
                spigotConnection.connect()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}