package com.ptun.app.apis.endpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.endpoints.EasyLinkPoints;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import lombok.Data;
import tray.notification.NotificationType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Data
public class AllScanLogs {
    @SerializedName("Result")
    @Expose
    private boolean result;
    @SerializedName("Data")
    @Expose
    private List<Scan> data = null;

    public static List<Scan> getLocalData() {
        return GsonConverter
                .transform()
                .fromJson(new InputStreamReader(AllScanLogs.class.getResourceAsStream("/dataexample/scanlog.json")), AllScanLogs.class).getData();
    }

    public static List<Scan> getMachineData() {
        try {
            return EasyLinkPoints.getClient().allScanLogs(Constants.SN).execute().body().getData();
        } catch (IOException e) {
            Util.showNotif("Error", e.getMessage(), NotificationType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

}
