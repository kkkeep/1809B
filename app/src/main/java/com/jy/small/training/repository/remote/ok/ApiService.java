package com.jy.small.training.repository.remote.ok;

import com.jy.small.training.repository.entity.Banner;
import com.jy.small.training.repository.entity.HttpResult;
import com.jy.small.training.repository.entity.ZhihuDailyData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

import java.util.List;

/*
 * created by taofu on 2019/5/5
 **/
public interface ApiService {

    @GET("/banner/json")
    Observable<HttpResult<List<Banner>>> getBanners();


    @GET
    Observable<ZhihuDailyData> getZhihuDailyData(@Url String url);


}
