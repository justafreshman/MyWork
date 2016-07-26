package com.example.administrator.mywork.Until.Multpic_pic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.PhotoActivity;
import com.example.administrator.mywork.Until.Multpic_pic.adapter.FolderAdapter;
import com.example.administrator.mywork.Until.Multpic_pic.bean.AllImagepath;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.PublicWay;


/**
 * 这个类主要用来显示包含图片的文件夹
*/
public class ImageFile extends Activity implements View.OnClickListener{


    private Context mContext;


    protected void onCreate(Bundle savedInstanceState) {
        //   取消按钮
         Button bt_cancel;
//        文件夹适配器
         FolderAdapter folderAdapter;
         Intent intent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_plugin_camera_image_file);
        mContext = this;
        intent = getIntent();
        AllImagepath allImagepath = intent.getParcelableExtra("path");
        PublicWay.activityList.add(this);
        bt_cancel = (Button) findViewById(R.id.cancel);
        bt_cancel.setOnClickListener(this);
        GridView gridView = (GridView) findViewById(R.id.fileGridView);
        TextView textView = (TextView) findViewById(R.id.headerTitle);
        textView.setText(R.string.photo);
        folderAdapter = new FolderAdapter(this,allImagepath);
        gridView.setAdapter(folderAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                //清空选择的图片
                Bimp.tempSelectBitmappath.clear();
                Intent intent = new Intent();
                intent.setClass(mContext, PhotoActivity.class);
                startActivity(intent);
                break;
        }
    }

//    按返回键的时候
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(mContext, PhotoActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}