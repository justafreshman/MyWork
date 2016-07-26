package com.example.administrator.mywork.HttpMethod;

/**
 * Created by Administrator on 2016/7/16.
 * 作者：wu
 */

import com.example.administrator.mywork.bean.History_Detail_Bean;
import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.history_bean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 请求参数说明：
 名称	类型	必填	说明
 key	string	是	你申请的key
 date	string	是	日期,格式:月/日 如:1/1,/10/1,12/12 如月或者日小于10,前面无需加0

 */
public interface History_Query_Service {
     @GET("todayOnhistory/queryEvent.php")
     Observable<HttpResult<List<history_bean>>> getHistory(@Query("key") String key ,@Query("date") String date);


/**
  请求参数说明：
    名称	类型	必填	说明
    key	string	是	你申请的key
    e_id	int	是	事件id
    */
     @GET("todayOnhistory/queryDetail.php")
     Observable<HttpResult<List<History_Detail_Bean>>> getHistory_Detail(@Query("key") String key ,@Query("e_id") String e_id);


}
