package com.example.administrator.mywork.Left_Menu_Function;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.phoneEntity;
import com.example.administrator.mywork.databinding.QueryPhonenumBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;
import com.example.administrator.mywork.view.SearchView;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class Query_PhoneNum extends BaseActivity{

    private QueryPhonenumBinding mMqb;
    private ProgressSubscriber<phoneEntity> mSubscriber;
    private String mPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMqb = DataBindingUtil.setContentView(this, R.layout.query_phonenum);
        NewSubscriber();
        mMqb.searchPhoneNum.setHintText("请输入手机号码");
        mMqb.searchPhoneNum.AddEditTextListen(new SearchView.OnEditTextListen() {
            @Override
            public void searchThing() {
                query();
            }
        });
    }

    public void query(){
        mPhoneNum = mMqb.searchPhoneNum.getSearchText();
        if(mPhoneNum.equals("")){
            ToastUntil.showmess(Query_PhoneNum.this,"请输入号码");
        }else {
            if(mSubscriber.isUnsubscribed()){
                mSubscriber.unsubscribe();
                mSubscriber = NewSubscriber();
            }else {
                mSubscriber = NewSubscriber();
            }
            Data.HTTP_METHODS.getPhoneMess(mSubscriber, mPhoneNum);
        }
    }

    public void query(View v){
        query();
    }


    public ProgressSubscriber<phoneEntity> NewSubscriber(){
       return mSubscriber = new ProgressSubscriber<phoneEntity>(new SubscriberOnNextListener<phoneEntity>() {

            @Override
            public void onNext(phoneEntity phoneEntity) {
                StringBuffer sb = new StringBuffer();
                sb.append("省 份：" + phoneEntity.getProvince() + "\n");
                sb.append("城 市："+phoneEntity.getCity()+"\n");
                sb.append("区 号："+phoneEntity.getAreacode()+"\n");
                sb.append("邮 编："+phoneEntity.getZip()+"\n");
                sb.append("运营商："+phoneEntity.getCompany()+"\n");
                sb.append("卡类型：" + phoneEntity.getCard() + "\n");
                mMqb.showmess.setText(sb.toString());
            }
        },this);
    }
}
