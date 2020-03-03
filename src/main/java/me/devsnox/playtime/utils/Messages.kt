package me.devsnox.playtime.utils

/**
 * Created by Yasin Dalal
 * Copyright (c) 2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
enum class Messages {

    PREFIX,
    PLAYTIME,
    PLAYTIME_OTHER,
    ERROR_UNKNOWN_PLAYER,
    ERROR_ONLY_PLAYERS,
    ERROR_COMMAND_FORMAT_INVALID;

    private val messages: MutableMap<Messages, String> = mutableMapOf()

    fun formatedName(): String {
        return name.toLowerCase().replace("_".toRegex(), "-")
    }

    fun set(value: String) {
        messages[this] = value
    }

    fun asString(): String {
        return messages[this].orEmpty()
    }
}