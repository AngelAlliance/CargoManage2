package com.sz.ljs.warehousing.model;

/**
 * 子单实体类
 */
public class SubnitModel {

    private String subnitNum;//子单号
    private String subnitWeight;//子单实重
    private String subnitLength;//子单长度
    private String subnitHeight;//子单高度
    private String subnitWeidth;//子单宽度
    private String subnitWolume;//子单体积
    private boolean isSubmit=false;//子单是否已提交服务器

    public String getSubnitNum() {
        return subnitNum;
    }

    public void setSubnitNum(String subnitNum) {
        this.subnitNum = subnitNum;
    }

    public String getSubnitWeight() {
        return subnitWeight;
    }

    public void setSubnitWeight(String subnitWeight) {
        this.subnitWeight = subnitWeight;
    }

    public String getSubnitLength() {
        return subnitLength;
    }

    public void setSubnitLength(String subnitLength) {
        this.subnitLength = subnitLength;
    }

    public String getSubnitHeight() {
        return subnitHeight;
    }

    public void setSubnitHeight(String subnitHeight) {
        this.subnitHeight = subnitHeight;
    }

    public String getSubnitWeidth() {
        return subnitWeidth;
    }

    public void setSubnitWeidth(String subnitWeidth) {
        this.subnitWeidth = subnitWeidth;
    }

    public String getSubnitWolume() {
        return subnitWolume;
    }

    public void setSubnitWolume(String subnitWolume) {
        this.subnitWolume = subnitWolume;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

}
