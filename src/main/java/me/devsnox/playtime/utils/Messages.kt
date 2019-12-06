package me.devsnox.playtime.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yasin Dalal
 * Copyright (c) 2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
 */
public enum Messages {

    PREFIX,
    PLAYTIME,
    PLAYTIME_OTHER,
    ERROR_UNKNOWN_PLAYER,
    ERROR_ONLY_PLAYERS,
    ERROR_COMMAND_FORMAT_INVALID;


    private final Map<Messages, String> messages;

    Messages() {
        this.messages = new HashMap<>();
    }

    public String formatedName() {
        return this.name().toLowerCase().replaceAll("_", "-");
    }

    public void set(String value) {
        this.messages.put(this, value);
    }

    public String asString() {
        return this.messages.get(this);
    }
}
