package com.example.administrator.mywork.FuncTion.function1.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.FifthItemBinding;
import com.example.administrator.mywork.databinding.FirstItemBinding;

/**
 * Created by Administrator on 2016/7/13.
 * 作者：wu
 */
public class JoJoViewHolder extends RecyclerView.ViewHolder{
    FifthItemBinding fib = null;
    public JoJoViewHolder(View itemView) {
        super(itemView);
        fib = DataBindingUtil.bind(itemView);
    }

    public FifthItemBinding getFib() {
        return fib;
    }
}
