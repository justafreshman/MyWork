package com.example.administrator.mywork.FuncTion.function2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.busLine;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 * 作者：wu
 */
public class BusLineAdapter extends RecyclerView.Adapter<BusLineHolder>{
    List<busLine.StationdesEntity> station;

    public BusLineAdapter( List<busLine.StationdesEntity> station ) {
        this.station = station;
    }

    @Override
    public BusLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_textview_item,parent,false);
        return new BusLineHolder(v);
    }

    @Override
    public void onBindViewHolder(BusLineHolder holder, int position) {
        String mess = station.get(position).getName();
        holder.getBind().alltext.setText(mess);
    }

    @Override
    public int getItemCount() {
        return station.size();
    }
}
