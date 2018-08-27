package com.sz.ljs.common.model;

import java.util.ArrayList;
import java.util.List;

//TODO 物流包模型
public class ExpressPackageModel {
    private String bag_pieces;  //件数
    private String number_pieces;//运单数量
    private String bag_weight;//包的重量
    private String weighing;//称重的重量
    private String bag_lable_code;//包号码
    private String IsSelect;
    private String length;//包的长
    private String width;//包的宽
    private String height;//包的高
    private List<ExpressModel> cn_list;

    public ExpressPackageModel(String bag_pieces, String number_pieces, String bag_weight, String weighing, String bag_lable_code, String isSelect, String length, String width, String height, List<ExpressModel> cn_list) {
        this.bag_pieces = bag_pieces;
        this.number_pieces = number_pieces;
        this.bag_weight = bag_weight;
        this.weighing = weighing;
        this.bag_lable_code = bag_lable_code;
        IsSelect = isSelect;
        this.length = length;
        this.width = width;
        this.height = height;
        this.cn_list = cn_list;
    }

    public String getBag_pieces() {
        return bag_pieces;
    }

    public void setBag_pieces(String bag_pieces) {
        this.bag_pieces = bag_pieces;
    }

    public String getNumber_pieces() {
        return number_pieces;
    }

    public void setNumber_pieces(String number_pieces) {
        this.number_pieces = number_pieces;
    }

    public String getBag_weight() {
        return bag_weight;
    }

    public void setBag_weight(String bag_weight) {
        this.bag_weight = bag_weight;
    }

    public String getWeighing() {
        return weighing;
    }

    public void setWeighing(String weighing) {
        this.weighing = weighing;
    }

    public String getBag_lable_code() {
        return bag_lable_code;
    }

    public void setBag_lable_code(String bag_lable_code) {
        this.bag_lable_code = bag_lable_code;
    }

    public String getIsSelect() {
        return IsSelect;
    }

    public void setIsSelect(String isSelect) {
        IsSelect = isSelect;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<ExpressModel> getCn_list() {
        return cn_list;
    }

    public void setCn_list(List<ExpressModel> cn_list) {
        this.cn_list = cn_list;
    }
}
