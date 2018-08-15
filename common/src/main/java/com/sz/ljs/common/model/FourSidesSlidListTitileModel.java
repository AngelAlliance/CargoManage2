package com.sz.ljs.common.model;


//TODO 双向移动List头部菜单实体类
public class FourSidesSlidListTitileModel {

    private String Checked ;//勾选
    private String packageNumber;//包编号
    private String transitState;//中转状态
    private String waybillNumber;//运单号
    private String bulletsBarcode;//子单条码
    private String number;//件数
    private String weight;//重量
    private String volume;//长宽高

    public FourSidesSlidListTitileModel(String Checked,String packageNumber,String transitState,String waybillNumber
                                        ,String bulletsBarcode,String number,String weight,String volume){
        this.Checked=Checked;
        this.packageNumber=packageNumber;
        this.transitState=transitState;
        this.waybillNumber=waybillNumber;
        this.bulletsBarcode=bulletsBarcode;
        this.number=number;
        this.weight=weight;
        this.volume=volume;
    }
    public String getChecked() {
        return Checked;
    }

    public void setChecked(String checked) {
        Checked = checked;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getTransitState() {
        return transitState;
    }

    public void setTransitState(String transitState) {
        this.transitState = transitState;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getBulletsBarcode() {
        return bulletsBarcode;
    }

    public void setBulletsBarcode(String bulletsBarcode) {
        this.bulletsBarcode = bulletsBarcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


}
