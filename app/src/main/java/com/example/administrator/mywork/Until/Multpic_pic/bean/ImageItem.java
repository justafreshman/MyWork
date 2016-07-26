package com.example.administrator.mywork.Until.Multpic_pic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/12.
 */


//序列化之后 对象还是那个对象只是引用地址不同了
public class ImageItem implements Parcelable{
    public String imageId;
    public String thumbnailPath;
//    只有这个用到
    public String imagePath;
    public boolean isSelected = false;

    public ImageItem(){

    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

//写入接口函数，打包
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageId);
        dest.writeString(thumbnailPath);
        dest.writeString(imagePath);
        dest.writeInt(isSelected ? 1 : 0);

    }


    //读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
    // 因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入
    //为了能够实现模板参数的传入，这里定义Creator嵌入接口,
    // 内含两个接口函数分别返回单个和多个继承类实例
    protected ImageItem(Parcel in) {
        imageId = in.readString();
        thumbnailPath = in.readString();
        imagePath = in.readString();

        isSelected = in.readByte() != 0;
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
