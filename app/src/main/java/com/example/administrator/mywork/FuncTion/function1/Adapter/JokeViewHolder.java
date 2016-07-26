package com.example.administrator.mywork.FuncTion.function1.Adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.FirstItemBinding;

/**
 * Created by Administrator on 2016/7/13.
 * 作者：wu
 */
public class JokeViewHolder extends RecyclerView.ViewHolder{
    FirstItemBinding fib = null;
    public JokeViewHolder(View itemView) {
        super(itemView);
        fib = DataBindingUtil.bind(itemView);
    }

    public FirstItemBinding getFib() {
        return fib;
    }
}
