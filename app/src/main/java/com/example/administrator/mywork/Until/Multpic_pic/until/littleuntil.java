package com.example.administrator.mywork.Until.Multpic_pic.until;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/19.
 */
public class littleuntil {
    public static void show(ArrayList o) {
        for (int i = 0; i < o.size(); i++) {
            System.out.println(o.get(i));
        }
    }

    public static String showstring(ArrayList o) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < o.size(); i++) {
            sb.append(o.get(i) + "\n");
        }
        return sb.toString();
    }

    //    查找是否相同的图片路径
    public static boolean checksamepath(ArrayList<String> pathlist, String path) {
        if (pathlist.size() > 0) {
            for (int i = 0; i < pathlist.size(); i++) {
                if (pathlist.get(i).equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }
}
