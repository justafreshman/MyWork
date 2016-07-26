package com.example.administrator.mywork.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.mywork.R;

import java.security.Key;

/**
 * Created by Administrator on 2016/7/3.
 * 作者：wu
 */
public class SearchView extends LinearLayout{

    private int img_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
    private EditText mEt;
    private ImageView mIv;
    private ImageView mIv2;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    private void init() {
        setBackgroundResource(R.drawable.searchbackground);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams layoutParams = new LayoutParams(img_size,img_size);
        setGravity(Gravity.CENTER_VERTICAL);
//        layoutParams.addRule(CENTER_VERTICAL);
        layoutParams.setMargins(
                dp2px(20), 0, 0, 0
        );
        mIv = new ImageView(getContext());
        mIv.setLayoutParams(layoutParams);
        mIv.setImageResource(R.drawable.ic_search_black_18dp);

        LayoutParams layoutParams2 = new LayoutParams(img_size,img_size);
        layoutParams2.setMargins(
                0, 0, dp2px(3), 0
        );
        mIv2 = new ImageView(getContext());
        mIv2.setLayoutParams(layoutParams2);
        mIv2.setImageResource(R.drawable.ic_highlight_off_black_18dp);

        LayoutParams layoutParams3 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
//        layoutParams3.addRule(RelativeLayout.RIGHT_OF,R.id.search);
        mEt = new EditText(getContext());
        mEt.setSingleLine();
        mEt.setBackground(null);
        mEt.setLayoutParams(layoutParams3);
        mEt.setTextSize(12);

        addView(mIv);
        addView(mEt);
        addView(mIv2);

        initEvent();
    }

    private void initEvent() {
        mIv2.setVisibility(INVISIBLE);
        mIv2.setClickable(true);
        mIv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEt.setText("");
            }
        });


        mEt.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode== KeyEvent.KEYCODE_ENTER){
                    mTextListen.searchThing();
                }
                boolean flag = getSearchText().equals("")?true:false;
                isVisitorNot(flag);
                return false;
            }
        });
    }

    private void isVisitorNot(boolean flag){
        if(flag){
            mIv2.setVisibility(INVISIBLE);
        }else {
            mIv2.setVisibility(VISIBLE);
        }
    }
    private OnEditTextListen mTextListen;


    //    -------------------------------对外暴露的方法-----------------------------------------------------

    public EditText getEditText(){
        return mEt;
    }

    public void AddEditTextListen(OnEditTextListen listen){
        this.mTextListen = listen;
    }

    public interface OnEditTextListen{
        void searchThing();
    }
//    设置提示词语s
    public void setHintText(String Text){
        mEt.setHint(Text);
    }

    public String getSearchText(){
        return mEt.getText().toString().trim();
    }
}
