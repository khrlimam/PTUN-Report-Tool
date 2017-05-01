package com.ptun.app.apis.endpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by khairulimam on 01/05/17.
 */
@Data
public class Result {
    @SerializedName("Result")
    @Expose
    private boolean result;
}
