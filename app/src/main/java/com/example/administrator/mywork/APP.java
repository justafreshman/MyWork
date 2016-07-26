package com.example.administrator.mywork;

import android.app.Application;
import android.content.Context;

import com.example.administrator.mywork.HttpMethod.HttpMethods;
import com.example.administrator.mywork.Until.AppContextUtil;

/**
 * Created by Administrator on 2016/6/29.
 * 作者：wu
 */
public class APP extends Application{
    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }

}
