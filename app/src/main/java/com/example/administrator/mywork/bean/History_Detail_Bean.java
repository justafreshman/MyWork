package com.example.administrator.mywork.bean;

import java.util.List; /**
 * Created by Administrator on 2016/7/17.
 * 作者：wu
 */
public class History_Detail_Bean {

    /**
     * e_id : 1
     * title : 罗马共和国开始使用儒略历
     * content :     在2061年前的今天，前45年1月1日 (农历冬月十九)，罗马共和国开始使用儒略历。
     儒略
     * picNo : 5
     * picUrl : [{"pic_title":"儒略历","id":1,"url":"http://images.juheapi.com/history/1_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/1_2.jpg"},{"pic_title":"","id":3,"url":"http://images.juheapi.com/history/1_3.jpg"},{"pic_title":"公式中的符号含义如下：","id":4,"url":"http://images.juheapi.com/history/1_4.jpg"},{"pic_title":"","id":5,"url":"http://images.juheapi.com/history/1_5.jpg"}]
     */

    private String e_id;
    private String title;
    private String content;
    private String picNo;
    /**
     * pic_title : 儒略历
     * id : 1
     * url : http://images.juheapi.com/history/1_1.jpg
     */

    private List<PicUrlEntity> picUrl;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicNo() {
        return picNo;
    }

    public void setPicNo(String picNo) {
        this.picNo = picNo;
    }

    public List<PicUrlEntity> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<PicUrlEntity> picUrl) {
        this.picUrl = picUrl;
    }

    public static class PicUrlEntity {
        private String pic_title;
        private int id;
        private String url;

        public String getPic_title() {
            return pic_title;
        }

        public void setPic_title(String pic_title) {
            this.pic_title = pic_title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
