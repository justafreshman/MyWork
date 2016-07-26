package com.example.administrator.mywork.FuncTion;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mywork.Base.BaseFragment;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.FuncTion.function2.BusLineAdapter;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.busLine;
import com.example.administrator.mywork.databinding.SecondBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：wu
 */
public class second extends BaseFragment{

    private SecondBinding mSb;
    private ProgressSubscriber<List<busLine>> mSubscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSb = DataBindingUtil.inflate(inflater, R.layout.second, container, false);
        mSubscriber = NewSubscriber();
        mSb.querybusline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querybusline(mSb.city.getText().toString().trim(), mSb.busline.getText().toString().trim());
            }
        });

        return mSb.getRoot();
    }


    public void querybusline(String cityname,String linenum){
        ToastUntil.showmess(getActivity(),cityname+linenum);
        if(mSubscriber==null){
            mSubscriber = NewSubscriber();
        }else {
            mSubscriber.unsubscribe();
            mSubscriber = NewSubscriber();
        }
        Data.HTTP_METHODS.QueryBusLine(mSubscriber, cityname, linenum);
    }

    public ProgressSubscriber<List<busLine>> NewSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<List<busLine>>() {
            @Override
            public void onNext(List<busLine> busLines) {
                setData(busLines.get(0),mSb.showStation,mSb.startBus,mSb.endBus);
                setData(busLines.get(1),mSb.otherShowStation,mSb.otherStartBus,mSb.otherEndBus);
            }
        },getContext());
    }

    public void setData(busLine busLines,RecyclerView rv,TextView start,TextView end){
        start.setText("起始站:"+busLines.getFront_name());
        end.setText("终点站:"+busLines.getTerminal_name());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        BusLineAdapter adapter = new BusLineAdapter(busLines.getStationdes());
        rv.setAdapter(adapter);
    }
}
