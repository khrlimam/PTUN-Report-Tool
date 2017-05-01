package com.ptun.app.controllers.dataoperations;

import com.ptun.app.apis.enpoints.models.User;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import lombok.Data;

import java.util.List;

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
        return getUsers()
                .stream()
                .filter(user -> user.getPIN().equalsIgnoreCase(PIN)).findAny().get();
    }

}
