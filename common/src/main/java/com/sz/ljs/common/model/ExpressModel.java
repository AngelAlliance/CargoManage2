package com.sz.ljs.common.model;

//TODO 子快件模型（物流包下的小包）
public class ExpressModel {

    private boolean isChecked = false;//是否被勾选
    private String packageNumber;//包编号
    private int waybillNumber;//运单号
    private int bulletsBarcode;//子单条码
    private int number;//件数
    private int weight;//重量
    private int length;//长度
    private int width;//宽度
    private int height;//高度
    private String transitState;//中转状态

    public ExpressModel(boolean isChecked, String packageNumber, String transitState, int waybillNumber, int bulletsBarcode
            , int number, int weight, int length, int width, int height) {
        this.isChecked = isChecked;
        this.packageNumber = packageNumber;
        this.transitState = transitState;
        this.waybillNumber = waybillNumber;
        this.bulletsBarcode = bulletsBarcode;
        this.number = number;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(int waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public int getBulletsBarcode() {
        return bulletsBarcode;
    }

    public void setBulletsBarcode(int bulletsBarcode) {
        this.bulletsBarcode = bulletsBarcode;
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

    public String getTransitState() {
        return transitState;
    }

    public void setTransitState(String transitState) {
        this.transitState = transitState;
    }


}
