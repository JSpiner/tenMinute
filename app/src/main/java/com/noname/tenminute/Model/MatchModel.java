package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import org.webrtc.SessionDescription;

public class MatchModel {

    @SerializedName("sdp")
    public SessionDescription sdp;

    @SerializedName("requestType")
    public String requestType;

    @SerializedName("username")
    public String username;

}
