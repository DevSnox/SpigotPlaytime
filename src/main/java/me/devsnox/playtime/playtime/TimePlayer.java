package me.devsnox.playtime.playtime;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Yasin Dalal
 * Copyright (c) 2017-2019 Yasin Dalal
 * GitHub: https://github.com/DevSnox
 * E-Mail: yasin@dalal.ch
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
