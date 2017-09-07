package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PJC on 2017-09-04.
 */

public class LikeThingModel extends BaseModel {

    @SerializedName("dataList")
    public List<LikeItem> dataList;

    public class LikeItem {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;
    }
}
