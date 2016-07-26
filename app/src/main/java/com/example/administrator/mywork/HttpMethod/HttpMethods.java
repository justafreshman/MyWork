package com.example.administrator.mywork.HttpMethod;

import android.util.Log;

import com.example.administrator.mywork.APP;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.bean.History_Detail_Bean;
import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.JoJo_Ke;
import com.example.administrator.mywork.bean.Joke;
import com.example.administrator.mywork.bean.busLine;
import com.example.administrator.mywork.bean.busstation;
import com.example.administrator.mywork.bean.busstationEntity;
import com.example.administrator.mywork.bean.busstationMessEntity;
import com.example.administrator.mywork.bean.driver;
import com.example.administrator.mywork.bean.history_bean;
import com.example.administrator.mywork.bean.identityMessEntity;
import com.example.administrator.mywork.bean.phoneEntity;
import com.example.administrator.mywork.bean.postalBycodeEntity;
import com.example.administrator.mywork.bean.postalofCity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class HttpMethods {
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";


    private List<Retrofit> list_Retrofit = new ArrayList<>();
    private  Phone_Query_Service mPhone_service;
    private final Identity_Query_Service mIdentity_query_service;
    private final Postal_Query_Service mPostal_query_service;
    private final Drivers_license_exam_Query_Service mDrivers_license_exam_query_service;
    private final bus_station_Query_Service mBus_station_query_service;
    private OkHttpClient.Builder mHttpClient;
    private final Joke_Query_Service mJoke_query_service;
    private final Bus_Query_Service mBus_query_service;
    private final History_Query_Service mHistory_query_service;


    //    私有构造方法
    private  HttpMethods() {
        initOkhttpClient();
        list_Retrofit =  createRetrofit(mHttpClient);
        mPhone_service = list_Retrofit.get(0).create(Phone_Query_Service.class);
        mIdentity_query_service =  list_Retrofit.get(0).create(Identity_Query_Service.class);
        mPostal_query_service = list_Retrofit.get(1).create(Postal_Query_Service.class);
        mDrivers_license_exam_query_service = list_Retrofit.get(2).create(Drivers_license_exam_Query_Service.class);
        mBus_station_query_service = list_Retrofit.get(3).create(bus_station_Query_Service.class);
        mJoke_query_service = list_Retrofit.get(4).create(Joke_Query_Service.class);
        mBus_query_service = list_Retrofit.get(5).create(Bus_Query_Service.class);
        mHistory_query_service = list_Retrofit.get(1).create(History_Query_Service.class);
    }

    private void initOkhttpClient() {
        //        添加访问网络的日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        日志级别
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if(mHttpClient ==null){
                if(mHttpClient == null){
                    //        指定缓存路径,缓存大小100Mb
                    File cacheFile = new File
                            (APP.getContext().getCacheDir().getAbsolutePath(), "HttpCache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//缓存文件为100MB

                    //        手动创建一个OkHttpClient并设置超时时间
                    mHttpClient = new OkHttpClient.Builder();

                    mHttpClient
                            .cache(cache)
//                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS);
                }
        }

    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            如果网络不可用
//            if (!NetUtil.isWifiConnected()) {
//                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//            }else {//网络可用
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
//            }
            Response originalResponse = chain.proceed(request);
//            if (NetUtil.isWifiConnected()) {//如果网络可用
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
//            } else {
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
//                        .removeHeader("Pragma").build();
//            }
        }
    };

    private List<Retrofit> createRetrofit(OkHttpClient.Builder httpClient){
        List<Retrofit> list_Retrofit = new ArrayList<>();
        for (String baseurl:Data.Base_Url) {
            Log.d("qwe",baseurl);
            Retrofit mRetrofit =  new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseurl)
                    .build();
            list_Retrofit.add(mRetrofit);
        }
        return list_Retrofit;
    }

//    单例模式
    private static class SingletonHolder{
        private static  final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }



    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T>{

        @Override
        public T call(HttpResult<T> tHttpResult) {
//            if(!(tHttpResult.getResultcode().equals("200"))){
//                throw new ApiException(tHttpResult.getResultcode());
//            }
            return tHttpResult.getResult();
        }
    }
    private void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }



    public void getPhoneMess(Subscriber<phoneEntity> subscriber, String phonenum){
        Observable ov = mPhone_service.getphone_mess(phonenum, Data.AppKey[0])
                .map(new HttpResultFunc<phoneEntity>());
        toSubscribe(ov, subscriber);
    }

//    获取身份证信息
    public void getIdentityMess(Subscriber<identityMessEntity> subscriber,String cardnum){
        Observable ov = mIdentity_query_service.getidentityMess(cardnum, Data.AppKey[1])
                .map(new HttpResultFunc<identityMessEntity>());
        toSubscribe(ov,subscriber);
    }

//    获取身份证是否泄露
    public void getIndentityleakOrNot(Subscriber<identityMessEntity> subscriber,String cardnum){
        Observable ov = mIdentity_query_service.IdentityLessOrNot(cardnum, Data.AppKey[1])
                .map(new HttpResultFunc<identityMessEntity>());
        toSubscribe(ov,subscriber);
    }

    /**
     *邮政编码的请求方法
     */
//    靠邮政编码来查找
    public void getPostalByCode(Subscriber<postalBycodeEntity> subscriber,String code,String pagenum){
        Observable ov = mPostal_query_service.getpostalBycode(code, Data.AppKey[3], pagenum)
                .map(new HttpResultFunc<postalBycodeEntity>());
        toSubscribe(ov,subscriber);
    }
//    靠城市名来查找
    public void getPostalByCity(Subscriber<postalBycodeEntity> subscriber,String proid,String cityid,String disId){
        Observable ov = mPostal_query_service.getpostalBycity(proid, cityid, disId, Data.AppKey[3])
                .map(new HttpResultFunc<postalBycodeEntity>());
        toSubscribe(ov,subscriber);
    }
//  获取城市
    public void getPostalcity(Subscriber<List<postalofCity>> subscriber){
        Observable ov = mPostal_query_service.getCity(Data.AppKey[3])
                .map(new HttpResultFunc<List<postalofCity>>());
        toSubscribe(ov,subscriber);
    }

/**	key	string	是	您申请的appKey
    subject	int	是	选择考试科目类型，1：科目1；4：科目4
    model	string	是	驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略
    testType	string	否	测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）
 */
    public void getQuestion(Subscriber<List<driver>> subscriber,int subject,String model,String type){

        Observable ov = mDrivers_license_exam_query_service.getQuestion(subject, model, Data.AppKey[2], type)
                .map(new HttpResultFunc<List<driver>>());
        toSubscribe(ov,subscriber);
    }


//    公交的
    public void getBusStation(Subscriber<busstationEntity> subscriber,String city){
        Observable ov = mBus_station_query_service.getBusStation(city, Data.AppKey[4])
                .map(new HttpResultFunc<busstationEntity>());
        toSubscribe(ov,subscriber);
    }

    public void getBusStationMess(Subscriber<busstationMessEntity> subscriber,String from,String to){
        Observable ov = mBus_station_query_service.getBusStation(from,to,Data.AppKey[4])
                .map(new HttpResultFunc<busstationMessEntity>());
        toSubscribe(ov,subscriber);
    }


//    笑话的
    public void getLastestJoke(Subscriber<Joke> subscriber){
        Observable ov = mJoke_query_service.getLastestJoke(1 + "", Data.AppKey[5], 20 + "")
                .map(new HttpResultFunc<Joke>());
        toSubscribe(ov,subscriber);
    }

    public void getLastestJokeImg(Subscriber<Joke> subscriber){
        Observable ov = mJoke_query_service.getLastestImage(1 + "", Data.AppKey[5], 20 + "")
                .map(new HttpResultFunc<Joke>());
        toSubscribe(ov,subscriber);
    }

    public void getJokeByTime(Subscriber<Joke> subscriber){
        Observable ov = mJoke_query_service.getJokebyTime("desc", 1 + "", System.currentTimeMillis() / 1000 + "", Data.AppKey[5], 20 + "")
                .map(new HttpResultFunc<Joke>());
        toSubscribe(ov,subscriber);
    }

    public void getJokeImgByTime(Subscriber<Joke> subscriber){
        Observable ov = mJoke_query_service.getImagebyTime("desc", 1 + "", System.currentTimeMillis() / 1000 + "", Data.AppKey[5], 20 + "")
                .map(new HttpResultFunc<Joke>());
        toSubscribe(ov,subscriber);
    }

    public void getRandJoke(Subscriber<List<JoJo_Ke>> subscriber){
        Observable ov = mPostal_query_service.getRandJoke(Data.AppKey[5], "")
                .map(new HttpResultFunc<List<JoJo_Ke>>());
        toSubscribe(ov,subscriber);
    }

//    查询公交的

    public void QueryBusLine(Subscriber<List<busLine>> subscriber,String city,String line){
        Observable ov = mBus_query_service.QueryBusLine(Data.AppKey[6], city, line)
                .map(new HttpResultFunc<List<busLine>>());
        toSubscribe(ov,subscriber);
    }
 //废弃的
    @Deprecated
    public void QueryBusStation(Subscriber<List<busstation>> subscriber,String city,String station){
        Observable ov = mBus_query_service.QueryBusStation(Data.AppKey[6], city, station)
                .map(new HttpResultFunc<List<busstation>>());
        toSubscribe(ov,subscriber);
    }

//    历史上的今天
    public void Query_Today_History(Subscriber<List<history_bean>> subscriber,String date){
        Observable ov = mHistory_query_service.getHistory(Data.AppKey[7], date)
                .map(new HttpResultFunc<List<history_bean>>());
        toSubscribe(ov,subscriber);
    }

    public void Query_History_Detail(Subscriber<List<History_Detail_Bean>> subscriber,String e_id){
        Observable ov = mHistory_query_service.getHistory_Detail(Data.AppKey[7],e_id)
                .map(new HttpResultFunc<List<History_Detail_Bean>>());
        toSubscribe(ov,subscriber);
    }
}
