package me.devsnox.playtime.configuration

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
data class ConnectionConfig(
        val host: String,
        val port: Int,
        val database: String,
        val username: String,
        val password: String
)