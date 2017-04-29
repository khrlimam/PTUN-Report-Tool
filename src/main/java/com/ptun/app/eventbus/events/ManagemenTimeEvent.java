package com.ptun.app.eventbus.events;

import com.ptun.app.db.models.TimeManagement;
import lombok.Data;

/**
 * Created by Lenovo on 4/29/2017.
 */

@Data
public class ManagemenTimeEvent {
    private TimeManagement timeManagement;

    public ManagemenTimeEvent(TimeManagement timeManagement) {
        this.timeManagement = timeManagement;
    }
}
