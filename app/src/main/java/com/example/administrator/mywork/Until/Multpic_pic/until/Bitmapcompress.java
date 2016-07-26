package com.example.administrator.mywork.Until.Multpic_pic.until;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Created by Administrator on 2016/4/30.
 * 作者：wu
 */
public class Bitmapcompress {
//    内存的大小
    int MaxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
    int FreeMemory = (int) (Runtime.getRuntime().freeMemory()/1024);
    int TotalMemory = (int) (Runtime.getRuntime().totalMemory()/1024);
    public void show(){
        Log.d("maxmemory",(MaxMemory/1024)+"M");
        Log.d("maxmemory",(FreeMemory/1024)+"M");
        Log.d("maxmemory",(TotalMemory/1024)+"M");
    }



    public static String revitionImageSize(String path) {
//        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
//                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
//        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // int f = (1.2 * i) > 0 ? (int)(1.2 * i) : 1;
            // int f = (int) (i<1?1:i);
            if ((options.outWidth >> i <= 1024)
                    && (options.outHeight >> i <= 1024)) {
//                in = new BufferedInputStream(
//                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap =  BitmapFactory.decodeFile(path,options);
                break;
            }
            i += 1;
        }
        ByteArrayOutputStream bos = null;
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        String data= Base64.encode(bos.toByteArray());
        Log.d("Timeqwe",new Date()+"   "+path);
        return data;
    }












    public static byte[] minpic(String path, int reqWidth, int reqHeight){
        return compressBitmap(decodeSampledBitmapFromResource(path,reqWidth,reqHeight));
    }


    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     * 通过设置BitmapFactory.Options中inSampleSize的值就可以实现。
     * 比如我们有一张2048*1536像素的图片，
     * 将inSampleSize的值设置为4，就可以把这张图片压缩成512*384像素。
     * 原本加载这张图片需要占用13M的内存，
     * 压缩后就只需要占用0.75M了(假设图片是ARGB_8888类型，即每个像素点占用4个字节)。
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 调用上面定义的方法计算inSampleSize值
//        BitmapFactory.decodeResource(res, resId, options);
//        这里是根据路径来获取图片65
        BitmapFactory.decodeFile(path,options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值  真正解析图片
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        float scale;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        if (originalHeight > originalWidth) {
            scale = ((float) reqHeight) / ((float) originalHeight);
        } else {
            scale = ((float) reqWidth) / ((float) originalWidth);
        }
        float scaledWidth = scale;
        float scaledHeight = scale;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.postScale(scaledWidth, scaledHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, scaleMatrix, true);
    }


    public static byte[] compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream bos = null;
        try {
            String saveDir = Environment.getExternalStorageDirectory() + "";
            File file = new File(saveDir+"/uiop"+bitmap.getWidth()+".jpeg");
            bos = new ByteArrayOutputStream(1000);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//          如果不大于120kb不压缩
            if(bitmap.getByteCount()/1024<120){
                return bos.toByteArray();
            }
            int intoptions =95;
            while((bos.toByteArray().length/1024)>120){
                bos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, intoptions, bos);
                intoptions-=5;
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bos.toByteArray(),0,bos.toByteArray().length);
            fos.flush();
                if(bos!=null){
                    bos.close();
                }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
    /**
     * 压缩图片到指定位置(默认JPG格式)
     *
     * @param bitmap       需要压缩的图片
     * @param compressPath 生成文件路径(例如: /storage/imageCache/1.jpg)
     * @param quality      图片质量，0~100
     * @return if true,保存成功
     */
    public static boolean compressBiamp(Bitmap bitmap, String compressPath, int quality) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(compressPath));

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);// (0-100)压缩文件

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
