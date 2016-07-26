package com.example.administrator.mywork.bean;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class phoneEntity {
    /**
     "province":"浙江",
     "city":"杭州",
     "areacode":"0571",
     "zip":"310000",
     "company":"中国移动",
     "card":"移动动感地带卡"
     */
        private String province;
        private String city;
        private String areacode;
        private String zip;
        private String company;
        private String card;

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getAreacode() {
            return areacode;
        }

        public String getZip() {
            return zip;
        }

        public String getCompany() {
            return company;
        }

        public String getCard() {
            return card;
        }
}
