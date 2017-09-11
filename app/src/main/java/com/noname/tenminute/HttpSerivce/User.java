package com.noname.tenminute.HttpSerivce;

import com.noname.tenminute.Model.AoorivalModel;
import com.noname.tenminute.Model.BaseModel;
import com.noname.tenminute.Model.LoginModel;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FieldMap;
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
    @POST("/register_user/")
    void signup_default(
            @Field("username") String id,
            @Field("password") String password,
            @Field("profile.loginType") int loginType,
            @FieldMap Map<String, String> params,
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
            Callback<LoginModel> callback
    );
}
