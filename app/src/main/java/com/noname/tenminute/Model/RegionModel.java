package com.noname.tenminute.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PJC on 2017-09-04.
 */

public class RegionModel extends BaseModel{

    @SerializedName("dataList")
    public List<RegionItem> dataList;

    public class RegionItem {
        @SerializedName("id")
        public String id;

        @SerializedName("regionName")
        public String regionName;
    }
}
