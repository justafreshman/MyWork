package com.example.administrator.mywork.Left_Menu_Function;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.Left_Menu_Function.Driver.DriverAdapter;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.driver;
import com.example.administrator.mywork.databinding.QueryDriverBinding;
import com.example.administrator.mywork.databinding.QueryDriverItemBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.internal.schedulers.NewThreadScheduler;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public class Query_Driver extends BaseActivity{

    private QueryDriverBinding mQdb;
    private ProgressSubscriber<List<driver>> mSubscriber;
    public int subId;
    public String modelstr;
    public String tType;
    private DriverAdapter mAd;
    private List<driver> driver_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQdb = DataBindingUtil.setContentView(this, R.layout.query_driver);
        initSpinnerData();
    }

    public void initSpinnerData(){
        final String[] subject = new String[]{"科目1","科目4"};
        final String[] testType = getResources().getStringArray(R.array.driver_testType);
        final String[] model = getResources().getStringArray(R.array.driver_model);
        ArrayAdapter<String> adapterone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subject);
        ArrayAdapter<String> adaptertwo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, model);
        ArrayAdapter<String> adapterthree = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testType);
        mQdb.driverSubject.setAdapter(adapterone);
        mQdb.driverModel.setAdapter(adaptertwo);
        mQdb.driverTestType.setAdapter(adapterthree);

        mQdb.driverSubject.setSelection(0);
        mQdb.driverModel.setSelection(0);
        mQdb.driverTestType.setSelection(0);
        mQdb.driverSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sub = subject[position];
                if (sub.equals("科目1")) {
                    mQdb.driverModel.setEnabled(true);
                    subId = 1;
                } else {
                    mQdb.driverModel.setEnabled(false);
                    modelstr = "";
                    subId = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mQdb.driverModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    modelstr = model[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mQdb.driverTestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(testType[position].equals("随机测试")){
                    tType = "rand";
                }else {
                    tType = "order";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public ProgressSubscriber<List<driver>> NewSubscriber(){
        return  new ProgressSubscriber<List<driver>>(new SubscriberOnNextListener<List<driver>>() {
            @Override
            public void onNext(List<driver> drivers) {
                initRecycleData(drivers);
            }
        },this);
    }

    public void queryquestion(View view){
        if(mSubscriber!=null){
            if(mSubscriber.isUnsubscribed()){
                mSubscriber.unsubscribe();
            }
            mSubscriber = NewSubscriber();
        }else {
            mSubscriber = NewSubscriber();
        }
        Data.HTTP_METHODS.getQuestion(mSubscriber,subId,modelstr,tType);
    }

    public void initRecycleData(List<driver> drivers){
            mAd = new DriverAdapter(drivers,Query_Driver.this);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            mQdb.driverListQuestion.setLayoutManager(llm);
            mQdb.driverListQuestion.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.bottom = 10;
                }
            });
            mQdb.driverListQuestion.setAdapter(mAd);
    }
}
