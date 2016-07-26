package com.example.administrator.mywork.bean;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：wu
 */
public class HttpResult<T> {
//     "error_code": 0,
//    "reason": "ok",
    private int error_code;
    private String reason;
    private String resultcode;
//    这是某些接口的
    private T result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
