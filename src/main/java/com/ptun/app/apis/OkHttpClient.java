package com.ptun.app.apis;

import java.util.concurrent.TimeUnit;

/**
 * Created by khairulimam on 08/05/17.
 */
public class OkHttpClient {
    private static okhttp3.OkHttpClient okHttpClient;

    public static okhttp3.OkHttpClient getInstance() {
        if (okHttpClient == null)
            okHttpClient = new okhttp3.OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .build();
        return okHttpClient;
    }
}
