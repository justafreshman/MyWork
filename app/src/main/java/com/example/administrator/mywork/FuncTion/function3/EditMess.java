package com.example.administrator.mywork.FuncTion.function3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.DBHelper.DBManager;
import com.example.administrator.mywork.Until.DBHelper.MyMessDB;
import com.example.administrator.mywork.Until.Multpic_pic.PhotoActivity;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.databinding.ThirdEditmessBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class EditMess extends BaseActivity implements View.OnClickListener{

    private ThirdEditmessBinding mTeb;
    private TextView title;
    private TextView concent;
    private notebean mNotebean;
    private MyPic mMp;
    private IntentFilter mFilter;
    private ArrayList<String> mList;
    private DBManager mManager;
    private PicAdapter mPa;
    private ArrayList<String> mAList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeb = DataBindingUtil.setContentView(this, R.layout.third_editmess);
        title = mTeb.title;
        concent = mTeb.content;
        mManager = new DBManager(this);
        mList = new ArrayList<>();
        mPa = new PicAdapter(mList,EditMess.this);
        mTeb.picList.setLayoutManager(new LinearLayoutManager(EditMess.this));
        mTeb.picList.setAdapter(mPa);
        showMess();
        mTeb.btnSave.setOnClickListener(this);
        mTeb.btnCancel.setOnClickListener(this);
        mTeb.selectPic.setOnClickListener(this);
    }


    public void showMess(){
        mNotebean = getIntent().getParcelableExtra("bean");
        if(mNotebean.getId()!=null){
            mTeb.title.setText(mNotebean.getTitle());
            mTeb.content.setText(mNotebean.getContent());
            Cursor cursor = mManager.querypic(mNotebean.getId()+"");
            String path = "";
            while (cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MyMessDB.PATH));
                mList.add(path);
            }
            mPa.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                int i=0;
                int x = 0;
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if(mNotebean.getId()!=null){
                    i = mManager.updateNoteAndMedia(mNotebean.getId(), title.getText().toString().trim()
                            , concent.getText().toString().trim(), date);
                }else {
                    i = mManager.addNote(title.getText().toString().trim(),
                            concent.getText().toString().trim(),
                            date);
                }

                String mess = "";
                if(i>-1){
                    if(mAList!=null){
                        for (int a = 0; a < mAList.size(); a++) {
                            x = mManager.savemedia(i, mList.get(a));
                            if(x<=-1){
                                mess = "保存失败";
                                return;
                            }else {
                                mess = "保存成功";
                            }
                        }
                    }else {
                        mess = "保存成功";
                    }

                }else {
                    mess = "保存失败";
                }
                ToastUntil.showmess(EditMess.this,mess);
                Intent intent = new Intent();
                intent.setAction("fresh");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.select_pic:
                if(mFilter==null){
                    mFilter = new IntentFilter();
                    mFilter.addAction("picpath");
                }
                if(mMp == null){
                    mMp = new MyPic();
                    registerReceiver(mMp, mFilter);
                }
                Intent intent2 = new Intent();
                intent2.setClass(EditMess.this, PhotoActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.destory();
        if(mMp!=null){
            unregisterReceiver(mMp);
        }
    }


//    广播
    public class MyPic extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAList = new ArrayList<>();
            mAList = intent.getStringArrayListExtra("path");
            mList.addAll(mAList);
            mPa.notifyDataSetChanged();
        }
    }


}
