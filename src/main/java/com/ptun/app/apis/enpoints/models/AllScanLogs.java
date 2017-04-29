package com.ptun.app.apis.enpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class AllScanLogs {
    @SerializedName("Result")
    @Expose
    private boolean result;
    @SerializedName("Data")
    @Expose
    private List<Scan> data = null;
}
