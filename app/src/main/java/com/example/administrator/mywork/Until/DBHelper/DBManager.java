package com.example.administrator.mywork.Until.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.mywork.APP;
import com.example.administrator.mywork.Until.ToastUntil;

/**
 * Created by Administrator on 2016/7/25.
 * 作者：wu
 */
public class DBManager {
    private MyMessDB mydb;
    private SQLiteDatabase dbread,dbwrite;

    public DBManager(Context context){
        mydb = MyMessDB.getIntance(context);
        dbread = mydb.getReadableDatabase();
        dbwrite = mydb.getWritableDatabase();
    }


//    保存文本
    public int addNote(String title,String content,String date){
        ContentValues cv = new ContentValues();
        cv.put(MyMessDB.TITLE,title);
        cv.put(MyMessDB.CONTENT,content);
        cv.put(MyMessDB.SAVEDATE,date);
        return  (int)dbwrite.insert(MyMessDB.NOTE_NAME,null,cv);
    }

//    保存图片
    public int savemedia(int noteId,String path){
        ContentValues cv = new ContentValues();
        cv.put(MyMessDB.OWNER_NOTE_ID,noteId);
        cv.put(MyMessDB.PATH,path);
        return (int)dbwrite.insert(MyMessDB.MEDIA_NAME,null,cv);
    }

//   更新
    public int  updateNoteAndMedia(String noteId,String Title,String content,String date){
        ContentValues cv = new ContentValues();
        cv.put(MyMessDB.CONTENT,content);
        cv.put(MyMessDB.TITLE,Title);
        cv.put(MyMessDB.SAVEDATE,date);
        return dbwrite.update(MyMessDB.NOTE_NAME,cv,MyMessDB.ID + "=?",new String[]{noteId + ""});
    }


//    查询
    public Cursor query(){
        Cursor cursor = dbread.query(MyMessDB.NOTE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public Cursor querypic(String noteId){
        Cursor cursor = dbread.query(MyMessDB.MEDIA_NAME,null,MyMessDB.OWNER_NOTE_ID+"=?",new String[]{noteId},null,null,null);
//        Cursor cursor = dbread.query(MyMessDB.MEDIA_NAME,null,null,null,null,null,null);
        return cursor;
    }


    public int delete(String noteId){
        int i = dbwrite.delete(MyMessDB.MEDIA_NAME,MyMessDB.OWNER_NOTE_ID+"=?",new String[]{noteId});
        if(i>=0){
            return dbwrite.delete(MyMessDB.NOTE_NAME,MyMessDB.ID+"=?",new String[]{noteId});
        }else {
            return -1;
        }
    }



    public void destory(){
        dbwrite.close();
        dbread.close();
        mydb.close();
    }
}
