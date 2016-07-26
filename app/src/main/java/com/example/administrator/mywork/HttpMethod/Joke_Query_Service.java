package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.Joke;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/12.
 * 作者：wu
 */
//笑话大全的接口
public interface Joke_Query_Service {
    /**
     服务级错误码参照(error_code)：
     错误码	说明
     209501	必须为10位时间戳
     209502	page、pagesize必须为int类型,time为10位时间戳
     */

    /**
     请求参数说明：
     名称	类型	必填	说明
     sort	string	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
     page	int	否	当前页数,默认1
     pagesize	int	否	每次返回条数,默认1,最大20
     time	string	是	时间戳（10位），如：1418816972
     key	string	是	您申请的key
     */
    @GET("content/list.from")
    Observable<HttpResult<Joke>> getJokebyTime(@Query("sort") String type,
                                                           @Query("page") String pagenum,
                                                           @Query("time") String time,
                                                           @Query("key") String app_key,
                                               @Query("pagesize") String size);

    /**
     请求参数说明：
     名称	类型	必填	说明
     page	int	否	当前页数,默认1
     pagesize	int	否	每次返回条数,默认1,最大20
     key	string	是	您申请的key
     */
    @GET("content/text.from")
    Observable<HttpResult<Joke>> getLastestJoke(@Query("page") String pagenum,
            @Query("key") String app_key,@Query("pagesize") String size);

    /**
     请求参数说明：
     名称	类型	必填	说明
     sort	string	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
     page	int	否	当前页数,默认1
     pagesize	int	否	每次返回条数,默认1,最大20
     time	string	是	时间戳（10位），如：1418816972
     key	string	是	您申请的key
     */
    @GET("img/list.from")
    Observable<HttpResult<Joke>> getImagebyTime(@Query("sort") String type,
                                                      @Query("page") String pagenum,
                                                      @Query("time") String time,
                                                      @Query("key") String app_key,
                                                      @Query("pagesize") String size);

    /**
     请求参数说明：
     名称	类型	必填	说明
     page	int	否	当前页数,默认1
     pagesize	int	否	每次返回条数,默认1,最大20
     key	string	是	您申请的key
     */
    @GET("img/text.from")
    Observable<HttpResult<Joke>> getLastestImage(@Query("page") String pagenum,
                                                 @Query("key") String app_key,@Query("pagesize") String size);
}
