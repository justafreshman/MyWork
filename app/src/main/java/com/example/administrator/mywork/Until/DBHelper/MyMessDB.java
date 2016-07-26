package com.example.administrator.mywork.Until.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：wu
 */
public class MyMessDB extends SQLiteOpenHelper {
//    数据库名称
    private static final String DATABASE_NAME = "mymess.db";
//    数据库的版本
    private static final int DATABASE_VERSION = 1;
    public  static String ID = "_id";
//  MEDIA
    public  static String MEDIA_NAME = "NAME_Media";
    public  static String PATH = "path";
    public  static String OWNER_NOTE_ID = "owner_Note_Id";
//  NOTE
    public static String NOTE_NAME = "NAME_NOTE";
    public static String TITLE = "title";
    public static String CONTENT = "content";
    public static String SAVEDATE = "savedate";



    public static MyMessDB instance = null;

    public static synchronized  MyMessDB getIntance(Context context){
        if(instance == null){
            instance = new MyMessDB(context);
        }
        return instance;
    }

    private MyMessDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+MEDIA_NAME+"\n" +
                "("+ID+" integer primary key autoincrement,\n" +
                ""+PATH+" varchar not null default '',\n" +
                ""+OWNER_NOTE_ID+" integer,\n" +
                "foreign key("+OWNER_NOTE_ID+") references "+NOTE_NAME+"("+ID+")) ");
        db.execSQL("create table if not exists "+NOTE_NAME+"\n" +
                "("+ID+" integer primary key autoincrement,\n" +
                ""+TITLE+" text not null,\n" +
                ""+CONTENT+" text  not null,\n" +
                ""+SAVEDATE+" text \n" +
                ")");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
