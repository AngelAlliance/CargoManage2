package com.ljs.examinegoods.model;

import java.util.List;

//TODO 添加问题件或者保存验货结果请求参数实体类
public class SaveDeteTionOrderRequestModel {

    private String number;   //运单号码
    private String reference_number;  //客户参考单号
    private String request_type;     //问题件还是不是问题件 Y问题件N不是问题件
    private String detection_note;  // 	检测结果 如（带电，没有发票……….）
    private List<String> image_url; //图片集合 LISt<string>
    private String order_id;       //订单id
    private String userId;         //用户id
    private String summary;       //zhbg_ips2018_cn得MD532位小写加密

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getDetection_note() {
        return detection_note;
    }

    public void setDetection_note(String detection_note) {
        this.detection_note = detection_note;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



}
