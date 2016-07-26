package com.example.administrator.mywork.Left_Menu_Function;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.Left_Menu_Function.Statution.BusAdapter;
import com.example.administrator.mywork.Left_Menu_Function.Statution.BusMessAdapter;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.busstationEntity;
import com.example.administrator.mywork.bean.busstationMessEntity;
import com.example.administrator.mywork.databinding.QueryBusstationBinding;
import com.example.administrator.mywork.databinding.QueryBusstationMessBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class  Query_BusStation_Mess extends BaseActivity{

    private QueryBusstationMessBinding mQbmb;
    private ProgressSubscriber<busstationMessEntity> mSubscriber;
    private String mTwo;
    private String mOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQbmb = DataBindingUtil.setContentView(this, R.layout.query_busstation_mess);
        mQbmb.busStart.setHint("请输入起点");
        mQbmb.busEnd.setHint("请输入终点");
        mQbmb.busQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOne = mQbmb.busStart.getText().toString().trim();
                mTwo = mQbmb.busEnd.getText().toString().trim();
                if(mOne.equals("")||mTwo.equals("")){
                    ToastUntil.showmess(Query_BusStation_Mess.this,"请输入文字");
                }else {
                    query(mOne,mTwo);
                }
            }
        });
    }


    public void query(String cityone,String citytwo){
        if(mSubscriber!=null){
            if(mSubscriber.isUnsubscribed()){
                mSubscriber.unsubscribe();
            }
            mSubscriber = NewSubscriber();
        }else {
            mSubscriber = NewSubscriber();
        }
        Data.HTTP_METHODS.getBusStationMess(mSubscriber, cityone, citytwo);
    }

    public ProgressSubscriber<busstationMessEntity> NewSubscriber(){
        return  new ProgressSubscriber<>(new SubscriberOnNextListener<busstationMessEntity>() {
            @Override
            public void onNext(busstationMessEntity busstationEntity) {
                setData(busstationEntity);
            }
        },this);
    }

    public void setData(busstationMessEntity data) {
        BusMessAdapter adapter = new BusMessAdapter(data.getList());
        mQbmb.showBusPrice.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 20;
            }
        });
        mQbmb.showBusPrice.setLayoutManager(new LinearLayoutManager(Query_BusStation_Mess.this));
        mQbmb.showBusPrice.setAdapter(adapter);
    }
}
