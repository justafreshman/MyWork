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
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.busstationEntity;
import com.example.administrator.mywork.bean.postalBycodeEntity;
import com.example.administrator.mywork.databinding.QueryBusstationBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;
import com.example.administrator.mywork.view.SearchView;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class Query_BusStation extends BaseActivity{

    private QueryBusstationBinding mQbb;
    private ProgressSubscriber<busstationEntity> mSubscriber;
    private String mSearchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQbb = DataBindingUtil.setContentView(this, R.layout.query_busstation);
        mSubscriber = NewSubscriber();

        mQbb.busSearch.AddEditTextListen(new SearchView.OnEditTextListen() {
            @Override
            public void searchThing() {
                mSearchtext = mQbb.busSearch.getSearchText();
                if(!(mSearchtext.equals(""))){
                    query(mSearchtext);
                }else {
                    ToastUntil.showmess(Query_BusStation.this,"不能为空"+mSearchtext);
                }

            }
        });
        mQbb.busSearch.setHintText("请输入城市");
    }

    public void query(String city){
        if(mSubscriber!=null){
            if(mSubscriber.isUnsubscribed()){
                mSubscriber.unsubscribe();
            }
            mSubscriber = NewSubscriber();
        }else {
            mSubscriber = NewSubscriber();
        }
        Data.HTTP_METHODS.getBusStation(mSubscriber, city);
    }

    public ProgressSubscriber<busstationEntity> NewSubscriber(){
        return  new ProgressSubscriber<>(new SubscriberOnNextListener<busstationEntity>() {
            @Override
            public void onNext(busstationEntity busstationEntity) {
                    setData(busstationEntity);
            }
        },this);
    }

    public void setData(busstationEntity data) {
        mQbb.busMain.setText(data.getTitle());
        BusAdapter adapter = new BusAdapter(data.getList());
        mQbb.busMess.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 20;
            }
        });
        mQbb.busMess.setLayoutManager(new LinearLayoutManager(Query_BusStation.this));
        mQbb.busMess.setAdapter(adapter);
    }
}
