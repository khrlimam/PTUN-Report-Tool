package com.ptun.app.apis.enpoints.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AllUsers {

    @SerializedName("Result")
    @Expose
    private boolean result;
    @SerializedName("Data")
    @Expose
    private List<User> data = null;
}
