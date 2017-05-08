package com.ptun.app.apis;

import com.ptun.app.statics.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 4/28/2017.
 */
public class RetrofitBuilder {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .client(com.ptun.app.apis.OkHttpClient.getInstance())
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonConverter.transform()))
                    .build();
        return retrofit;
    }
}
