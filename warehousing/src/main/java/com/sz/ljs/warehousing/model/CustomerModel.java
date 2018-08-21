package com.sz.ljs.warehousing.model;

import java.util.List;

/**
 * Created by liujs on 2018/8/21.
 * 根据客户代码或者客户名称查询客户资料实体类
 */

public class CustomerModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"customer_id":19029,"customer_code":"JM6032","customer_shortname":"bamilo"}]
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
         * customer_id : 19029
         * customer_code : JM6032
         * customer_shortname : bamilo
         */

        private int customer_id;    //客户id
        private String customer_code;      //客户代码
        private String customer_shortname;  //客户名称

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getCustomer_shortname() {
            return customer_shortname;
        }

        public void setCustomer_shortname(String customer_shortname) {
            this.customer_shortname = customer_shortname;
        }
    }
}
