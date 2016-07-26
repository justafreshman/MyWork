package com.example.administrator.mywork.FuncTion.function3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/26.
 * 作者：wu
 */
public class PicAdapter extends RecyclerView.Adapter<Holder>{
    private ArrayList<String> mList;
    private Context mContext;

    public PicAdapter(ArrayList<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_note_list,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Glide.with(mContext)
                .load("file://" + mList.get(position))
                .into(holder.getBind().showPic);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
