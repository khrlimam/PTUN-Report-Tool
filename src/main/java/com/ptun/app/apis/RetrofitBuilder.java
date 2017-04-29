package com.ptun.app.apis;

import com.ptun.app.statics.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 4/28/2017.
 */
public class RetrofitBuilder {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonConverter.getIt()))
                    .build();
        }
        return retrofit;
    }
}
