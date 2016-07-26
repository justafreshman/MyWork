package com.example.administrator.mywork.Left_Menu_Function.Statution;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.busstationMessEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class BusMessAdapter extends RecyclerView.Adapter<BusHolder>{
    private List<busstationMessEntity.ListEntity> mListEntities;

    public BusMessAdapter(List<busstationMessEntity.ListEntity> listEntities) {
        mListEntities = listEntities;
    }

    @Override
    public BusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.only_textview_item, parent, false);
        return new BusHolder(item);
    }

    @Override
    public void onBindViewHolder(BusHolder holder, int position) {
        busstationMessEntity.ListEntity entity = mListEntities.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append("起点:"+entity.getStart()+"\n");
        sb.append("终点:"+entity.getArrive()+"\n");
        sb.append("时间:"+entity.getDate()+"\n");
        sb.append("价格:"+entity.getPrice());
        holder.getBind().alltext.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return mListEntities.size();
    }
}
