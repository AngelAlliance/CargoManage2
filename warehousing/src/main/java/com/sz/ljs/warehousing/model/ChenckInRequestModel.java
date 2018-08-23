package com.sz.ljs.warehousing.model;

public class ChenckInRequestModel {

    private String order_id;   //订单id
    private String shipper_number;//运单号
    private String reference_number;//客户参考单号
    private String customer_id;//客户id
    private String pk_code;//产品代码
    private String country_code;//国家代码
    private String arrivalbatch_id;//到货总单id
    private String arrival_date;//到货时间
    private String customer_channelid;//客户渠道id
    private String checkin_og_id;//操作人机构id
    private String shipper_weight;//客户预报重
    private String shipper_pieces;//
    private String Len;//长
    private String Height;//高
    private String Width;//宽
    private String User_Name;//用户名称
    private String OG_CityEnName;//机构英文名字（SHENZHEN、GUANGZHOU 登陆成功有返回）
    private String CargoVolumes;//重量集合
    private String m_lstExtraService;//附加服务集合

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getShipper_number() {
        return shipper_number;
    }

    public void setShipper_number(String shipper_number) {
        this.shipper_number = shipper_number;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPk_code() {
        return pk_code;
    }

    public void setPk_code(String pk_code) {
        this.pk_code = pk_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getArrivalbatch_id() {
        return arrivalbatch_id;
    }

    public void setArrivalbatch_id(String arrivalbatch_id) {
        this.arrivalbatch_id = arrivalbatch_id;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getCustomer_channelid() {
        return customer_channelid;
    }

    public void setCustomer_channelid(String customer_channelid) {
        this.customer_channelid = customer_channelid;
    }

    public String getCheckin_og_id() {
        return checkin_og_id;
    }

    public void setCheckin_og_id(String checkin_og_id) {
        this.checkin_og_id = checkin_og_id;
    }

    public String getShipper_weight() {
        return shipper_weight;
    }

    public void setShipper_weight(String shipper_weight) {
        this.shipper_weight = shipper_weight;
    }

    public String getShipper_pieces() {
        return shipper_pieces;
    }

    public void setShipper_pieces(String shipper_pieces) {
        this.shipper_pieces = shipper_pieces;
    }

    public String getLen() {
        return Len;
    }

    public void setLen(String len) {
        Len = len;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getOG_CityEnName() {
        return OG_CityEnName;
    }

    public void setOG_CityEnName(String OG_CityEnName) {
        this.OG_CityEnName = OG_CityEnName;
    }

    public String getCargoVolumes() {
        return CargoVolumes;
    }

    public void setCargoVolumes(String cargoVolumes) {
        CargoVolumes = cargoVolumes;
    }

    public String getM_lstExtraService() {
        return m_lstExtraService;
    }

    public void setM_lstExtraService(String m_lstExtraService) {
        this.m_lstExtraService = m_lstExtraService;
    }


}
