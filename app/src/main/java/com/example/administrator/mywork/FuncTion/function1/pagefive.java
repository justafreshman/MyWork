package com.example.administrator.mywork.FuncTion.function1;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.FuncTion.function1.Adapter.JokeAdapter;
import com.example.administrator.mywork.FuncTion.function1.Adapter.JokeAdapter_ke;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.JoJo_Ke;
import com.example.administrator.mywork.bean.Joke;
import com.example.administrator.mywork.databinding.FirstFiveBinding;
import com.example.administrator.mywork.databinding.FirstOneBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 * 作者：wu
 */
public class pagefive extends Fragment{
    private FirstOneBinding mFob;
    private ProgressSubscriber<List<JoJo_Ke>> mSubscriber;
    private ArrayList mList;
    private JokeAdapter_ke mJa;
    private Boolean isUpdata =false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFob = DataBindingUtil.inflate(inflater, R.layout.first_one, container, false);
        mSubscriber = NewSubscriber();
        mList = new ArrayList();
        Data.HTTP_METHODS.getRandJoke(mSubscriber);
        mFob.TimeJoke.addOnScrollListener(new MyScroll());
        return mFob.getRoot();
    }

    public ProgressSubscriber<List<JoJo_Ke>> NewSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<List<JoJo_Ke>>() {

            @Override
            public void onNext(List<JoJo_Ke> joJo_kes) {
                mList.addAll(joJo_kes);
                if(joJo_kes.size()<10) isUpdata = true;
                if(mJa==null){
                    mJa = new JokeAdapter_ke(mList, JokeAdapter.IMAGE_VISIBLE,getActivity());
                    mFob.TimeJoke.setLayoutManager(new LinearLayoutManager(getContext()));
                    mFob.TimeJoke.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            outRect.bottom = 50;
                        }
                    });
                    mFob.TimeJoke.setAdapter(mJa);
                }else {
                    mJa.notifyDataSetChanged();
                }

            }
        },getContext());
    }


    public class MyScroll extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItem = manager.getItemCount();
            int lastItem = manager.findLastCompletelyVisibleItemPosition();
            if(lastItem==(totalItem-1)&&newState == recyclerView.SCROLL_STATE_IDLE){
                if(!isUpdata){
                    if(mSubscriber!=null){
                        mSubscriber.unsubscribe();
                    }
                    mSubscriber = NewSubscriber();
                    Data.HTTP_METHODS.getRandJoke(mSubscriber);
                }else {
                    ToastUntil.showmess(getActivity(), "没有更多啦");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
