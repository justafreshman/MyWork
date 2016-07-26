package com.example.administrator.mywork.Until.Multpic_pic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.PhotoActivity;
import com.example.administrator.mywork.Until.Multpic_pic.adapter.AlbumGridViewAdapter;
import com.example.administrator.mywork.Until.Multpic_pic.bean.AllImagepath;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.PublicWay;
import com.example.administrator.mywork.Until.Multpic_pic.until.littleuntil;


public class AlbumActivity extends Activity implements View.OnClickListener{
//    adapter
    private AlbumGridViewAdapter gridImageAdapter;
//    完成按钮
    private Button okButton;
//
    private Intent intent;
//    预览按钮
    private Button preview;
//
    private Context mContext;

//    所有图片的路径
    private AllImagepath allpath;
    private String mess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.photo_plugin_camera_album);

        Intent intent = getIntent();
        allpath = intent.getParcelableExtra("path");
        PublicWay.activityList.add(this);
        mContext = this;
        init();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
        isShowOkBt();
//        为了不让前面选中取消
        if(Bimp.tempSelectBitmappath_sure.size()>0){
            Bimp.tempSelectBitmappath.clear();
            for(int i = 0;i < Bimp.tempSelectBitmappath_sure.size();i++){
                Bimp.tempSelectBitmappath.add(Bimp.tempSelectBitmappath_sure.get(i));
            }
        }
    }


//  初始化组件
    private void init() {
        //    相册按钮
         Button albumbtn;
//    取消按钮
         Button cancelbtn;
//    显示手机里的所有图片的列表控件
         GridView gridView;
//    手机没有图片 提示用户没有图片
         TextView tv;

        albumbtn = (Button) findViewById(R.id.album);
        cancelbtn = (Button) findViewById(R.id.cancel);
        preview = (Button) findViewById(R.id.preview);
        cancelbtn.setOnClickListener(this);
        albumbtn.setOnClickListener(this);
        preview.setOnClickListener(this);
        albumbtn.setText("相册");
        intent = getIntent();
        gridView = (GridView) findViewById(R.id.myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this,allpath.getImagepath());
        gridView.setAdapter(gridImageAdapter);
//        设置没有图片的显示
        tv = (TextView) findViewById(R.id.myText);
        gridView.setEmptyView(tv);
        okButton = (Button) findViewById(R.id.ok_button);
        mess = getResources().getString(R.string.complete) +"(" + Bimp.tempSelectBitmappath.size() + "/"+ PublicWay.num+")";
        okButton.setText(mess);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            启动相册
            case R.id.album:
                intent.putExtra("path",allpath);
                intent.setClass(AlbumActivity.this, ImageFile.class);
                startActivity(intent);
                break;
            case R.id.cancel:
                Bimp.tempSelectBitmappath.clear();
                intent.setClass(mContext, PhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.preview:
                changepage();
//                进入图片轮放的Intent
                intent.setClass(AlbumActivity.this, GalleryActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.ok_button:
                changepage();
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                intent.setClass(mContext, PhotoActivity.class);
//
                startActivity(intent);
                finish();
                break;
        }
    }




    public void changepage(){
        //                增加图片的
        if(Bimp.tempSelectBitmappath.size()>Bimp.tempSelectBitmappath_sure.size()){
            Bimp.tempSelectBitmappath_sure.clear();
            for(int i = 0;i < Bimp.tempSelectBitmappath.size();i++){
                Bimp.tempSelectBitmappath_sure.add(Bimp.tempSelectBitmappath.get(i));
            }
        }else if(Bimp.tempSelectBitmappath.size()<Bimp.tempSelectBitmappath_sure.size()){//减少图片的
            Bimp.tempSelectBitmappath_sure.clear();
            for(int i = 0;i < Bimp.tempSelectBitmappath.size();i++){
                Bimp.tempSelectBitmappath_sure.add(Bimp.tempSelectBitmappath.get(i));
            }
        }
    }

// 初始化监听器
    private void initListener() {
//        显示全部图片选择的监听器   图片的监听器
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, Button chooseBt) {
//                警告不要超出选中张数
                if (Bimp.tempSelectBitmappath.size() >= PublicWay.num) {
                    toggleButton.setChecked(false);
                    chooseBt.setVisibility(View.GONE);
                    if (!removeOneData(allpath.getImagepath().get(position))) {
                        Toast.makeText(AlbumActivity.this, R.string.only_choose_num, Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
//
                if (isChecked) {
                    chooseBt.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmappath.add(allpath.getImagepath().get(position));
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
                    okButton.setText(mess);
                } else {
                    Bimp.tempSelectBitmappath.remove(allpath.getImagepath().get(position));
                    chooseBt.setVisibility(View.GONE);
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
                    okButton.setText(mess);
                }
                isShowOkBt();
            }
        });
    }

    //  双击的时候出现的情况
    private boolean removeOneData(String path) {
        if (littleuntil.checksamepath(Bimp.tempSelectBitmappath, path)) {
            Bimp.tempSelectBitmappath.remove(path);
            mess =getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
            okButton.setText(mess);
            return true;
        }
        return false;
    }
//   根据图片的数量来控制按钮的是否点击还有颜色
    public void isShowOkBt() {
        if (Bimp.tempSelectBitmappath.size() > 0) {
            mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/"+PublicWay.num+")";
            okButton.setText(mess);
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            mess = getResources().getString(R.string.complete) +"(" + Bimp.tempSelectBitmappath.size() + "/"+PublicWay.num+")";
            okButton.setText(mess);
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }


    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
