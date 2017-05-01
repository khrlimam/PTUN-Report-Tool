package com.ptun.app.apis.endpoints;

import com.ptun.app.apis.RetrofitBuilder;
import com.ptun.app.apis.endpoints.models.AllScanLogs;
import com.ptun.app.apis.endpoints.models.AllUsers;
import com.ptun.app.apis.endpoints.models.Result;
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

    @FormUrlEncoded
    @POST("user/set")
    Call<Result> addNewUser(@Field("sn") String sn, @Field("pin") int pin, @Field("nama") String nama, @Field("pwd") int password, @Field("rfid") int rfid, @Field("priv") int privillage, @Field("tmp") String template);

    static EasyLinkPoints getClient() {
        return RetrofitBuilder.getInstance().create(EasyLinkPoints.class);
    }
}
