package com.ljs.examinegoods.model;

import java.util.List;

public class ItemTypeModel {


    /**
     * Code : 1
     * Msg : OK
     * Data : [{"item_en_name":"","item_cn_name":"文件"},{"item_en_name":"","item_cn_name":"普通货物"},{"item_en_name":"","item_cn_name":"带电货物"},{"item_en_name":"","item_cn_name":"带磁货物"},{"item_en_name":"","item_cn_name":"带品牌货物"},{"item_en_name":"","item_cn_name":"纯电机"}]
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
         * item_en_name :
         * item_cn_name : 文件
         */

        private String item_en_name; //英文名称
        private String item_cn_name; //中文名称

        public String getItem_en_name() {
            return item_en_name;
        }

        public void setItem_en_name(String item_en_name) {
            this.item_en_name = item_en_name;
        }

        public String getItem_cn_name() {
            return item_cn_name;
        }

        public void setItem_cn_name(String item_cn_name) {
            this.item_cn_name = item_cn_name;
        }
    }
}
