package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PJC on 2017-09-11.
 */

public class LoginModel extends BaseModel {
    @SerializedName("token")
    public String token;

    @SerializedName("profile")
    public ProfileModel profile;
}