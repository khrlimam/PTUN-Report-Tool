package com.ptun.app.eventbus.events;

import com.ptun.app.db.models.User;
import lombok.Data;

/**
 * Created by Lenovo on 5/1/2017.
 */
@Data
public class NewUserEvent {
    private User user;

    public NewUserEvent(User user) {
        this.user = user;
    }
}
