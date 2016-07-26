package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.identityMessEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public interface Identity_Query_Service {

    /**
     *
     错误码	说明
     203801	请输入正确的15或18位身份证
     203802	错误的身份证或无结果
     203803	身份证校验位不正确
     203804	查询失败
     */

//    cardno	string	Y	身份证号码
//    dtype	string	Y	返回数据格式：json或xml,默认json
//    key	string	Y	你申请的key

//    返回参数说明：
//    名称	类型	说明
//    error_code	int	返回码
//    reason	string	返回说明
//    data	-	返回结果集
//    　　area	string	地区
//    　　sex	string	性别
//    　　birthday	string	出生日期
    @GET("idcard/index")
    Observable<HttpResult<identityMessEntity>> getidentityMess(@Query("cardno") String cardnum,@Query("key") String appkey);


//    请求参数说明：
//    名称	类型	必填	说明
//    cardno	string	Y	身份证号码
//    dtype	string	Y	返回数据格式：json或xml,默认json
//    key	string	Y	你申请的key
//    返回参数说明：
//    名称	类型	说明
//    error_code	int	返回码
//    reason	string	返回说明
//    data	-	返回结果集
//    　　res	string	结果,1:安全 2：疑似泄漏 3：未知
//    　　tips	string	说明

//    http://apis.juhe.cn/idcard/leak
    @GET("idcard/leak")
    Observable<HttpResult<identityMessEntity>> IdentityLessOrNot(@Query("cardno") String cardno,@Query("key") String appkey);
}
