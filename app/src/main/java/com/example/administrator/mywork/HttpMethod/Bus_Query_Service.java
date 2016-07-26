package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.busLine;
import com.example.administrator.mywork.bean.busstation;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/15.
 * 作者：wu
 */
public interface Bus_Query_Service {
    /**
     请求参数说明：
     名称	类型	必填	说明
     key	string	是	应用APPKEY(应用详细页查询)
     dtype	string	否	返回数据的格式,xml或json，默认json
     city	string	是	城市名称(如：苏州)或者城市代码（如：0512）
     bus	string	是	公交线路
     */
    @GET("busline")
    Observable<HttpResult<List<busLine>>> QueryBusLine(@Query("key") String appkey,
                                                 @Query("city") String city,@Query("bus") String line);

    /**
     * 请求参数说明：
     名称	类型	必填	说明
     key	string	是	应用APPKEY(应用详细页查询)
     dtype	string	否	返回数据的格式,xml或json，默认json
     city	string	是	城市名称(如：苏州)或者城市代码（如：0512）
     station	string	是	公交站台名称
     */
    @GET("station")
    Observable<HttpResult<List<busstation>>> QueryBusStation(@Query("key") String appkey,
                                                 @Query("city") String city,@Query("station") String station);
}
