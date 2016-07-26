package com.example.administrator.mywork.Data;

import com.example.administrator.mywork.HttpMethod.HttpMethods;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class Data {
//    http://v.juhe.cn/postcode/query?postcode=215001&key=1e4a29e8043f748949d04f6496424bee
    public static final String[] Base_Url =
            {"http://apis.juhe.cn/",
             "http://v.juhe.cn/",
             "http://api2.juheapi.com/",
//                    长途汽车的
            "http://op.juhe.cn/onebox/bus/",
//                    笑话大全的
            "http://japi.juhe.cn/joke/",
//                    公交的
            "http://op.juhe.cn/189/bus/"};
    public static final int DEFAULT_TIMEOUT = 5;
    public static String[] AppKey =
            new String[]{
//                  0  手机归属地查询的
                    "66538ce1821e73ed650f9212bdf80079",
//                  1 身份证查询
                    "223151555a8beef14d9789745ad78718",
//                  2  驾照题库
                    "6e21251e0a66c4e75b0e7b27f85b47a3",
//                  3  邮政查询
                    "1e4a29e8043f748949d04f6496424bee",
//                  4  长途汽车信息
                    "f03f6afcfb434dad9ac94aed929b3470",
//                  5  笑话大全的
                    "9d7c62bfb5ee67fe0166a71f67566722",
//                  6 公交线路查询
                    "319737c4fe315fd7e28a9fcefcaf5597",
//                  7.历史上的今天
                    "4946c9633d8e671f5c6cd5031a20b443"
            };
    public static final HttpMethods HTTP_METHODS = HttpMethods.getInstance();
}
