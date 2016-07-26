package com.example.administrator.mywork.FuncTion.function4;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.History_Detail_Bean;
import com.example.administrator.mywork.bean.history_bean;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 * 作者：wu
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>{
    private List<history_bean> history_beans;
    private Context mContext;
    private ViewGroup mGroup;

    public HistoryAdapter(List<history_bean> history_beans,Context context) {
        this.history_beans = history_beans;
        mContext = context;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_textview_item,parent,false);
        mGroup = parent;
        return new HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, final int position) {
        holder.getBind().alltext.setText(history_beans.get(position).getTitle());
        holder.getBind().alltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressSubscriber<List<History_Detail_Bean>> mmSubscriber = NewSubscriber2();
                Data.HTTP_METHODS.Query_History_Detail(mmSubscriber, history_beans.get(position).getE_id());
            }
        });
    }

    public ProgressSubscriber<List<History_Detail_Bean>> NewSubscriber2(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<List<History_Detail_Bean>>() {
            @Override
            public void onNext(List<History_Detail_Bean> history_detail_beans) {
                History_Detail_Bean bean = history_detail_beans.get(0);
                String content = bean.getContent();
                if(content.equals("")||content==null||history_detail_beans==null){
                    content = "没有数据";
                }
                BasePopuWindow.showpopuwindow(mContext,mGroup,content);
            }
        },mContext);
    }

    @Override
    public int getItemCount() {
        return history_beans.size();
    }
}
