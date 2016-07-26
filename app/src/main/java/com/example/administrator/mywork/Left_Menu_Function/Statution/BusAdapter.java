package com.example.administrator.mywork.Left_Menu_Function.Statution;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.busstationEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class BusAdapter extends RecyclerView.Adapter<BusHolder>{
    private List<busstationEntity.ListEntity> mListEntities;

    public BusAdapter(List<busstationEntity.ListEntity> listEntities) {
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
        busstationEntity.ListEntity entity = mListEntities.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append("汽车站名:"+entity.getName()+"\n");
        sb.append("电话:"+entity.getTel()+"\n");
        sb.append("地址:"+entity.getAdds());
        holder.getBind().alltext.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return mListEntities.size();
    }
}
