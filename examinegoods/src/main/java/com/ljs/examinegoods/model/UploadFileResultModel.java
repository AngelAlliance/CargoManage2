package com.ljs.examinegoods.model;

/**
 * Created by liujs on 2018/8/21.
 */

public class UploadFileResultModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : http:www.baidu.com
     */

    private int Code;
    private String Msg;
    private String Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
