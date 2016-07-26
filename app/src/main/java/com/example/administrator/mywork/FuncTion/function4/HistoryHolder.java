package com.example.administrator.mywork.FuncTion.function4;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.OnlyTextviewItemBinding;

/**
 * Created by Administrator on 2016/7/22.
 * 作者：wu
 */
//TODO   重复代码 找个时间重构
public class HistoryHolder extends RecyclerView.ViewHolder{
    OnlyTextviewItemBinding otib;
    public HistoryHolder(View itemView) {
        super(itemView);
        otib = DataBindingUtil.bind(itemView);
    }
    public OnlyTextviewItemBinding getBind(){
        return otib;
    }
}
