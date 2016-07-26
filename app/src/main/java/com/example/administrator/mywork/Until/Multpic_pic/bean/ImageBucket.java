package com.example.administrator.mywork.Until.Multpic_pic.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ImageBucket implements Parcelable{
//    记录文件夹下有多小张图片的变量
    public int count = 0;
//   记录的是图片文件的存放的文件夹
    public String bucketName;
//   存放的是文件夹下的图片
    public List<ImageItem> imageList;
    public Bitmap mBitmap;

    public ImageBucket(){}

    protected ImageBucket(Parcel in) {
        mBitmap = in.readParcelable(getClass().getClassLoader());
        count = in.readInt();
        bucketName = in.readString();
        imageList = in.createTypedArrayList(ImageItem.CREATOR);
    }

    public static final Creator<ImageBucket> CREATOR = new Creator<ImageBucket>() {
        @Override
        public ImageBucket createFromParcel(Parcel in) {
            return new ImageBucket(in);
        }

        @Override
        public ImageBucket[] newArray(int size) {
            return new ImageBucket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mBitmap,0);
        dest.writeInt(count);
        dest.writeString(bucketName);
        dest.writeTypedList(imageList);
    }
}
