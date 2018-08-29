package com.ljs.examinegoods.model;

import java.util.List;

public class DetectionByModel {


    /**
     * Code : 1
     * Msg : OK
     * Data : [{"item_cn_name":"文件","item_en_name":"","detection_cn_name":"是否带电","detection_en_name":"Whether it is powered","defult_value":"N","value":"N"},{"item_cn_name":"文件","item_en_name":"","detection_cn_name":"是否带磁","detection_en_name":"Whether with magnetic","defult_value":"N","value":"N"},{"item_cn_name":"文件","item_en_name":"","detection_cn_name":"是否带牌","detection_en_name":"Whether to bring a card","defult_value":"N","value":"N"},{"item_cn_name":"文件","item_en_name":"","detection_cn_name":"是否有随货发票","detection_en_name":"Is there an invoice with the goods","defult_value":"N","value":"Y"},{"item_cn_name":"文件","item_en_name":"","detection_cn_name":"发票资料是否正确","detection_en_name":"Is the invoice information correct?","defult_value":"N","value":"Y"}]
     */

    private int Code;
    private String Msg;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * item_cn_name : 文件
         * item_en_name :
         * detection_cn_name : 是否带电
         * detection_en_name : Whether it is powered
         * defult_value : N
         * value : N
         */

        private String item_cn_name;  //货物类型中文名称
        private String item_en_name;  //货物类型英文名称
        private String detection_cn_name;  //检查项中文
        private String detection_en_name;  //检查项英文
        private String defult_value;    //默认项 Y：是 N:否
        private String value;           //检测结果项 Y：是 N:否D：不做限制
        private boolean isChaYi=false;
        private String select_value;//手动选择项(根据这个来显示选中哪个，默认和默认项一模一样)

        public String getItem_cn_name() {
            return item_cn_name;
        }

        public void setItem_cn_name(String item_cn_name) {
            this.item_cn_name = item_cn_name;
        }

        public String getItem_en_name() {
            return item_en_name;
        }

        public void setItem_en_name(String item_en_name) {
            this.item_en_name = item_en_name;
        }

        public String getDetection_cn_name() {
            return detection_cn_name;
        }

        public void setDetection_cn_name(String detection_cn_name) {
            this.detection_cn_name = detection_cn_name;
        }

        public String getDetection_en_name() {
            return detection_en_name;
        }

        public void setDetection_en_name(String detection_en_name) {
            this.detection_en_name = detection_en_name;
        }

        public String getDefult_value() {
            return defult_value;
        }

        public void setDefult_value(String defult_value) {
            this.defult_value = defult_value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isChaYi() {
            return isChaYi;
        }

        public void setChaYi(boolean chaYi) {
            isChaYi = chaYi;
        }

        public String getSelect_value() {
            return select_value;
        }

        public void setSelect_value(String select_value) {
            this.select_value = select_value;
        }
    }
}
