package com.ptun.app.controllers.dataoperations;

import com.ptun.app.apis.endpoints.models.User;
import com.ptun.app.statics.Util;
import lombok.Data;
import tray.notification.NotificationType;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
public class DataUserOperations {
    private List<User> users;

    public DataUserOperations(List<User> allUsers) {
        this.users = allUsers;
    }

    public User findByPIN(String PIN) {
        try {
            return getUsers()
                    .stream()
                    .filter(user -> user.getPIN().equalsIgnoreCase(PIN)).findAny().orElse(new User());
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

}
