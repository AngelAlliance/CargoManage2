package com.sz.ljs.warehousing.model;

import java.util.List;

/**
 * Created by liujs on 2018/8/21.
 * 生效得销售产品实体类
 */

public class ProductModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"product_cnname":"伊朗专线","product_enname":"IPS-IRAN","product_code":"PK1021"}]
     */

    private int Code;
    private String Msg;
    private List<DataEntity> Data;

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

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        /**
         * product_cnname : 伊朗专线
         * product_enname : IPS-IRAN
         * product_code : PK1021
         */

        private String product_cnname; //销售产品中文名称
        private String product_enname; //销售产品英文名称
        private String product_code;   //产品代码

        public String getProduct_cnname() {
            return product_cnname;
        }

        public void setProduct_cnname(String product_cnname) {
            this.product_cnname = product_cnname;
        }

        public String getProduct_enname() {
            return product_enname;
        }

        public void setProduct_enname(String product_enname) {
            this.product_enname = product_enname;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }
    }
}
