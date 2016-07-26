package com.example.administrator.mywork.FuncTion.function1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.JoJo_Ke;
import com.example.administrator.mywork.bean.Joke;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 * 作者：wu
 */
public class JokeAdapter_ke extends RecyclerView.Adapter<JoJoViewHolder>{
    public final static int IMAGE_VISIBLE = 1;
    public final static int IMAGE_GONE = 2;
    private List<JoJo_Ke> mList;
    private Context mContext;
    private int Type = 0;

    public JokeAdapter_ke(List<JoJo_Ke> list){
        this(list,0,null);
    }

//    public JokeAdapter(List<Joke.DataEntity> list,Context context){
//        this(list,0,context);
//    }

    public JokeAdapter_ke(List<JoJo_Ke> list, int Type, Context context) {
        mList = list;
        this.mContext = context;
        this.Type = Type;
    }

    @Override
    public JoJoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fifth_item,parent,false);
        return new JoJoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JoJoViewHolder holder, int position) {
        JoJo_Ke entity = mList.get(position);
        holder.getFib().jokeContent.setText(entity.getContent().toString());
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
