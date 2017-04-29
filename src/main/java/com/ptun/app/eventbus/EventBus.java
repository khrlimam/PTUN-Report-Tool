package com.ptun.app.eventbus;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class EventBus {
    private static com.google.common.eventbus.EventBus eventBus;

    public static com.google.common.eventbus.EventBus getDefault() {
        if (eventBus == null)
            eventBus = new com.google.common.eventbus.EventBus();
        return eventBus;
    }
}
