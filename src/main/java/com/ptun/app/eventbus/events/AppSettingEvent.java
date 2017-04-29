package com.ptun.app.eventbus.events;

import com.ptun.app.db.models.AppSettings;
import lombok.Data;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
public class AppSettingEvent {
    private AppSettings appSettings;

    public AppSettingEvent(AppSettings appSettings) {
        this.appSettings = appSettings;
    }
}
