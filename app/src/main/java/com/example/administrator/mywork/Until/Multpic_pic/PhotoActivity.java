package com.example.administrator.mywork.Until.Multpic_pic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.Multpic_pic.activity.AlbumActivity;
import com.example.administrator.mywork.Until.Multpic_pic.activity.GalleryActivity;
import com.example.administrator.mywork.Until.Multpic_pic.bean.AllImagepath;
import com.example.administrator.mywork.Until.Multpic_pic.until.AlbumHelper;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bimp;
import com.example.administrator.mywork.Until.Multpic_pic.until.Bitmapcompress;
import com.example.administrator.mywork.Until.Multpic_pic.until.InputHide;
import com.example.administrator.mywork.Until.Multpic_pic.until.PublicWay;
import com.example.administrator.mywork.Until.ToastUntil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends Activity implements View.OnClickListener {
    public GridView noScrollgridview;
    //
    private View parentView;
    //    弹出窗体
    private PopupWindow pop = null;
    //
    private LinearLayout ll_popup;
    // 适配器
    public GridAdapter adapter;
    //  工具类 获取所需的略缩图
    private AlbumHelper helper;
    //    //        略缩图路径
//    public static ArrayList<String> thumbnailPath;
    //    拍照的通知代码
    private static final int TAKE_PICTURE = 0x000001;


    private AllImagepath allpath;
    // 拍照的略缩图图片
    public static Bitmap bitmap = null;
//    提醒是否需要更新
    private Boolean update = true;
    //    用户的写的信息
//    private EditText person_mess_show;
    private InputHide inputHide;

    private ImageButton mGoback;
//    显示上传的等待时间
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      初始化是否关闭键盘
        inputHide = new InputHide(PhotoActivity.this);
        PublicWay.activityList.add(this);
//        获取布局
        parentView = getLayoutInflater().inflate(R.layout.photo_activity_selectimg, null);
        setContentView(parentView);
//        FileUtils.createdir();
//        初始化控件
        init();
    }




    protected void isupdate() {
        if(update){
            helper.refreshornot(true);
            ArrayList first = new ArrayList();
            ArrayList second = new ArrayList();
            ArrayList third = new ArrayList();
            first.clear();
            second.clear();
            third.clear();
            first = helper.getImagesBucketList();
            second = helper.getImageItemList(first);
            third = helper.getallImagepath(second);
            Log.d("sizeoflist",third.size()+"third");
            allpath = new AllImagepath(first, second, third);
            update = false;
        }
    }


    private void init() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        mGoback = (ImageButton) findViewById(R.id.phototgoback);
        mGoback.setOnClickListener(this);
        findViewById(R.id.photo_select).setOnClickListener(this);

//        用户的写的信息
//        person_mess_show = (EditText) findViewById(R.id.person_mess_show);
        //        弹出窗体
        pop = new PopupWindow(PhotoActivity.this);
//        唤醒布局
        View view = getLayoutInflater().inflate(R.layout.photo_item_popupwindows, null);
//        承载弹出窗体的布局
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        pop.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(null)));
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

//      三个按钮的点击事件
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.activity_selectimg_send);
        textView.setOnClickListener(this);

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
//      选中图片的背景颜色
        noScrollgridview.setSelection(Color.TRANSPARENT);
        adapter = new GridAdapter(this);

        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(inputHide.hideornot()){
                    inputHide.hideInput(view);
                }
                if (position == Bimp.tempSelectBitmappath_sure.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(PhotoActivity.this,
                            R.anim.activity_translate_in));
//                   设置弹出窗体的出现的位置
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
//              进入浏览图片的模式
                    Intent intent = new Intent(PhotoActivity.this, GalleryActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });
    }

    //    点击事件
    @Override
    public void onClick(View v) {
        if(inputHide.hideornot()){
            inputHide.hideInput(v);
        }
        switch (v.getId()) {
            case R.id.parent:
                //                弹窗消失
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_camera:
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_Photo:
//               耗时操作放进子线程中进行加载
                handler.sendEmptyMessage(0);
                break;
            case R.id.item_popupwindows_cancel:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.activity_selectimg_send:
                ArrayList<String> bimp = Bimp.tempSelectBitmappath_sure;
                Intent intent = new Intent();
                intent.setAction("picpath");
                intent.putStringArrayListExtra("path",bimp);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.phototgoback:
                onStop();
                onDestroy();
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    isupdate();
//           打开相册 在相册中查看
                    Intent intent = new Intent(PhotoActivity.this, AlbumActivity.class);
                    intent.putExtra("flag","0");
                    intent.putExtra("path", allpath);
                    startActivity(intent);
//                能够快速响应
                    overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                    pop.dismiss();
                    ll_popup.clearAnimation();
                    break;
            }

        }
    };


    //  设置数组传进去
    private class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
//            固定死只可以上传九张图片
            if (Bimp.tempSelectBitmappath_sure.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmappath_sure.size() + 1);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.photo_item_published_grida, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmappath_sure.size()) {
//                holder.image.setImageResource(R.drawable.icon_addpic_focused);
                Glide.with(PhotoActivity.this)
                        .load(R.drawable.icon_addpic_focused)
                        .into(holder.image);
                if (position == 9) {
                    holder.image.setImageBitmap(null);
                }
            } else {
                String path = "file://" + Bimp.tempSelectBitmappath_sure.get(position);
//                显示图片
                Glide.with(PhotoActivity.this)
                        .load(path)
                        .into(holder.image);
            }
            return convertView;
        }
    }


    //  adapter拥有缓存机制 不用每次都查找
    protected class ViewHolder {
        public ImageView image;
    }

    @Override
    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();
    }

    private File file = null;
//  拍照
    private void photo() {
        destoryBimap();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory() + "/temple";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String filename = String.valueOf(System.currentTimeMillis());
            file = new File(saveDir, filename + ".jpg");
            Intent openCamereIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            openCamereIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(openCamereIntent, TAKE_PICTURE);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    //    获取的是略缩图
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String pic_path = null;
        switch (requestCode) {
//            获取的是系统压缩过的图片
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmappath_sure.size() < 9 && resultCode == RESULT_OK) {
                    if (file != null && file.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(file);
                            if (fis.available() == 0) {
                                file.delete();
                            } else {
                                pic_path = file.getPath();
//                                通知系统数据库更新
                                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                scanIntent.setData(Uri.fromFile(new File(pic_path)));
                                sendBroadcast(scanIntent);
                                Bimp.tempSelectBitmappath_sure.add(pic_path);
                                Bimp.tempSelectBitmappath.add(pic_path);
                                update = true;
                            }
                        } catch (FileNotFoundException e) {
//                            文件不存在
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(this, "拍照失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 111:
                ToastUntil.showmess(PhotoActivity.this,"我中都");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            Bimp.tempSelectBitmappath_sure.clear();
            Bimp.tempSelectBitmappath.clear();
        }
        return true;
    }

    /**
     * 销毁图片文件
     */
    private void destoryBimap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }



    @Override
    protected void onStop() {
        Log.d("onstop", "stop?");
        if (bitmap != null) {
            bitmap.recycle();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.tempSelectBitmappath_sure.clear();
        Bimp.tempSelectBitmappath.clear();
        for (int i = 0; i < PublicWay.activityList.size(); i++) {
            if (null != PublicWay.activityList.get(i)) {
                PublicWay.activityList.get(i).finish();
            }
        }
    }
}
