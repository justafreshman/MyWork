package com.example.administrator.mywork.FuncTion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.Base.BaseFragment;
import com.example.administrator.mywork.FuncTion.function3.EditMess;
import com.example.administrator.mywork.FuncTion.function3.MyBro;
import com.example.administrator.mywork.FuncTion.function3.NoteAdapter;
import com.example.administrator.mywork.FuncTion.function3.notebean;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.DBHelper.DBManager;
import com.example.administrator.mywork.Until.DBHelper.MyMessDB;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.databinding.ThirdBinding;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：wu
 */
public class third extends BaseFragment{

    private ThirdBinding mTb;
    private NoteAdapter mAdapter;
    private MyBro mBro;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTb = DataBindingUtil.inflate(inflater, R.layout.third, null, false);

        mTb.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditMess.class);
                intent.putExtra("bean", new notebean());
                startActivity(intent);
            }
        });
        setData(getData());
        changedata(mAdapter);
        return mTb.getRoot();
    }

    private void changedata(NoteAdapter adapter) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("fresh");
        mBro = new MyBro(adapter,third.this);
        getActivity().registerReceiver(mBro,filter);
    }


    public ArrayList getData(){
        DBManager mManager = new DBManager(getActivity());
        ArrayList<notebean> arrayList = new ArrayList();
        Cursor cursor = mManager.query();
        while (cursor.moveToNext()){
            notebean bean = new notebean();
            bean.setTitle(cursor.getString(cursor.getColumnIndex(MyMessDB.TITLE)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(MyMessDB.SAVEDATE)));
            bean.setId(cursor.getInt(cursor.getColumnIndex(MyMessDB.ID)) + "");
            bean.setContent(cursor.getString(cursor.getColumnIndex(MyMessDB.CONTENT)));
            arrayList.add(bean);
        }
        cursor.close();
        mManager.destory();
      return arrayList;
    }


    public void setData(ArrayList arrayList){
        mAdapter = new NoteAdapter(arrayList);
        mAdapter.addclick(new NoteAdapter.OnListener() {
            @Override
            public void singleclick(notebean bean) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditMess.class);
                intent.putExtra("bean", bean);
                getActivity().startActivity(intent);
            }

            @Override
            public void longclick(notebean bean) {
                showDialog(bean);
            }
        });
        mTb.showNote.allText.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTb.showNote.allText.setAdapter(mAdapter);
    }



    public void showDialog(final notebean bean){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("删除");
        dialog.setMessage("真的删除吗");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager dbManager = new DBManager(getActivity());
                int i =dbManager.delete(bean.getId());
                if (i >= 0) {
                    ToastUntil.showmess(getActivity(),"删除成功");
                    setData(getData());
                }
                dbManager.destory();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBro!=null){
            getActivity().unregisterReceiver(mBro);
        }
    }
}
