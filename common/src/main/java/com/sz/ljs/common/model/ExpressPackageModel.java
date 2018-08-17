package com.sz.ljs.common.model;

import java.util.ArrayList;
import java.util.List;

//TODO 物流包模型
public class ExpressPackageModel {

    private boolean isChecked=false;//是否勾选状态
    private boolean isOpen=false;//是否展开
    private String packageNumber;//包编号
    private int number;//件数
    private int weight;//重量
    private int length;//长度
    private int width;//宽度
    private int height;//高度
    private List<ExpressModel> expressModels=new ArrayList<ExpressModel>();

    public ExpressPackageModel(boolean isChecked,boolean isOpen,String packageNumber,int number
                                ,int weight,int length,int width,int height,List<ExpressModel> expressModels){
        this.isChecked=isChecked;
        this.isOpen=isOpen;
        this.packageNumber=packageNumber;
        this.number=number;
        this.weight=weight;
        this.length=length;
        this.width=width;
        this.height=height;
        this.expressModels=expressModels;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<ExpressModel> getExpressModels() {
        return expressModels;
    }

    public void setExpressModels(List<ExpressModel> expressModels) {
        this.expressModels = expressModels;
    }


}
