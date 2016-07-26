package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.busstationEntity;
import com.example.administrator.mywork.bean.busstationMessEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public interface bus_station_Query_Service {
//    错误码	说明
//    208201	汽车站次不能为空
//    208202	查询不到该汽车站相关信息
//    208203	网络错误，请重试
//    208204	汽车始发终点站不能为空
//    208205	查询不到该汽车行程相关信息

//    请求参数说明：
//    名称	类型	必填	说明
//    station	string	是	城市名称，如:北京
//    key	string	是	应用APPKEY(应用详细页查询)
    @GET("query")
    Observable<HttpResult<busstationEntity>> getBusStation(@Query("station") String city,@Query("key") String appkey);


//    名称	类型	必填	说明
//    from	string	是	出发城市，如：上海
//    to	string	是	到达城市，如:苏州
//    key	string	是	应用APPKEY(应用详细页查询)
    @GET("query_ab")
    Observable<HttpResult<busstationMessEntity>> getBusStation(@Query("from") String from,@Query("to") String to, @Query("key") String appkey);
}
