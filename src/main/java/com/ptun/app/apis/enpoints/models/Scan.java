package com.ptun.app.apis.enpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import lombok.Data;
import org.joda.time.DateTime;
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
        return Util.getDateFromString(this.scanDate);
    }

    public String getScanDateString() {
        return this.getScanDate().toString(DateTimeFormat.forPattern(Constants.DATE_PATTERN));
    }
}
