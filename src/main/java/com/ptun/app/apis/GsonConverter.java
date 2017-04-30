package com.ptun.app.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Lenovo on 4/28/2017.
 */
public class GsonConverter {
    private static Gson gson;

    public static Gson transform() {
        if (gson == null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }
}
