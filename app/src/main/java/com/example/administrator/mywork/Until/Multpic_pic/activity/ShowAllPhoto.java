package com.example.administrator.mywork.Until.Multpic_pic.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.PhotoActivity;
import com.example.administrator.mywork.Until.Multpic_pic.adapter.AlbumGridViewAdapter;
import com.example.administrator.mywork.Until.Multpic_pic.bean.AllImagepath;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.PublicWay;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
*/
public class ShowAllPhoto extends Activity implements View.OnClickListener{

    private AlbumGridViewAdapter gridImageAdapter;
    // 完成按钮
    private Button okButton;
    // 预览按钮
    private Button preview;
    // 标题
    private TextView headTitle;
    private Intent intent;
//    显示图片
    public  ArrayList<String> folder_pic;

    private AllImagepath allImagepath;
    private String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_plugin_camera_show_all_photo);
        initViw();
        initData();
        init();
        initItemListener();
        isShowOkBt();
    }
    private void initViw() {


        // 返回按钮
         Button back;
        // 取消按钮
         Button cancel;
        back = (Button) findViewById(R.id.showallphoto_back);
        cancel = (Button) findViewById(R.id.showallphoto_cancel);
        preview = (Button) findViewById(R.id.showallphoto_preview);
        okButton = (Button) findViewById(R.id.showallphoto_ok_button);
        headTitle = (TextView) findViewById(R.id.showallphoto_headtitle);
        okButton.setOnClickListener(this);
        cancel.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initData() {
        String folderName;
        PublicWay.activityList.add(this);
        this.intent = getIntent();
        preview.setVisibility(View.GONE);
//        获取文件命
        folderName = intent.getStringExtra("folderName");
//        获取图片路径列表
        folder_pic = intent.getStringArrayListExtra("bucket_imagelist");
//        获取allpath
        allImagepath = intent.getParcelableExtra("path");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        headTitle.setText(folderName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showallphoto_cancel:
                //清空选择的图片
                Bimp.tempSelectBitmappath.clear();
                intent.setClass(ShowAllPhoto.this, PhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.showallphoto_back:
                intent.putExtra("path",allImagepath);
                intent.setClass(ShowAllPhoto.this, ImageFile.class);
                startActivity(intent);
                break;
            case R.id.showallphoto_ok_button:
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
                okButton.setClickable(false);
                intent.setClass(ShowAllPhoto.this, PhotoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void init() {
        GridView gridView;
        ProgressBar progressBar;
//        TODO
        progressBar = (ProgressBar) findViewById(R.id.showallphoto_progressbar);
        progressBar.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.showallphoto_myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this,folder_pic);
        gridView.setAdapter(gridImageAdapter);
    }

    private void initItemListener() {

        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            public void onItemClick(final ToggleButton toggleButton,
                                    int position, boolean isChecked,
                                    Button button) {
                if (Bimp.tempSelectBitmappath.size() >= PublicWay.num && isChecked) {
                    button.setVisibility(View.GONE);
                    toggleButton.setChecked(false);
                    Toast.makeText(ShowAllPhoto.this, R.string.only_choose_num, Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (isChecked) {
                    button.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmappath.add(folder_pic.get(position));
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size()
                            + "/" + PublicWay.num + ")";
                    okButton.setText(mess);
                } else {
                    button.setVisibility(View.GONE);
                    Bimp.tempSelectBitmappath.remove(folder_pic.get(position));
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size()
                            + "/" + PublicWay.num + ")";
                    okButton.setText(mess);
                }
                isShowOkBt();
            }
        });
    }


    public void isShowOkBt() {
        if (Bimp.tempSelectBitmappath.size() > 0) {
            mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size()
                    + "/" + PublicWay.num + ")";
            okButton.setText(mess);
            okButton.setPressed(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
        } else {
            mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size()
                    + "/" + PublicWay.num + ")";
            okButton.setText(mess);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
