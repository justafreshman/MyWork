package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.JoJo_Ke;
import com.example.administrator.mywork.bean.Joke;
import com.example.administrator.mywork.bean.postalBycodeEntity;
import com.example.administrator.mywork.bean.postalofCity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public interface Postal_Query_Service {

	/**
	 206601	错误的邮编号码
	 206602	错误的省份ID
	 206603	错误的城市ID
	 206604	错误的地区ID
	 其他系统错误
	*
	 */
	/**
	 返回参数说明：
	 名称	类型	说明
	 error_code	int	返回码
	 reason	string	返回说明
	 */

/*	postcode	string	是	邮编，如：215001
 	key	string	是	应用APPKEY(应用详细页查询)
 	page	int	否	页数，默认1
 	pagesize	int	否	每页返回，默认:20,最大不超过50
 	dtype	string	否	返回数据的格式,xml或json，默认json
* */
    @GET("postcode/query")
    Observable<HttpResult<postalBycodeEntity>> getpostalBycode(@Query("postcode") String phone_num,@Query("key") String appkey,@Query("page") String pagenum);

//	key	string	是	应用APPKEY(应用详细页查询)
//	dtype	string	否	返回数据的格式,xml或json，默认json
	@GET("postcode/pcd")
	Observable<HttpResult<List<postalofCity>>> getCity(@Query("key") String appkey);

//	pid	int	是	省份ID
//	cid	int	是	城市ID
//	did	int	否	区域ID
//	TODO 以后好了输入地名
//	q	string	否	地名关键字，如:木渎
//	key	string	是	应用APPKEY(应用详细页查询)
//	dtype	string	否	返回数据的格式,xml或json，默认json
	@GET("postcode/search")
	Observable<HttpResult<postalBycodeEntity>> getpostalBycity(@Query("pid") String pro,@Query("cid") String city,
															   @Query("did") String dis,@Query("key") String appkey);

//	请求参数说明：
//	名称	类型	必填	说明
//	key	string	是	你申请的key
//	type	string	否	类型(pic:趣图,不传默认为笑话)
//  TODO 没办法谁叫它的baseUrl是属于这个的
	@GET("joke/randJoke.php")
	Observable<HttpResult<List<JoJo_Ke>>> getRandJoke(@Query("key") String appkey,@Query("type") String type);
}
