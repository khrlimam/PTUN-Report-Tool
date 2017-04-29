package com.ptun.app.apis.enpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import lombok.Data;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

@Data
public class Scan {
    @SerializedName("SN")
    @Expose
    private String SN;
    @SerializedName("ScanDate")
    @Expose
    private String scanDate;
    @SerializedName("PIN")
    @Expose
    private String PIN;
    @SerializedName("VerifyMode")
    @Expose
    private int verifyMode;
    @SerializedName("IOMode")
    @Expose
    private int iOMode;
    @SerializedName("WorkCode")
    @Expose
    private int workCode;

    public DateTime getScanDate() {
        if (this.scanDate == null || scanDate.length() <= 0)
            return null;
        return Util.getDateFromString(this.scanDate);
    }

    public String getScanDateString() {
        if (getScanDate() == null)
            return "";
        return this.getScanDate().toString(DateTimeFormat.forPattern(Constants.DATE_PATTERN));
    }

    public String getScanTime() {
        if (getScanDate() == null)
            return "";
        return this.getScanDate().toString(DateTimeFormat.forPattern(Constants.HOUR_MINUTE_PATTERN));
    }

    public String getLate(int tolerance, String time) {
        try {
            DateTime startTime = getScanDate().withTime(LocalTime.parse(time)).plusMinutes(tolerance);
            DateTime endTime = getScanDate();
            return Util.getTimeLaps(startTime, endTime);
        } catch (NullPointerException e) {
            return "";
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    public String getEarly(int tolerance, String time) {
        try {
            DateTime start = getScanDate().withTime(LocalTime.parse(time)).minusMinutes(tolerance);
            DateTime end = getScanDate();
            System.out.println(Days.daysBetween(end, start));
            return Util.getTimeLaps(end, start);
        } catch (NullPointerException e) {
            return "";
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    public String getAbsen() {
        return "";
    }

}
