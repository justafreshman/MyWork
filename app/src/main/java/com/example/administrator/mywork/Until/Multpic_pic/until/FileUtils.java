package com.example.administrator.mywork.Until.Multpic_pic.until;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/12.
 */

//TODO
public class FileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory().toString();


    public  String saveBitmap(Bitmap bm, String picName) {
        File f = null;
        File dir = new File(SDPATH,"TEST_PHOTO12345");
        if(!dir.exists()&&!dir.isDirectory()){
            dir.mkdir();
        }
        try {
            f = new File(dir, picName + ".JPG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
//            压缩图片
            bm.compress(Bitmap.CompressFormat.JPEG, 0, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getPath();
    }

    //    创建文件
    public static File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    //          判断文件是否存在
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }


    //    删除文件
    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }


    // 删除文件夹
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }
}
