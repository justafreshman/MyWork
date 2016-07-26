package com.example.administrator.mywork.Until.Multpic_pic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.bean.ImageItem;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.littleuntil;


import java.util.ArrayList;


/**
*
*@author  Administrator
*@time    2016/4/20 15:51
*/
public class AlbumGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ImageItem> dataList;
    //    所有图片的路径
    private ArrayList<String> imagepath;


    //    构造方法 初始化变量
    public AlbumGridViewAdapter(Context c, ArrayList<String> imagepath) {
        mContext = c;
        this.imagepath = imagepath;
    }

    @Override
    public int getCount() {
        return imagepath.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_plugin_camera_select_imageview, parent, false);
            viewHolder.imageView= (ImageView)convertView.findViewById(R.id.image_view);
            viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
            viewHolder.choosetoggle = (Button) convertView.findViewById(R.id.choosedbt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        获取图片的路径
        String path;
        viewHolder.toggleButton.setTag(position);
        viewHolder.choosetoggle.setTag(position);
        path = "file://" + imagepath.get(position);
        Glide.with(mContext)
                .load(path)
                .into(viewHolder.imageView);
        viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.choosetoggle));
        if (littleuntil.checksamepath(Bimp.tempSelectBitmappath, imagepath.get(position))) {
            viewHolder.toggleButton.setChecked(true);
            viewHolder.choosetoggle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.toggleButton.setChecked(false);
            viewHolder.choosetoggle.setVisibility(View.GONE);
        }
        return convertView;
    }

//    存放列表控件的句柄   有效解决OOM
    private class ViewHolder {
        public ImageView imageView;
        public ToggleButton toggleButton;
        public Button choosetoggle;
    }

    //    双击事件 显示被点击的图片
    private class ToggleClickListener implements View.OnClickListener {
        Button chooseBt;
        public ToggleClickListener(Button choosebt) {
            this.chooseBt = choosebt;
        }
        @Override
        public void onClick(View view) {
            if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                int position = (Integer) toggleButton.getTag();
                if (imagepath != null && mOnItemClickListener != null && position < imagepath.size()) {
                    mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(), chooseBt);
                }
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(ToggleButton view, int position, boolean isChecked, Button chooseBt);
    }
}
