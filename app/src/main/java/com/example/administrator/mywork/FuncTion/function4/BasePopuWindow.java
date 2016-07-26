package com.example.administrator.mywork.FuncTion.function4;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.administrator.mywork.R;
import com.example.administrator.mywork.databinding.ForthPopuwindowBinding;

/**
 * Created by Administrator on 2016/7/24.
 * 作者：wu
 */
public class BasePopuWindow {
    public static void showpopuwindow(Context context,ViewGroup parent,String content){
        final PopupWindow pw = new PopupWindow();
        ForthPopuwindowBinding fpw = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.forth_popuwindow, parent, false);
//        View v= LayoutInflater.from(context).inflate(R.layout.forth_popuwindow,parent,false);
        pw.setContentView(fpw.getRoot());
        fpw.showHistoryDetail.setText(content);
//        fpw.showHistoryDetail.setMovementMethod(new ScrollingMovementMethod());
        pw.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pw.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        fpw.detailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();;
            }
        });
        pw.setBackgroundDrawable(new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(null)));
        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
}
