package com.example.administrator.mywork.Until.Multpic_pic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.activity.ShowAllPhoto;
import com.example.administrator.mywork.Until.Multpic_pic.bean.AllImagepath;
import com.example.administrator.mywork.Until.Multpic_pic.bean.ImageBucket;
import com.example.administrator.mywork.Until.Multpic_pic.bean.ImageItem;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
*/
public class FolderAdapter extends BaseAdapter {
    private Context mContext;
    private Intent mIntent;
    private DisplayMetrics dm;
    private AllImagepath allpath;

    public FolderAdapter(Context context,AllImagepath allImagepath) {
        init(context);
        this.allpath = allImagepath;
    }

    // 初始化
    public void init(Context c) {
        mContext = c;
        mIntent = ((Activity) mContext).getIntent();

    }


    @Override
    public int getCount() {
        return  allpath.getImagebucketList().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        //
        public ImageView backImage;
        // 封面
        public ImageView imageView;
        public ImageView choose_back;
        // 文件夹名称
        public TextView folderName;
        // 文件夹里面的图片数量
        public TextView fileNum;
    }

    ViewHolder holder = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            相册的首页显示
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_plugin_camera_select_folder, parent, false);
            holder = new ViewHolder();
//            相册封面后面点缀的图片
            holder.backImage = (ImageView) convertView
                    .findViewById(R.id.file_back);
//            相册封面
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.file_image);
//
            holder.choose_back = (ImageView) convertView
                    .findViewById(R.id.choose_back);
            holder.folderName = (TextView) convertView.findViewById(R.id.name);
            holder.fileNum = (TextView) convertView.findViewById(R.id.filenum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String path;
//        获取文件夹下的图片不为空
        if (allpath.getImagebucketList().get(position).imageList != null) {
            //封面图片路径
            path = allpath.getImagebucketList().get(position).imageList.get(0).imagePath;
            // 给folderName设置值为文件夹名称
            holder.folderName.setText(allpath.getImagebucketList().get(position).bucketName);
            // 给fileNum设置文件夹内图片数量
            holder.fileNum.setText("" + allpath.getImagebucketList().get(position).count);
        } else {
            path = "android_hybrid_camera_default";
        }
        if (path.contains("android_hybrid_camera_default"))
            holder.imageView.setImageResource(R.drawable.plugin_camera_no_pictures);
        else {
            final String minpicpath = "file://" + allpath.getImagebucketList().get(position).imageList.get(0).imagePath;
            Glide.with(mContext)
                    .load(minpicpath)
                    .into(holder.imageView);
        }

        // 为封面添加监听
        holder.imageView.setOnClickListener(new ImageViewClickListener(position, mIntent, holder.choose_back, allpath.getImagebucketList().get(position)));
        return convertView;
    }


    // 为每一个文件夹构建的监听器
    private class ImageViewClickListener implements View.OnClickListener {
        private int position;
        private Intent intent;
        private ImageView choose_back;
        private ImageBucket bucket;

        public ImageViewClickListener(int position, Intent intent, ImageView choose_back, ImageBucket bucket) {
            this.position = position;
            this.intent = intent;
            this.choose_back = choose_back;
            this.bucket = bucket;
        }

        public void onClick(View v) {
//            文件夹名称
            String folderName = bucket.bucketName;
//            图片文件存储路径
            ArrayList<ImageItem> itempath = (ArrayList<ImageItem>) bucket.imageList;
//            放在文件下的图片路径
            ArrayList<String> imagepath_bucket = new ArrayList<>();
            for (int a = 0; a < itempath.size(); a++) {
                imagepath_bucket.add(itempath.get(a).imagePath);
            }
            Intent intent = new Intent();
            intent.putExtra("bucket_imagelist", imagepath_bucket);
            intent.putExtra("folderName", folderName);
            intent.putExtra("path", allpath);
            intent.setClass(mContext, ShowAllPhoto.class);
            mContext.startActivity(intent);
            choose_back.setVisibility(v.VISIBLE);
        }
    }
}
