package com.sz.ljs.warehousing.model;

/**
 * Created by liujs on 2018/8/21.
 * 入库时选择客户生成到货总单实体类
 */

public class SelectCurrentDayBatchModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"arrivalbatch_id":188964,"arrival_date":"2018-08-16 15:20:12","customer_id":19029,"customer_channelid":0,"arrivalbatch_labelcode":"JM6032-101SZX-1808160001"}
     */

    private int Code;
    private String Msg;
    private DataEntity Data;

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

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        /**
         * arrivalbatch_id : 188964
         * arrival_date : 2018-08-16 15:20:12
         * customer_id : 19029
         * customer_channelid : 0
         * arrivalbatch_labelcode : JM6032-101SZX-1808160001
         */

        private int arrivalbatch_id; //总单id
        private String arrival_date;  //查询时间
        private int customer_id;     //客户id
        private int customer_channelid;   //客户渠道
        private String arrivalbatch_labelcode;  //到货总单号码

        public int getArrivalbatch_id() {
            return arrivalbatch_id;
        }

        public void setArrivalbatch_id(int arrivalbatch_id) {
            this.arrivalbatch_id = arrivalbatch_id;
        }

        public String getArrival_date() {
            return arrival_date;
        }

        public void setArrival_date(String arrival_date) {
            this.arrival_date = arrival_date;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getCustomer_channelid() {
            return customer_channelid;
        }

        public void setCustomer_channelid(int customer_channelid) {
            this.customer_channelid = customer_channelid;
        }

        public String getArrivalbatch_labelcode() {
            return arrivalbatch_labelcode;
        }

        public void setArrivalbatch_labelcode(String arrivalbatch_labelcode) {
            this.arrivalbatch_labelcode = arrivalbatch_labelcode;
        }
    }
}
