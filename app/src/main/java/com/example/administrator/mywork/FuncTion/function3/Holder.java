package com.example.administrator.mywork.FuncTion.function3;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.mywork.databinding.ThirdNoteListBinding;

/**
 * Created by Administrator on 2016/7/25.
 * 作者：wu
 */
public class Holder extends RecyclerView.ViewHolder{
    private ThirdNoteListBinding tnlb;

    public Holder(View itemView) {
        super(itemView);
        tnlb = DataBindingUtil.bind(itemView);
    }
    public ThirdNoteListBinding getBind(){
        return tnlb;
    }
}
