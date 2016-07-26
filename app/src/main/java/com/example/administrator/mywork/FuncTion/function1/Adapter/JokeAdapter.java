package com.example.administrator.mywork.FuncTion.function1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.Joke;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 * 作者：wu
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeViewHolder>{
    public final static int IMAGE_VISIBLE = 1;
    public final static int IMAGE_GONE = 2;
    private List<Joke.DataEntity> mList;
    private Context mContext;
    private int Type = 0;

    public JokeAdapter(List<Joke.DataEntity> list){
        this(list,0,null);
    }

//    public JokeAdapter(List<Joke.DataEntity> list,Context context){
//        this(list,0,context);
//    }

    public JokeAdapter(List<Joke.DataEntity> list,int Type,Context context) {
        mList = list;
        this.mContext = context;
        this.Type = Type;
    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_item,parent,false);
        return new JokeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JokeViewHolder holder, int position) {
        Joke.DataEntity entity = mList.get(position);
        holder.getFib().setJoke(entity);
        if(Type==IMAGE_VISIBLE){
            holder.getFib().jokePic.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(entity.getUrl())
                    .thumbnail(0.1f)
                    .into(holder.getFib().jokePic);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
