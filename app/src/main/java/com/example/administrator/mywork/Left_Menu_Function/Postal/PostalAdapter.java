package com.example.administrator.mywork.Left_Menu_Function.Postal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.postalBycodeEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class PostalAdapter extends RecyclerView.Adapter<PostalHolder>{
    private List<postalBycodeEntity.ListEntity> mListEntities;

    public PostalAdapter(List<postalBycodeEntity.ListEntity> listEntities) {
        mListEntities = listEntities;
    }

    @Override
    public PostalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.only_textview_item, parent, false);
        return new PostalHolder(item);
    }

    @Override
    public void onBindViewHolder(PostalHolder holder, int position) {
        postalBycodeEntity.ListEntity entity = mListEntities.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append("编号:"+entity.getPostNumber()+"\n");
        sb.append("省份:"+entity.getProvince()+"\n");
        sb.append("城市:"+entity.getCity()+"\n");
        sb.append("区域"+entity.getDistrict()+"\n");
        sb.append("地址:"+entity.getAddress());
        holder.getBind().alltext.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return mListEntities.size();
    }
}
