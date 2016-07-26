package com.example.administrator.mywork.Left_Menu_Function.Driver;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.mywork.databinding.QueryDriverItemBinding;

/**
 * Created by Administrator on 2016/7/5.
 * 作者：wu
 */
public class DriverHolder extends RecyclerView.ViewHolder{
    QueryDriverItemBinding qdi = null;
    public DriverHolder(View itemView) {
        super(itemView);
        qdi = DataBindingUtil.bind(itemView);
    }

    public QueryDriverItemBinding getBind(){
        return qdi;
    }
}
