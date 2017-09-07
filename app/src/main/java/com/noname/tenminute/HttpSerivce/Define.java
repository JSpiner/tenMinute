package com.noname.tenminute.HttpSerivce;

import com.noname.tenminute.Model.LikeThingModel;
import com.noname.tenminute.Model.RegionModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by PJC on 2017-09-04.
 */

public interface Define {
    @GET("/define/get_like/") void getLike(Callback<LikeThingModel> callback);
    @GET("/define/get_region/") void getRegion(Callback<RegionModel> callback);
}
