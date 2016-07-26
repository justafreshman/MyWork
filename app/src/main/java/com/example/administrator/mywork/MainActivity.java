package com.example.administrator.mywork;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.FuncTion.fifth;
import com.example.administrator.mywork.FuncTion.first;
import com.example.administrator.mywork.FuncTion.forth;
import com.example.administrator.mywork.FuncTion.second;
import com.example.administrator.mywork.FuncTion.third;
import com.example.administrator.mywork.Left_Menu_Function.Query_BusStation;
import com.example.administrator.mywork.Left_Menu_Function.Query_BusStation_Mess;
import com.example.administrator.mywork.Left_Menu_Function.Query_Driver;
import com.example.administrator.mywork.Left_Menu_Function.Query_Identity;
import com.example.administrator.mywork.Left_Menu_Function.Query_PhoneNum;
import com.example.administrator.mywork.Left_Menu_Function.Query_Postal;
import com.example.administrator.mywork.databinding.MainpageBinding;

/**
 * Created by Administrator on 2016/6/29.
 * 作者：wu
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private MainpageBinding mMb;
    private FragmentManager fm;
    private first first;
    private second second;
    private third third;
    private forth forth;
    private fifth fifth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        mMb = DataBindingUtil.setContentView(this, R.layout.mainpage);

        initFiled();
        initEvent();

    }

    private void initFiled() {
        fm = getSupportFragmentManager();
    }

    private void initEvent() {
        mMb.buttomButton.first.setOnClickListener(this);
        mMb.buttomButton.second.setOnClickListener(this);
        mMb.buttomButton.third.setOnClickListener(this);
        mMb.buttomButton.forth.setOnClickListener(this);
        mMb.buttomButton.fifth.setOnClickListener(this);

        setSupportActionBar(mMb.topbar.head);
        mMb.topbar.head.setLogo(R.mipmap.ic_launcher);
        mMb.topbar.head.setTitle("最帅的程序员");
        mMb.topbar.head.setSubtitle("程序员");
        mMb.topbar.head.setNavigationIcon(R.mipmap.ic_launcher);

        mMb.topbar.head.setBackgroundColor(getResources().getColor(R.color.darkorange));
        setSupportActionBar(mMb.topbar.head);

        mMb.fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMb.fresh.setRefreshing(false);
            }
        });
        mMb.fresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorRed);
        mMb.fresh.setRefreshing(false);
        mMb.fresh.setEnabled(false);
        mMb.idNvLeftMenu.setNavigationItemSelectedListener(this);

        setTabsecetion(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mMb.drawLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.firstItem:
                Toast.makeText(this,"使用first",Toast.LENGTH_SHORT).show();
                break;
            case R.id.secondItem:
                Toast.makeText(this,"使用second",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first:
                setTabsecetion(0);
                break;
            case R.id.second:
                setTabsecetion(1);
                break;
            case R.id.third:
                setTabsecetion(2);
                break;
            case R.id.forth:
                setTabsecetion(3);
                break;
            case R.id.fifth:
                setTabsecetion(4);
                break;
        }

    }

    private void setTabsecetion(int i){
        resbtn();
        FragmentTransaction ft = fm.beginTransaction();
        hide(ft);
        switch (i){
            case 0:
                if(first==null){
                    first = new first();
                    ft.add(R.id.main_content,first);
                }else {
                    ft.show(first);
                }
                break;
            case 1:
                if(second==null){
                    second = new second();
                    ft.add(R.id.main_content,second);
                }else {
                    ft.show(second);
                }
                break;
            case 2:
                if(third==null){
                    third = new third();
                    ft.add(R.id.main_content,third);
                }else {
                    ft.show(third);
                }
                break;
            case 3:
                if(forth==null){
                    forth = new forth();
                    ft.add(R.id.main_content,forth);
                }else {
                    ft.show(forth);
                }
                break;
            case 4:
                Toast.makeText(this,"test5",Toast.LENGTH_SHORT).show();
                break;
        }
        ft.commit();
    }

//    重置字体颜色
    private void resbtn(){
        mMb.buttomButton.messone.setTextColor(Color.parseColor("#a9a9a9"));
        mMb.buttomButton.messtwo.setTextColor(Color.parseColor("#a9a9a9"));
        mMb.buttomButton.messfour.setTextColor(Color.parseColor("#a9a9a9"));
        mMb.buttomButton.messfive.setTextColor(Color.parseColor("#a9a9a9"));
    }

//  隐藏Fragment的显示和隐藏
    private void hide(FragmentTransaction transaction){
        if(first!=null){
            transaction.hide(first);
        }
        if(second!=null){
            transaction.hide(second);
        }
        if(third!=null){
            transaction.hide(third);
        }
        if(forth!=null){
            transaction.hide(forth);
        }
        if(fifth!=null){
            transaction.hide(fifth);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.nav_mail:
                intent.setClass(MainActivity.this, Query_Postal.class);
                break;
            case R.id.nav_phone:
                intent.setClass(MainActivity.this, Query_PhoneNum.class);
                break;
            case R.id.nav_identity:
                intent.setClass(MainActivity.this, Query_Identity.class);
                break;
            case R.id.nav_Drivers_license_exam:
                intent.setClass(MainActivity.this, Query_Driver.class);
                break;
            case R.id.sub_motor_station:
                intent.setClass(MainActivity.this, Query_BusStation.class);
                break;
            case R.id.sub_motor_station_mess:
                intent.setClass(MainActivity.this, Query_BusStation_Mess.class);
                break;
        }
        startActivity(intent);
        return false;
    }
}
