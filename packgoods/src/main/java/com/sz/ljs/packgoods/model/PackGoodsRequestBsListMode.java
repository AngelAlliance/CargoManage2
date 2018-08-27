package com.sz.ljs.packgoods.model;

//TODO 运单打包接口中运单实体类(选择多个运单然后以集合形式提交服务器进行打包)
public class PackGoodsRequestBsListMode {

    private String id; //id  查询数据中返回
    private String bs_id; //业务id
    private String shipper_hawbcode; //运单号码
    private String serve_hawbcode; //服务商单号
    private String child_number; //子单号码
    private String server_id; //服务id
    private String server_channelid; //渠道id
    private String formal_code; //渠道code
    private String server_channel_cnname; //渠道中文名字
    private String invoice_totalcharge; //申报金额
    private String cargo_type_cnname; //货物类型
    private String last_comment; //最新的备注信息
    private String checkin_date; //签入时间
    private String shipper_pieces; //件数
    private String outvolume_grossweight; //实重
    private String outvolume_volumeweight; //材积重
    private String outvolume_chargeweight; //实重
    private String outvolume_length; //长
    private String outvolume_width; //宽
    private String outvolume_height; //高
    private String balance_sign; //欠费状态
    private String holding; //扣件状态

    public PackGoodsRequestBsListMode() {

    }

    public PackGoodsRequestBsListMode(String id, String bs_id, String shipper_hawbcode, String serve_hawbcode, String child_number, String server_id, String server_channelid, String formal_code, String server_channel_cnname, String invoice_totalcharge, String cargo_type_cnname, String last_comment, String checkin_date, String shipper_pieces, String outvolume_grossweight, String outvolume_volumeweight, String outvolume_chargeweight, String outvolume_length, String outvolume_width, String outvolume_height, String balance_sign, String holding) {
        this.id = id;
        this.bs_id = bs_id;
        this.shipper_hawbcode = shipper_hawbcode;
        this.serve_hawbcode = serve_hawbcode;
        this.child_number = child_number;
        this.server_id = server_id;
        this.server_channelid = server_channelid;
        this.formal_code = formal_code;
        this.server_channel_cnname = server_channel_cnname;
        this.invoice_totalcharge = invoice_totalcharge;
        this.cargo_type_cnname = cargo_type_cnname;
        this.last_comment = last_comment;
        this.checkin_date = checkin_date;
        this.shipper_pieces = shipper_pieces;
        this.outvolume_grossweight = outvolume_grossweight;
        this.outvolume_volumeweight = outvolume_volumeweight;
        this.outvolume_chargeweight = outvolume_chargeweight;
        this.outvolume_length = outvolume_length;
        this.outvolume_width = outvolume_width;
        this.outvolume_height = outvolume_height;
        this.balance_sign = balance_sign;
        this.holding = holding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getServe_hawbcode() {
        return serve_hawbcode;
    }

    public void setServe_hawbcode(String serve_hawbcode) {
        this.serve_hawbcode = serve_hawbcode;
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

    public String getFormal_code() {
        return formal_code;
    }

    public void setFormal_code(String formal_code) {
        this.formal_code = formal_code;
    }

    public String getServer_channel_cnname() {
        return server_channel_cnname;
    }

    public void setServer_channel_cnname(String server_channel_cnname) {
        this.server_channel_cnname = server_channel_cnname;
    }

    public String getInvoice_totalcharge() {
        return invoice_totalcharge;
    }

    public void setInvoice_totalcharge(String invoice_totalcharge) {
        this.invoice_totalcharge = invoice_totalcharge;
    }

    public String getCargo_type_cnname() {
        return cargo_type_cnname;
    }

    public void setCargo_type_cnname(String cargo_type_cnname) {
        this.cargo_type_cnname = cargo_type_cnname;
    }

    public String getLast_comment() {
        return last_comment;
    }

    public void setLast_comment(String last_comment) {
        this.last_comment = last_comment;
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

    public String getOutvolume_volumeweight() {
        return outvolume_volumeweight;
    }

    public void setOutvolume_volumeweight(String outvolume_volumeweight) {
        this.outvolume_volumeweight = outvolume_volumeweight;
    }

    public String getOutvolume_chargeweight() {
        return outvolume_chargeweight;
    }

    public void setOutvolume_chargeweight(String outvolume_chargeweight) {
        this.outvolume_chargeweight = outvolume_chargeweight;
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
}
