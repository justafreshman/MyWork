package com.example.administrator.mywork.Until;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/4.
 * 作者：wu
 */
public class ToastUntil {
    public static void showmess(Context context,String mess){
        Toast.makeText(context,mess,Toast.LENGTH_SHORT).show();
    }
}
