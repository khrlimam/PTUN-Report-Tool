package com.ptun.app.apis.enpoints;

import com.ptun.app.apis.RetrofitBuilder;
import com.ptun.app.apis.enpoints.models.AllScanLogs;
import com.ptun.app.apis.enpoints.models.AllUsers;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Lenovo on 4/28/2017.
 */
public interface EasyLinkPoints {
    @FormUrlEncoded
    @POST("user/all")
    Call<AllUsers> allUsers(@Field("sn") String sn);

    @FormUrlEncoded
    @POST("scanlog/all")
    Call<AllScanLogs> allScanLogs(@Field("sn") String sn);

    @FormUrlEncoded
    @POST("scanlog/new")
    Call<AllScanLogs> newScanLogs(@Field("sn") String sn);

    static EasyLinkPoints getClient() {
        return RetrofitBuilder.getInstance().create(EasyLinkPoints.class);
    }
}
