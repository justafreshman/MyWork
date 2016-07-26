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
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.JoJo_Ke;
import com.example.administrator.mywork.bean.Joke;
import com.example.administrator.mywork.databinding.FirstOneBinding;
import com.example.administrator.mywork.databinding.FirstThreeBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 * 作者：wu
 */
public class pagethree extends Fragment {
    private FirstOneBinding mFob;
    private ProgressSubscriber<Joke> mSubscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFob = DataBindingUtil.inflate(inflater, R.layout.first_one, container, false);
        mSubscriber = NewSubscriber();
        Data.HTTP_METHODS.getJokeImgByTime(mSubscriber);
        return mFob.getRoot();
    }

    public ProgressSubscriber<Joke> NewSubscriber(){
        return new ProgressSubscriber<>(new SubscriberOnNextListener<Joke>() {
            @Override
            public void onNext(Joke joke) {
                JokeAdapter ja = new JokeAdapter(joke.getData(),JokeAdapter.IMAGE_GONE,getActivity());
                mFob.TimeJoke.setLayoutManager(new LinearLayoutManager(getContext()));
                mFob.TimeJoke.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.bottom = 50;
                    }
                });
                mFob.TimeJoke.setAdapter(ja);
            }
        },getContext());
    }
}
