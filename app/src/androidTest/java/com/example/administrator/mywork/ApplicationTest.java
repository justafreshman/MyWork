package com.example.administrator.mywork;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.bean.driver;

import rx.Subscriber;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        Subscriber<driver> subscriber = new Subscriber<driver>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("qwe",e.toString()+"qwe");
            }

            @Override
            public void onNext(driver postalBycodeEntity) {
                Log.d("qwe",postalBycodeEntity.toString());
            }
        };
    }
}