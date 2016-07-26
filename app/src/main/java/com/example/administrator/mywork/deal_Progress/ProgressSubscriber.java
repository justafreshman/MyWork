package com.example.administrator.mywork.deal_Progress;

import android.content.Context;
import android.util.Log;

import com.example.administrator.mywork.Interface.ProgressCancelListener;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }


    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Log.d("qwe",e.toString());
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        Log.d("qwe","3333"+t.toString());
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
