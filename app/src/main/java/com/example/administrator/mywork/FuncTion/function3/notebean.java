package com.example.administrator.mywork.FuncTion.function3;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.mywork.BR;

/**
 * Created by Administrator on 2016/7/25.
 * 作者：wu
 */
public  class notebean extends BaseObservable implements Parcelable{
    private String title;
    private String content;
    private String date;
    private String id;
    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeString(this.id);
    }

    public notebean() {
    }

    protected notebean(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.id = in.readString();
    }

    public static final Creator<notebean> CREATOR = new Creator<notebean>() {
        @Override
        public notebean createFromParcel(Parcel source) {
            return new notebean(source);
        }

        @Override
        public notebean[] newArray(int size) {
            return new notebean[size];
        }
    };
}
