package com.example.administrator.mywork.Until.Multpic_pic.until;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：wu
 */
public class compareTime {


    public static Date getnowDate(){
        Date date = new Date();
        return date;
    }

    public static Date recdate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date recetime = null;
        try {
            recetime = sdf.parse(time);
        } catch (ParseException e) {

        }
        return recetime;
    }
    /**
     * @param date1   当前时间
     * @param date2   接收过来的时间
     * @return 文字
     */
    public static String comparetime(Date date1,Date date2){
//        当前日期
        int frist =  date1.getMonth();
//         发过来的日期
        int second = date2.getMonth();

        Log.d("testhour",date1.toString()+"事件"+date2.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        StringBuffer sb = new StringBuffer();

//       是否是当前月份
        int month = Math.abs(date1.getMonth()-date2.getMonth());
        if(month>1){
            Log.d("test",frist+" "+second);
            sb.append(sdf.format(date2));
        }else {
//            是否是昨天的
            int yesterday = Math.abs(date1.getDate()-date2.getDate());
            if(yesterday>=1){
                if(yesterday==1){
                    sb.append("昨天");
                }else {
                    sb.append(sdf.format(date2));
                }
            }else {
//                是否是这一个小时里
                int recelyhour = Math.abs(date1.getHours()-date2.getHours());
                Log.d("testhour",recelyhour+"事件");
                if(recelyhour>=1){
                    Log.d("testhour",recelyhour+"事件");
                    sb.append(recelyhour + "小时前");
                }else if(recelyhour<1){
                    int recelymin = Math.abs(date1.getMinutes() - date2.getMinutes());
                    if(recelymin<=5){
                        sb.append("刚刚");
                    }else {
                        sb.append(recelymin+"分钟前");
                    }
                }
            }
        }
        return sb.toString();
    }
}
