package com.noname.tenminute.Util;

import android.content.Context;

public class HttpUtil {

    public static Context context;

    public static <T> T api(Class<T> cls) {
        return NetworkManager.getInstance().getAPI(cls);
    }
}
