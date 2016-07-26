package com.example.administrator.mywork.FuncTion.function3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/25.
 * 作者：wu
 */
public class NoteAdapter extends RecyclerView.Adapter<Holder> {
    private ArrayList<notebean> mList;

    public NoteAdapter(ArrayList<notebean> list) {
        mList = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_note_list,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.getBind().setMess(mList.get(position));
        longclick lc = new longclick(position);
        singleclick ck = new singleclick(position);
        holder.getBind().mainLayout.setOnClickListener(ck);
        holder.getBind().mainLayout.setOnLongClickListener(lc);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class longclick implements View.OnLongClickListener{
        int position;

        public longclick(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            mOnListener.longclick(mList.get(position));
            return false;
        }
    }

    public class singleclick implements View.OnClickListener{
        int position;
        public singleclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mOnListener.singleclick(mList.get(position));
        }
    }

    private OnListener mOnListener;

    public interface OnListener{
        void singleclick(notebean bean);
        void longclick(notebean bean);
    }

    public void addclick(OnListener listener){
        this.mOnListener = listener;
    }
}
