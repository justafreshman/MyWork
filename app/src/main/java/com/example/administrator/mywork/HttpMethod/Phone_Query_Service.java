package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.phoneEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public interface Phone_Query_Service {
/**
    错误码	说明
    201101	手机号码不能为空
    201102	错误的手机号码
    201103	查询无结果
 */
    /**
     请求参数说明：
     名称	类型	必填	说明
     phone	int	是	需要查询的手机号码或手机号码前7位
     key	string	是	应用APPKEY(应用详细页查询)
     dtype	string	否	返回数据的格式,xml或json，默认json
     */

    /**
     返回参数说明：
     名称	类型	说明
     error_code	int	返回码
     reason	string	返回说明
     result	string	返回结果集
     province	string	省份
     city	string	城市
     areacode	string	区号
     zip	string	邮编
     company	string	运营商
     card	string	卡类型
     */
    @GET("mobile/get")
    Observable<HttpResult<phoneEntity>> getphone_mess(@Query("phone") String phone_num,@Query("key") String appkey);

}
