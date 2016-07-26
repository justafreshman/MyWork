package com.example.administrator.mywork.Left_Menu_Function;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.administrator.mywork.APP;
import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.identityMessEntity;
import com.example.administrator.mywork.databinding.QueryIdentityBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;
import com.example.administrator.mywork.view.SearchView;

import rx.internal.schedulers.NewThreadScheduler;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class Query_Identity extends BaseActivity{

    private QueryIdentityBinding mQib;
    ProgressSubscriber<identityMessEntity> sSubscriber;
    private int whichselect = 0;
    private String mStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQib = DataBindingUtil.setContentView(this, R.layout.query_identity);
        initEvent();
//        设置只能输入数字
        mQib.identitySearch.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

//        mQib.identitySearch.AddEditTextListen(new SearchView.OnEditTextListen() {
//            @Override
//            public void searchThing() {
//               query();
//            }
//        });
    }
    //    观察者不可以订阅多个事件
    private ProgressSubscriber<identityMessEntity> NewsSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<identityMessEntity>() {
            @Override
            public void onNext(identityMessEntity identityMessEntity) {
                StringBuffer mess  = new StringBuffer();
                if(whichselect==0){
                    mess.append(identityMessEntity.getArea()+"\n");
                    mess.append(identityMessEntity.getSex()+"\n");
                    mess.append(identityMessEntity.getBirthday());
                }else {
                    mess.append(identityMessEntity.getCardno()+"\n");
                    mess.append(identityMessEntity.getTips());
                }
                mQib.showmess.setText(mess.toString());
            }
        },this);
    }

    public void query(View v){
        mStr = mQib.identitySearch.getSearchText();
        if (mStr.equals("")){
            ToastUntil.showmess(Query_Identity.this,"请输入身份证号码");
        }else {
            query(mStr);
        }
    }

    public void query(String num){
        if(sSubscriber!=null){
            if(sSubscriber.isUnsubscribed()){
                sSubscriber.unsubscribe();
            }
            sSubscriber = NewsSubscriber();
        }else {
            sSubscriber =  NewsSubscriber();
        }
        if(whichselect==0){
            Data.HTTP_METHODS.getIdentityMess(sSubscriber, num);
        }else {
            Data.HTTP_METHODS.getIndentityleakOrNot(sSubscriber,num);
        }
    }


    private void initEvent(){
        String[] identity_array = getResources().getStringArray(R.array.identity);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Query_Identity.this,android.R.layout.simple_spinner_item,identity_array);
        mQib.identitySelect.setAdapter(arrayAdapter);
        mQib.identitySelect.setSelection(0);
        mQib.identitySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                whichselect = position;
                String tips = null;
                if(position==0){
                    tips = getResources().getString(R.string.identity_tip1);
                }else {
                    tips = getResources().getString(R.string.identity_tip2);
                    mQib.showmess.setText(tips);
                }
                mQib.identitySearch.setHintText(tips);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
