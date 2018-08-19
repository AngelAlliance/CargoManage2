package com.sz.ljs.warehousing.model;


import java.util.List;

/**
 * 后台请求回来的杂费项实体类
 */
public class GsonIncidentalModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"extra_service_kind":"A1","extra_service_cnname":"包装费","extra_service_enname":"包装费"}]
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
         * extra_service_kind : A1
         * extra_service_cnname : 包装费
         * extra_service_enname : 包装费
         */

        private String extra_service_kind;
        private String extra_service_cnname;
        private String extra_service_enname;

        public String getExtra_service_kind() {
            return extra_service_kind;
        }

        public void setExtra_service_kind(String extra_service_kind) {
            this.extra_service_kind = extra_service_kind;
        }

        public String getExtra_service_cnname() {
            return extra_service_cnname;
        }

        public void setExtra_service_cnname(String extra_service_cnname) {
            this.extra_service_cnname = extra_service_cnname;
        }

        public String getExtra_service_enname() {
            return extra_service_enname;
        }

        public void setExtra_service_enname(String extra_service_enname) {
            this.extra_service_enname = extra_service_enname;
        }
    }
}
