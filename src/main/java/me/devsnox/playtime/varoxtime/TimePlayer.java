package me.devsnox.playtime.varoxtime;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class TimePlayer {

    @Getter @Setter
    private UUID uuid;

    @Getter @Setter
    private long time;

    public TimePlayer(UUID uuid, long time) {
        this.uuid = uuid;
        this.time = time;
    }
}
