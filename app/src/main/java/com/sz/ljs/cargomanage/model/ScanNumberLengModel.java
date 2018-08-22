package com.sz.ljs.cargomanage.model;


//TODO 返回输入运单N位得时候调用接口返回实体类
public class ScanNumberLengModel {


    /**
     * Code : 1
     * Msg : OK
     * Data : 9
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
