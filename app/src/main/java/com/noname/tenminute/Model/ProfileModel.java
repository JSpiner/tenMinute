package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PJC on 2017-09-11.
 */

public class ProfileModel implements Serializable{
    @SerializedName("loginType")
    public int loginType;

    @SerializedName("sex")
    public Boolean sex;

    @SerializedName("height")
    public int height;

    @SerializedName("approval")
    public Boolean approval;
}
