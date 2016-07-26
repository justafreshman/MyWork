package com.example.administrator.mywork.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public class postalBycodeEntity extends BaseObservable {

    /**
     * list : [{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"廖家巷新光里"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"龙兴桥顺德里"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"渔郎桥浜"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"王洗马巷"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"宝城桥街"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"桃花桥四弄"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"白塔东路"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"周五郎巷"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"仓街122-180号(双号)"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"白塔西路1-51号(单号)"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"临顿路178-400号(双号)"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"桃花坞下塘"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"后张家弄"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"北码头"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"西蔡家桥"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"丁香巷东弄堂"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"宝城桥街宝城桥沿河"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"阊门内下塘街志仁里"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"杨院巷"},{"PostNumber":"215001","Province":"江苏省","City":"苏州市","District":"平江区","Address":"汤家巷敦仁里"}]
     * totalcount : 352
     * totalpage : 18
     * currentpage : 1
     * pagesize : 20
     */

    private int totalcount;
    private int totalpage;
    private int currentpage;
    private int pagesize;
    private List<ListEntity> list;


    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        /**
         * PostNumber : 215001
         * Province : 江苏省
         * City : 苏州市
         * District : 平江区
         * Address : 廖家巷新光里
         */

        private String PostNumber;
        private String Province;
        private String City;
        private String District;
        private String Address;

        public void setPostNumber(String PostNumber) {
            this.PostNumber = PostNumber;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public void setDistrict(String District) {
            this.District = District;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPostNumber() {
            return PostNumber;
        }

        public String getProvince() {
            return Province;
        }

        public String getCity() {
            return City;
        }

        public String getDistrict() {
            return District;
        }

        public String getAddress() {
            return Address;
        }
    }
}
