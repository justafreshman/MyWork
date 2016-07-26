package com.example.administrator.mywork.Until.Multpic_pic.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/3.
 * 作者：wu
 */
public class AllImagepath implements Parcelable{
    //   图片及其路径
    private ArrayList<ImageItem> dataItemImageList = new ArrayList<>();
    //   文件夹及其下面的图片
    private ArrayList<ImageBucket> imagebucketList  = new ArrayList<>();
    //  获取所有图片的路径
    private ArrayList<String> imagepath  = new ArrayList<>();

    public AllImagepath(ArrayList<ImageBucket> imagebucketList,ArrayList<ImageItem> dataItemImageList, ArrayList<String> imagepath) {
        this.dataItemImageList.clear();
        this.imagebucketList.clear();
        this.imagepath.clear();
        Log.d("sizeoflist",imagepath.size()+"");
        this.dataItemImageList = dataItemImageList;
        this.imagepath = imagepath;
        this.imagebucketList = imagebucketList;
    }

    public ArrayList<ImageItem> getDataItemImageList() {
        return dataItemImageList;
    }

    public void setDataItemImageList(ArrayList<ImageItem> dataItemImageList) {
        this.dataItemImageList = dataItemImageList;
    }

    public ArrayList<ImageBucket> getImagebucketList() {
        return imagebucketList;
    }

    public void setImagebucketList(ArrayList<ImageBucket> imagebucketList) {
        this.imagebucketList = imagebucketList;
    }

    public ArrayList<String> getImagepath() {
        return imagepath;
    }

    public void setImagepath(ArrayList<String> imagepath) {
        this.imagepath = imagepath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(dataItemImageList);
        dest.writeTypedList(imagebucketList);
        dest.writeStringList(imagepath);

    }

    protected AllImagepath(Parcel in) {
        dataItemImageList = in.createTypedArrayList(ImageItem.CREATOR);
        imagebucketList = in.createTypedArrayList(ImageBucket.CREATOR);
        imagepath = in.createStringArrayList();
    }


    public static final Creator<AllImagepath> CREATOR = new Creator<AllImagepath>() {
        @Override
        public AllImagepath createFromParcel(Parcel in) {
            return new AllImagepath(in);
        }

        @Override
        public AllImagepath[] newArray(int size) {
            return new AllImagepath[size];
        }
    };
}
