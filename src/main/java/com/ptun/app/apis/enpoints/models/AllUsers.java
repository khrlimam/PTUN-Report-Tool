package com.ptun.app.apis.enpoints.models;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.enpoints.EasyLinkPoints;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import lombok.Data;
import tray.notification.NotificationType;

@Data
public class AllUsers {

    @SerializedName("Result")
    @Expose
    private boolean result;
    @SerializedName("Data")
    @Expose
    private List<User> data = null;

    public static List<User> getLocalData() {
        return GsonConverter
                .getIt()
                .fromJson(new InputStreamReader(AllUsers.class.getResourceAsStream("/dataexample/users.json")), AllUsers.class).data;
    }

    public static List<User> getMachineData() {
        try {
            return EasyLinkPoints.getClient().allUsers(Constants.SN).execute().body().getData();
        } catch (IOException e) {
            Util.showNotif("Error", e.getMessage(), NotificationType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

}
