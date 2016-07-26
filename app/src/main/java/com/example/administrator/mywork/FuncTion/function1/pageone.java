package com.example.administrator.mywork.FuncTion.function1;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.FuncTion.function1.Adapter.JokeAdapter;
import com.example.administrator.mywork.HttpMethod.HttpMethods;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.Joke;
import com.example.administrator.mywork.databinding.FirstOneBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/7/8.
 * 作者：wu
 */
public class pageone extends Fragment {
    private Boolean isUpdata = false;
    private ProgressSubscriber<Joke> mSubscriber;
    private FirstOneBinding mFob;
    private ArrayList mList;
    private JokeAdapter mJa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFob = DataBindingUtil.inflate(inflater, R.layout.first_one, container, false);
        mSubscriber = NewSubscriber();
        mList = new ArrayList();
        Data.HTTP_METHODS.getJokeByTime(mSubscriber);
        mFob.TimeJoke.addOnScrollListener(new MyScroll());
        return mFob.getRoot();
    }

    public ProgressSubscriber<Joke> NewSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<Joke>() {
            @Override
            public void onNext(Joke joke) {
                mList.addAll(joke.getData());
                if(joke.getData().size()<10){
                    isUpdata = true;
                }
                if(mJa==null){
                    mJa = new JokeAdapter(mList);
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
        Boolean isToBottom = false;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItem = manager.getItemCount();
            // 判断是否滚动到底部，并且是向下滚动

            if (lastItem == (totalItem - 1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                if(!isUpdata){
                    if(mSubscriber!=null){
                        mSubscriber.unsubscribe();
                    }
                    mSubscriber = NewSubscriber();
                    Data.HTTP_METHODS.getJokeByTime(mSubscriber);
                }else {
                    ToastUntil.showmess(getActivity(), "没有更多啦");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(dy>0){
                isToBottom = true;
            }else {
                isToBottom = false;
            }
        }

    }

}
