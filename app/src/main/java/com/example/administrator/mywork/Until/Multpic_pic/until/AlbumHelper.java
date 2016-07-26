package com.example.administrator.mywork.Until.Multpic_pic.until;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import com.example.administrator.mywork.Until.Multpic_pic.bean.ImageBucket;
import com.example.administrator.mywork.Until.Multpic_pic.bean.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AlbumHelper {
    Context context;
    ContentResolver cr;


/**
 * 存储略缩图的map
 */
    HashMap<String, String> thumbnailList = new HashMap<>();

    /**
     * 一个存储存储集的map
     */
    HashMap<String, ImageBucket> bucketList = new HashMap<>();

    private static AlbumHelper albumHelper;

    private AlbumHelper(){}

//    单例模式
    public static AlbumHelper getHelper(){
        if(albumHelper == null){
            albumHelper = new AlbumHelper();
        }
        return albumHelper;
    }
    public void init(Context context){
        if(this.context == null){
            this.context = context;
            cr = context.getContentResolver();
        }
    }



    //    是否进行更新
    public void refreshornot(Boolean hasBuildImagesBucketList){
        if (hasBuildImagesBucketList) {
            bucketList.clear();
            buildImagesBucketList();
        }
    }


//  获取信息
    public ArrayList<ImageBucket> getImagesBucketList() {
//     List里面在存放一个list
        ArrayList<ImageBucket> tmpList = new ArrayList<>();
        tmpList.clear();
        Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet().iterator();
//      TODO
        while (itr.hasNext()) {
            Map.Entry<String, ImageBucket> entry =  itr.next();
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }


//    获取所有的图片的路径
    public ArrayList<ImageItem> getImageItemList(List<ImageBucket> list){
        ArrayList<ImageItem> dataItemList = new ArrayList<>();
        dataItemList.clear();
        for(int i = 0; i<list.size(); i++){
            dataItemList.addAll( list.get(i).imageList );
        }
        return dataItemList;
    }

    //    获取所有的图片的路径
    public ArrayList<String> getallImagepath(ArrayList<ImageItem> list){
        ArrayList<String> imagepath= new ArrayList<>();
        imagepath.clear();
        if(list.size()>0){
            for (int i=0;i<list.size();i++){
                imagepath.add(list.get(i).imagePath);
            }
            return imagepath;
        }else {
            return imagepath;
        }
    }

//   获取略缩图路径
    public ArrayList<String> getallthumbnailPath(ArrayList<ImageItem> list){
        ArrayList<String> thumbnailPath= new ArrayList<>();
        thumbnailPath.clear();
        if(list.size()>0){
            for (int i=0;i<list.size();i++){
                thumbnailPath.add(list.get(i).thumbnailPath);
            }
            return thumbnailPath;
        }else {
            return thumbnailPath;
        }
    }

//  获取的是图片的路径
    void buildImagesBucketList() {

//      获取略缩图
        getThumbnail();
//      获取实例
        String columns[] = new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.PICASA_ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null,"_id desc");
        if (cur.moveToFirst()) {
//            获取数据库字段
            int photoIDIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int photoNameIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int photoTitleIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
            int photoSizeIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
            int picasaIdIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.PICASA_ID);

            do {
                String _id = cur.getString(photoIDIndex);
                String name = cur.getString(photoNameIndex);
                String path = cur.getString(photoPathIndex);
                String title = cur.getString(photoTitleIndex);
                String size = cur.getString(photoSizeIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);
                String picasaId = cur.getString(picasaIdIndex);



                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<>();
//                    存放的是略缩图的路径
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                ImageItem imageItem = new ImageItem();
                imageItem.imageId = _id;
                imageItem.imagePath = path;
                imageItem.thumbnailPath = thumbnailList.get(_id);
                bucket.imageList.add(imageItem);

            } while (cur.moveToNext());
        }

        if(cur!=null){
            cur.close();
        }
    }






    /**
     * This class allows developers to query and get two kinds of thumbnails: MINI_KIND: 512 x 384 thumbnail MICRO_KIND: 96 x 96 thumbnail
     */
//  查询多张图片  略缩图
    public void getThumbnail(){
        String[] projection  = {MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};
//        读取的是略缩图的数据库
        Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        getThumbnailColumnData(cursor);
        if(cursor!=null){
            cursor.close();
        }
    }


    //  获取略缩图
    private void getThumbnailColumnData(Cursor cur){
        if(cur.moveToFirst()){
            int _id;
            int image_id;
            String image_path;
            int _idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int image_idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            do{
                _id = cur.getInt(_idColumn);
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);

                thumbnailList.put(""+image_id,image_path);
            }while (cur.moveToNext());
        }
        if(cur!=null){
            cur.close();
        }
    }

}
