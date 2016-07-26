package com.example.administrator.mywork.FuncTion;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.administrator.mywork.Base.BaseFragment;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.FuncTion.function4.HistoryAdapter;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.history_bean;
import com.example.administrator.mywork.databinding.ForthBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：wu
 */
public class forth extends BaseFragment implements View.OnClickListener{

    private ForthBinding mSb;
    private ProgressSubscriber<List<history_bean>> mSubscriber;
    private static String date;
    private static TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSb = DataBindingUtil.inflate(inflater, R.layout.forth, container, false);
        mSb.selectTime.setOnClickListener(this);
        mSb.queryTime.setOnClickListener(this);
        tv = mSb.showtime;
        return mSb.getRoot();
    }

    public ProgressSubscriber<List<history_bean>> NewSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<List<history_bean>>() {
            @Override
            public void onNext(List<history_bean> history_beans) {
                HistoryAdapter adapter = new HistoryAdapter(history_beans,getContext());
                mSb.historyThing.setLayoutManager(new LinearLayoutManager(getActivity()));
                mSb.historyThing.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.bottom = 20;
                        outRect.top = 20;
                    }
                });
                mSb.historyThing.setAdapter(adapter);
            }
        },getContext());
    }

    public void queryhistory(String date){
        if(mSubscriber!=null){
            mSubscriber.unsubscribe();
        }
        mSubscriber = NewSubscriber();
        Data.HTTP_METHODS.Query_Today_History(mSubscriber,date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_time:
                TimeSelect ts = new TimeSelect();
                ts.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.query_time:
                queryhistory(date);
                break;
        }
    }

    public static  class TimeSelect extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return  new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date = monthOfYear+1+"/"+dayOfMonth;
            tv.setText(date);
        }
    }
}
