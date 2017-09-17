package com.noname.tenminute.Util;

import com.google.gson.Gson;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SockerIO {

    private static SockerIO sockerIO;

    private Socket socket;

    private SockerIO(){
        try {
            socket = IO.socket("http://1.231.33.100:2128");
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("에러남 ㅅㄱ" + e.getMessage());
        }
    }

    private static SockerIO getInstance(){
        if(sockerIO == null){
            sockerIO = new SockerIO();
        }
        return sockerIO;
    }

    public static void connect(){
        if(getInstance().socket.connected() == false){
            getInstance().socket.connect();
        }
    }

    public static void disconnect(){
        getInstance().socket.disconnect();
    }

    public static void emit(String event, Object value){
        getInstance().socket.emit(event, new Gson().toJson(value));
    }

    public static void on(String event, Emitter.Listener listener){
        getInstance().socket.on(event, listener);
    }

    public static final String EVENT_JOIN = "join";
    public static final String EVENT_MATCHING = "matching";
    public static final String EVENT_OFFER = "offer";
    public static final String EVENT_ANSWER = "answer";
    public static final String EVENT_ICECANDIDATE = "icecandidate";

}
