package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PJC on 2017-09-04.
 */

public class BaseModel {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
}
