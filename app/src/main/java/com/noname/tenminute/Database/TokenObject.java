package com.noname.tenminute.Database;

/**
 * Created by PJC on 2017-09-11.
 */

public class TokenObject {
    private static String token = null;
    private static TokenObject tokenObject = null;

    public static TokenObject getInstance() {
        if(tokenObject == null) {
            tokenObject = new TokenObject();
        }
        return tokenObject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String t) {
        token = t;
    }
}
