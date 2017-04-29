package com.ptun.app.statics;

import com.ptun.app.apis.RetrofitBuilder;

/**
 * Created by Lenovo on 4/28/2017.
 */
public class Constants {
    public static final String BASE_URL = "http://192.168.8.3:7005/";
    public static final String SN = "616385016280372";
    public static final String DB_NAME = "ptunmataram";
    public static final String APP_NAME = "PTUN Report Tool";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = String.format("%s %s", DATE_PATTERN, TIME_PATTERN);
}
