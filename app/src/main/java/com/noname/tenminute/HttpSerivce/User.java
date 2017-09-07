package com.noname.tenminute.HttpSerivce;

import com.noname.tenminute.Model.AoorivalModel;
import com.noname.tenminute.Model.BaseModel;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by PJC on 2017-09-05.
 */

public interface User {
    @FormUrlEncoded
    @POST("/signup/default/")
    void signup_default(
            @Field("username") String id,
            @Field("password") String password,
            @Field("profile.sex") Boolean sex,
            @Field("profile.height") int height,
            @Field("profile.region") int region,
            @Field("profile.nameWork") String nameWork,
            @Field("profile.nameWorkplace") String nameWorkplace,
            Callback<BaseModel> callback
    );

    @GET("/check/userid/")
    void check_username(
            @Query("username") String username,
            Callback<BaseModel> callback
    );

    @GET("/get/approval/")
    void getApproval(
            @Query("username") String username,
            Callback<AoorivalModel> callback
    );

    @GET("/login")
    void login(
            @Query("username") String username,
            @Query("password") String password,
            Callback<BaseModel> callback
    );
}
