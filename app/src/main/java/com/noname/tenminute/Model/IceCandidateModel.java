package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import org.webrtc.IceCandidate;

public class IceCandidateModel {

    @SerializedName("targetusername")
    public String targetusername;

    @SerializedName("icecandidate")
    public IceCandidate iceCandidate;

    public IceCandidateModel(String targetusername, IceCandidate iceCandidate){
        this.targetusername = targetusername;
        this.iceCandidate = iceCandidate;
    }

}
