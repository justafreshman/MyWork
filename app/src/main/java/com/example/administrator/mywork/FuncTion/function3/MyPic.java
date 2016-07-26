package com.example.administrator.mywork.FuncTion.function3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.mywork.FuncTion.third;

/**
 * Created by Administrator on 2016/7/25.
 * 作者：wu
 */
public class MyPic extends BroadcastReceiver{
    private NoteAdapter mAdapter;
    private third mThird;
    public MyPic(NoteAdapter adapter, third third) {
        mAdapter = adapter;
        mThird = third;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mThird.setData(mThird.getData());
    }
}
