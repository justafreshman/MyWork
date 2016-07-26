package com.example.administrator.mywork.bean;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 * 说明 这个类是身份证查询和身份证泄露的bean类
 */
public class identityMessEntity {
//   这个是查询身份证的
    /**
     * area : 浙江省温州市平阳县
     * sex : 男
     * birthday : 1989年03月08日
     */

    private String area;
    private String sex;
    private String birthday;
    /**
     * cardno : 330326198903081211
     * res : 1
     * tips : 安全
     */

//    这个是查询是否身份证泄露的
    private String cardno;
    private String res;
    private String tips;

    public void setArea(String area) {
        this.area = area;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea() {
        return area;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCardno() {
        return cardno;
    }

    public String getRes() {
        return res;
    }

    public String getTips() {
        return tips;
    }


//    "result":{
//        "cardno":"330326198903081211",
//                "res":"1",
//                "tips":"安全"
//    }

}
