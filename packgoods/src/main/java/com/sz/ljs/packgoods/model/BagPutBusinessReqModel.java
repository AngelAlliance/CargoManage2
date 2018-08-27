package com.sz.ljs.packgoods.model;

/**
 * Created by liujs on 2018/8/27.
 * 把运单从某个包提出 其中一个单的实体列
 */

public class BagPutBusinessReqModel {
    private String bs_id;  //业务id
    private String hawbcode;//运单号码
    private String hawbcode_mode;//运单类型 “C”
    private String scan_date;//扫描时间
    private String bag_id;//包id
    private String bag_labelcode;//包号码 PPNO-

    public BagPutBusinessReqModel(String bs_id, String hawbcode, String hawbcode_mode, String scan_date, String bag_id, String bag_labelcode) {
        this.bs_id = bs_id;
        this.hawbcode = hawbcode;
        this.hawbcode_mode = hawbcode_mode;
        this.scan_date = scan_date;
        this.bag_id = bag_id;
        this.bag_labelcode = bag_labelcode;
    }

    public String getBs_id() {
        return bs_id;
    }

    public void setBs_id(String bs_id) {
        this.bs_id = bs_id;
    }

    public String getHawbcode() {
        return hawbcode;
    }

    public void setHawbcode(String hawbcode) {
        this.hawbcode = hawbcode;
    }

    public String getHawbcode_mode() {
        return hawbcode_mode;
    }

    public void setHawbcode_mode(String hawbcode_mode) {
        this.hawbcode_mode = hawbcode_mode;
    }

    public String getScan_date() {
        return scan_date;
    }

    public void setScan_date(String scan_date) {
        this.scan_date = scan_date;
    }

    public String getBag_id() {
        return bag_id;
    }

    public void setBag_id(String bag_id) {
        this.bag_id = bag_id;
    }

    public String getBag_labelcode() {
        return bag_labelcode;
    }

    public void setBag_labelcode(String bag_labelcode) {
        this.bag_labelcode = bag_labelcode;
    }

}
