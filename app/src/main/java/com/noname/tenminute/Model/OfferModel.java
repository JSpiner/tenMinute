package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import org.webrtc.SessionDescription;

public class OfferModel {

    @SerializedName("targetusername")
    public String targetusername;

    @SerializedName("sdp")
    public SessionDescription sdp;

    public OfferModel(String targetusername, SessionDescription sdp){
        this.targetusername = targetusername;
        this.sdp = sdp;
    }

}
