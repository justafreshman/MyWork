package com.example.administrator.mywork.FuncTion;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mywork.Base.BaseFragment;
import com.example.administrator.mywork.FuncTion.function1.pagefive;
import com.example.administrator.mywork.FuncTion.function1.pagefour;
import com.example.administrator.mywork.FuncTion.function1.pageone;
import com.example.administrator.mywork.FuncTion.function1.pagethree;
import com.example.administrator.mywork.FuncTion.function1.pagetwo;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.databinding.FirstBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：wu
 */
public class first extends BaseFragment{
    private pageone one;
    private pagetwo two;
    private pagethree three;
    private pagefour four;
    private pagefive five;

    private List<Fragment> list_fragment;

    private List<String> list_title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirstBinding fb = DataBindingUtil.inflate(inflater, R.layout.first, container, false);
        View v = fb.getRoot();
        init(fb);
        return v;
    }

    private void init(FirstBinding fb) {
        one =  new pageone();
        two = new pagetwo();
        three = new pagethree();
        four = new pagefour();
        five = new pagefive();

        list_fragment= new ArrayList<>();
        list_fragment.add(one);
//        list_fragment.add(two);
//        list_fragment.add(three);
//        list_fragment.add(four);
        list_fragment.add(five);

//        .按更新时间查询笑话
//        2.最新笑话
//        3.按更新时间查询趣图
//        4.最新趣图
//        5.随机获取趣图/笑话
        list_title = new ArrayList<>();
        list_title.add("按更新时间查询笑话");
//        list_title.add("最新笑话");
//        list_title.add("按更新时间查询趣图");
//        list_title.add("最新趣图");
        list_title.add("随机获取趣图/笑话");

        //设置TabLayout的模式
        fb.tabTitleName.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 下方滚动的下划线颜色
        fb.tabTitleName.setSelectedTabIndicatorColor(Color.GREEN);
        // tab被选中后，文字的颜色
        fb.tabTitleName.setSelectedTabIndicatorColor(Color.BLUE);
//        // tab默认的文字颜色
//        fb.tabTitleName.setTabTextColors(0,0);

        //为TabLayout添加tab名称
        for (int i = 0; i < list_title.size(); i++) {
            fb.tabTitleName.addTab(fb.tabTitleName.newTab().setText(list_title.get(i)));
        }

        MyFragmentAdapter adapter = new MyFragmentAdapter(getChildFragmentManager()
        ,list_fragment,list_title);

        fb.tabContent.setAdapter(adapter);
        //TabLayout加载viewpager
        fb.tabTitleName.setupWithViewPager(fb.tabContent);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    public class MyFragmentAdapter extends FragmentPagerAdapter{
        private List<Fragment> list_fragment;
        private List<String> list_title;


        public MyFragmentAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_title = list_title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position%list_title.size());
        }
    }
}
