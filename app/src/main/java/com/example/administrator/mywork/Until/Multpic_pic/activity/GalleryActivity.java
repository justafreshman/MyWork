package com.example.administrator.mywork.Until.Multpic_pic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.PhotoActivity;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.PublicWay;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class GalleryActivity extends Activity implements View.OnClickListener{

    // 发送按钮
    private Button send_bt;
    private Intent intent;

    //TODO   顶部显示预览图片位置的textview
//    private TextView positionTextView;

    //当前的位置
    private int location = 0;
//  存储被选中的图片
    private ArrayList<PhotoView> imageview_pager = null;

    private ViewPager viewPager;
    private MyPageAdapter adapter;

    private Context mContext;

    private String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageview_pager = new ArrayList<>();
//        // 切屏到主界面
        setContentView(R.layout.photo_plugin_camera_gallery);
        initView();
        mContext = this;


        PublicWay.activityList.add(this);
        intent = getIntent();
        isShowOkBt();
//        TODO
        for (int i = 0; i < Bimp.tempSelectBitmappath.size(); i++) {
//            初始化选择图片
            String path = Bimp.tempSelectBitmappath.get(i);
            initListViews(path);
        }

//      添加数据
        adapter = new MyPageAdapter(imageview_pager);
        viewPager.setAdapter(adapter);
//        设置边距
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
//        获取当前点击的图片位置 没有则为第一张
        int id = intent.getIntExtra("ID", 0);
//        设置当前的ViewPager显示图片的id
        viewPager.setCurrentItem(id);
    }

    private void initView() {
        // 相册按钮
         Button back_bt;
        //删除按钮
         Button del_bt;
        back_bt = (Button) findViewById(R.id.gallery_back);
        send_bt = (Button) findViewById(R.id.send_button);
        del_bt = (Button)findViewById(R.id.gallery_del);
        viewPager = (ViewPager) findViewById(R.id.gallery01);
        back_bt.setOnClickListener(this);
        send_bt.setOnClickListener(this);
        del_bt.setOnClickListener(this);
        viewPager.addOnPageChangeListener(pageChangeListener);

    }

    //   初始化
    private void initListViews(String path) {
        if(path!=null){
            PhotoView pv = (PhotoView) LayoutInflater.from(GalleryActivity.this).inflate(R.layout.photo_gallery_item, null);
            String imagepath = "file://" + path;
                    Glide.with(GalleryActivity.this).load(imagepath).into(pv);
//            pv.setImageBitmap(bitmap);
            imageview_pager.add(pv);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gallery_back:
                finish();
                break;
            case R.id.send_button:
                intent.setClass(mContext,PhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.gallery_del:
                if (imageview_pager.size() == 1) {
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
                    send_bt.setText(mess);
                    Bimp.tempSelectBitmappath.clear();
                    Bimp.tempSelectBitmappath_sure.clear();
                    finish();
                } else {
                    Bimp.tempSelectBitmappath.remove(location);
                    Bimp.tempSelectBitmappath_sure.remove(location);
                    imageview_pager.remove(location);
                    mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
                    send_bt.setText(mess);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    //    viewpager的监听器
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        public void onPageSelected(int arg0) {
//            获取当前的图片位置
            location = arg0;
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        public void onPageScrollStateChanged(int arg0) {
        }
    };




    public void isShowOkBt() {
        if (Bimp.tempSelectBitmappath.size() > 0) {
            mess = getResources().getString(R.string.complete) + "(" + Bimp.tempSelectBitmappath.size() + "/" + PublicWay.num + ")";
            send_bt.setText(mess);
            send_bt.setPressed(true);
            send_bt.setClickable(true);
            send_bt.setTextColor(Color.WHITE);
        } else {
            send_bt.setPressed(false);
            send_bt.setClickable(false);
            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }


    private class MyPageAdapter extends PagerAdapter {
        private List<PhotoView> list;

        public MyPageAdapter(List<PhotoView> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }
//用来实现判断View和Object是否为同一个View。
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
//        用以控制当某个View不需要的时候的回收处理
        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((PhotoView)object);
        }
//        用来得到每个View
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
        /**
         *  POSITION_NONE，则调用 PagerAdapter.destroyItem() 来去掉该对象，
         *  并设置为需要刷新 (needPopulate = true)
         *  以便触发PagerAdapter.instantiateItem() 来生成新的对象。
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
