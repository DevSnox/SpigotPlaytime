package me.devsnox.playtime.configuration

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
data class ConnectionConfig(
        val host: String,
        val port: Int,
        val database: String,
        val username: String,
        val password: String
)