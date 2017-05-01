package com.ptun.app.apis.endpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Template {

    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("idx")
    @Expose
    private int idx;
    @SerializedName("alg_ver")
    @Expose
    private int algVer;
    @SerializedName("template")
    @Expose
    private String template;
}
