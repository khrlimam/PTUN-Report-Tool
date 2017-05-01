package com.ptun.app.controllers.dataoperations;

import com.ptun.app.apis.enpoints.models.User;
import com.ptun.app.enums.PEGAWAI_CHOICES;
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
                    .filter(user -> user.getPIN().equalsIgnoreCase(PIN)).findAny().get();
        }catch (NoSuchElementException e) {
            Util.showNotif("Error", String.format("Ada kesalahan %s", e.getMessage()), NotificationType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

}
