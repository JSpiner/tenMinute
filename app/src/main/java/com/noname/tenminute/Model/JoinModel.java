package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import org.webrtc.SessionDescription;

public class JoinModel {

    @SerializedName("sex")
    public String sex;

    @SerializedName("username")
    public String username;

    public JoinModel(String username, Boolean sex){
        this.username = username;
        this.sex = sex ? "M" : "F";
    }

}
