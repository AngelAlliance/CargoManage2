package com.sz.ljs.shipments.model;

import java.util.List;

public class GsonServiceChannelModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"server_channelid":1185,"server_id":591,"formal_code":"IPS-CHINA","server_product_code":null,"server_channel_cnname":"IPS-大陆","server_channel_enname":"IPS-CHINA"},{"server_channelid":1186,"server_id":592,"formal_code":"IPS-HK","server_product_code":null,"server_channel_cnname":"IPS-香港","server_channel_enname":"IPS-HK"}]
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
         * server_channelid : 1185
         * server_id : 591
         * formal_code : IPS-CHINA
         * server_product_code : null
         * server_channel_cnname : IPS-大陆
         * server_channel_enname : IPS-CHINA
         */

        private int server_channelid;   //渠道id
        private int server_id;        //服务id
        private String formal_code;   //渠道代码
        private Object server_product_code;  //没用参数
        private String server_channel_cnname;  //渠道中文名字
        private String server_channel_enname;  //渠道英文名字

        public int getServer_channelid() {
            return server_channelid;
        }

        public void setServer_channelid(int server_channelid) {
            this.server_channelid = server_channelid;
        }

        public int getServer_id() {
            return server_id;
        }

        public void setServer_id(int server_id) {
            this.server_id = server_id;
        }

        public String getFormal_code() {
            return formal_code;
        }

        public void setFormal_code(String formal_code) {
            this.formal_code = formal_code;
        }

        public Object getServer_product_code() {
            return server_product_code;
        }

        public void setServer_product_code(Object server_product_code) {
            this.server_product_code = server_product_code;
        }

        public String getServer_channel_cnname() {
            return server_channel_cnname;
        }

        public void setServer_channel_cnname(String server_channel_cnname) {
            this.server_channel_cnname = server_channel_cnname;
        }

        public String getServer_channel_enname() {
            return server_channel_enname;
        }

        public void setServer_channel_enname(String server_channel_enname) {
            this.server_channel_enname = server_channel_enname;
        }
    }
}
