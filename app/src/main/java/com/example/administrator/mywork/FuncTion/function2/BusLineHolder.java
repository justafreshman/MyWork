package com.example.administrator.mywork.FuncTion.function2;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.OnlyTextviewItemBinding;

/**
 * Created by Administrator on 2016/7/22.
 * 作者：wu
 */
public class BusLineHolder extends RecyclerView.ViewHolder{
    OnlyTextviewItemBinding otib;

    public BusLineHolder(View itemView) {
        super(itemView);
        otib = DataBindingUtil.bind(itemView);
    }

    public OnlyTextviewItemBinding getBind(){
        return otib;
    }
}
