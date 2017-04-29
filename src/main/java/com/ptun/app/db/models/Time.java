package com.ptun.app.db.models;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
public class Time implements Serializable {
    private String time;

    public Time(String time) {
        this.time = time;
    }
}
