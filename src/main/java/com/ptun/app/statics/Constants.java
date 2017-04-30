package com.ptun.app.statics;

import com.ptun.app.apis.RetrofitBuilder;
import com.ptun.app.controllers.ScanLogController;

/**
 * Created by Lenovo on 4/28/2017.
 */
public class Constants {
    public static final String MAIN_UI = "/main.fxml";
    public static final String LOGIN_UI = "/login.fxml";
    public static final String APP_SETTING_UI = "/appsettings.fxml";
    public static final String MANAGEMEN_TIME_UI = "/timemanagement.fxml";
    public static final String SCAN_LOG_UI = "/scanlog.fxml";

    public static final String BASE_URL = ScanLogController.BASE_URL;
    public static final String INIT_BASE_URL = "http://192.168.8.3:7005/";
    public static final String SN = "616385016280372";
    public static final String DB_NAME = "ptunmataram";
    public static final String APP_NAME = "PTUN Report Tool";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String HOUR_MINUTE_PATTERN = "HH:mm";
    public static final String DATE_TIME_PATTERN = String.format("%s %s", DATE_PATTERN, TIME_PATTERN);
    public static final int SETTING_ID = 1;
    public static final long secondsInMilli = 1000;
    public static final long minutesInMilli = secondsInMilli * 60;
    public static final long hoursInMilli = minutesInMilli * 60;
    public static final long daysInMilli = hoursInMilli * 24;
}
