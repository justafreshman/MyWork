package com.example.administrator.mywork.Until.Multpic_pic.until;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/5/23.
 * 作者：wu
 */
public class InputHide {
    private  InputMethodManager imm = null;
    public InputHide(Context context) {
        imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void hideInput(View v){
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public Boolean hideornot(){
        boolean isOpen=imm.isActive();
        return isOpen;
    }
}
