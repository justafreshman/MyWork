package com.example.administrator.mywork.Left_Menu_Function.Statution;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.OnlyTextviewItemBinding;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class BusHolder extends RecyclerView.ViewHolder{
    private OnlyTextviewItemBinding ipm;
    public BusHolder(View itemView) {
        super(itemView);
        ipm = DataBindingUtil.bind(itemView);
    }

    public OnlyTextviewItemBinding getBind(){
        return ipm;
    }
}
