package me.devsnox.playtime.utils;

import java.util.HashMap;
import java.util.Map;

public enum Messages {

    PREFIX,
    PLAYTIME,
    PLAYTIME_OTHER,
    ERROR_UNKOWN_PLAYER;


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
