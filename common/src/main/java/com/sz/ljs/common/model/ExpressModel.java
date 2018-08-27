package com.sz.ljs.common.model;

//TODO 子快件模型（物流包下的小包）
public class ExpressModel {
    private String bs_id; //业务id
    private String shipper_hawbcode; //运单号码
    private String child_number; //子单号码
    private String server_id;  //服务id
    private String server_channelid;  //服务渠道id
    private String checkin_date; //签入时间
    private String shipper_pieces;  //件数
    private String outvolume_grossweight;  //重量
    private String outvolume_length;  //长
    private String outvolume_width; //宽
    private String outvolume_height;  //高
    private String balance_sign;   //欠费状态
    private String holding;  //扣件状态
    private String IsSelect;

    public ExpressModel(String bs_id, String shipper_hawbcode, String child_number, String server_id, String server_channelid, String checkin_date, String shipper_pieces, String outvolume_grossweight, String outvolume_length, String outvolume_width, String outvolume_height, String balance_sign, String holding, String isSelect) {
        this.bs_id = bs_id;
        this.shipper_hawbcode = shipper_hawbcode;
        this.child_number = child_number;
        this.server_id = server_id;
        this.server_channelid = server_channelid;
        this.checkin_date = checkin_date;
        this.shipper_pieces = shipper_pieces;
        this.outvolume_grossweight = outvolume_grossweight;
        this.outvolume_length = outvolume_length;
        this.outvolume_width = outvolume_width;
        this.outvolume_height = outvolume_height;
        this.balance_sign = balance_sign;
        this.holding = holding;
        IsSelect = isSelect;
    }

    public String getBs_id() {
        return bs_id;
    }

    public void setBs_id(String bs_id) {
        this.bs_id = bs_id;
    }

    public String getShipper_hawbcode() {
        return shipper_hawbcode;
    }

    public void setShipper_hawbcode(String shipper_hawbcode) {
        this.shipper_hawbcode = shipper_hawbcode;
    }

    public String getChild_number() {
        return child_number;
    }

    public void setChild_number(String child_number) {
        this.child_number = child_number;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getServer_channelid() {
        return server_channelid;
    }

    public void setServer_channelid(String server_channelid) {
        this.server_channelid = server_channelid;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getShipper_pieces() {
        return shipper_pieces;
    }

    public void setShipper_pieces(String shipper_pieces) {
        this.shipper_pieces = shipper_pieces;
    }

    public String getOutvolume_grossweight() {
        return outvolume_grossweight;
    }

    public void setOutvolume_grossweight(String outvolume_grossweight) {
        this.outvolume_grossweight = outvolume_grossweight;
    }

    public String getOutvolume_length() {
        return outvolume_length;
    }

    public void setOutvolume_length(String outvolume_length) {
        this.outvolume_length = outvolume_length;
    }

    public String getOutvolume_width() {
        return outvolume_width;
    }

    public void setOutvolume_width(String outvolume_width) {
        this.outvolume_width = outvolume_width;
    }

    public String getOutvolume_height() {
        return outvolume_height;
    }

    public void setOutvolume_height(String outvolume_height) {
        this.outvolume_height = outvolume_height;
    }

    public String getBalance_sign() {
        return balance_sign;
    }

    public void setBalance_sign(String balance_sign) {
        this.balance_sign = balance_sign;
    }

    public String getHolding() {
        return holding;
    }

    public void setHolding(String holding) {
        this.holding = holding;
    }

    public String getIsSelect() {
        return IsSelect;
    }

    public void setIsSelect(String isSelect) {
        IsSelect = isSelect;
    }
}
