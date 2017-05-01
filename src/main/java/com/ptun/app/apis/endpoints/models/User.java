package com.ptun.app.apis.endpoints.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class User {

    @SerializedName("PIN")
    @Expose
    private String pIN;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("RFID")
    @Expose
    private String rFID;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Privilege")
    @Expose
    private int privilege;
    @SerializedName("Template")
    @Expose
    private List<Template> template = null;

    @Override
    public String toString() {
        return String.format("PIN: %s, Nama: %s", getPIN(), getName());
    }

}
