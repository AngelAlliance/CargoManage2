package com.sz.ljs.common.model;

import java.util.List;

/**
 * Created by liujs on 2018/8/20.
 * 订单详情数据模型
 */

public class OrderModel {


    /**
     * Code : 1
     * Msg : OK
     * Data : {"order_id":"337","shipper_hawbcode":"300802681","server_hawbcode":"300802681","channel_hawbcode":"","customer_id":"19162","customer_code":"JM6075","customer_shortname":"IPS","customer_channelid":"","customer_channelcode":"","customer_reference_number":"","product_code":"PK1021","product_cnname":"伊朗专线","country_code":"IR","consignee_postcode":"","consignee_city":"","consignee_address":"","order_weight":"1.000","order_pieces":"1","api_post_date":"2018-07-17 16:10:58","order_status":"P","order_status_cnname":"已预报","order_status_enname":"Post","order_info":"文件","mergebox_id":"","mergebox_count":0,"mergebox_arrivalno":"","mergebox_order":"","extraservice":[{"order_id":337,"extra_servicecode":"A7","extra_service_cnname":"到付手续费","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""},{"order_id":337,"extra_servicecode":"B6","extra_service_cnname":"单独报关费","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""},{"order_id":337,"extra_servicecode":"C7","extra_service_cnname":"关税预付","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""}],"smt_customer":"N","cargo_type":"W","server_channelid":""}
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
         * order_id : 337
         * shipper_hawbcode : 300802681
         * server_hawbcode : 300802681
         * channel_hawbcode :
         * customer_id : 19162
         * customer_code : JM6075
         * customer_shortname : IPS
         * customer_channelid :
         * customer_channelcode :
         * customer_reference_number :
         * product_code : PK1021
         * product_cnname : 伊朗专线
         * country_code : IR
         * consignee_postcode :
         * consignee_city :
         * consignee_address :
         * order_weight : 1.000
         * order_pieces : 1
         * api_post_date : 2018-07-17 16:10:58
         * order_status : P
         * order_status_cnname : 已预报
         * order_status_enname : Post
         * order_info : 文件
         * mergebox_id :
         * mergebox_count : 0
         * mergebox_arrivalno :
         * mergebox_order :
         * extraservice : [{"order_id":337,"extra_servicecode":"A7","extra_service_cnname":"到付手续费","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""},{"order_id":337,"extra_servicecode":"B6","extra_service_cnname":"单独报关费","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""},{"order_id":337,"extra_servicecode":"C7","extra_service_cnname":"关税预付","extra_servicevalue":"","extra_servicenote":"","extra_paytype":1,"extra_paytypename":"到付","extra_createdate":"2018-07-17 16:10:58","extra_createrid":""}]
         * smt_customer : N
         * cargo_type : W
         * server_channelid :
         */

        private String order_id;  //订单id
        private String shipper_hawbcode; //订单的运单号码
        private String server_hawbcode; //服务商单号
        private String channel_hawbcode; //转单号
        private String customer_id;   //客户id
        private String customer_code;  //客户代码
        private String customer_shortname; //客户名称
        private String customer_channelid;
        private String customer_channelcode;
        private String customer_reference_number;  //第三方单号
        private String product_code;  //产品代码
        private String product_cnname; //产品名称
        private String country_code;   //目的国家
        private String consignee_postcode;  //收件人邮编
        private String consignee_city;  //收件人城市
        private String consignee_address;  //收件人地址
        private String order_weight;   //订单预计重量
        private String order_pieces;   //件数
        private String api_post_date;  //预报时间
        private String order_status;    //订单状态   P:已预报Q:问题件V：已收货C：已出仓
        private String order_status_cnname; //订单状态中文名称
        private String order_status_enname; //订单状态英文名称
        private String order_info;     //货物类型
        private String mergebox_id;   //服务渠道id
        private int mergebox_count;
        private String mergebox_arrivalno;
        private String mergebox_order;
        private String smt_customer;
        private String cargo_type;
        private String server_channelid;
        private List<ExtraserviceEntity> extraservice;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getShipper_hawbcode() {
            return shipper_hawbcode;
        }

        public void setShipper_hawbcode(String shipper_hawbcode) {
            this.shipper_hawbcode = shipper_hawbcode;
        }

        public String getServer_hawbcode() {
            return server_hawbcode;
        }

        public void setServer_hawbcode(String server_hawbcode) {
            this.server_hawbcode = server_hawbcode;
        }

        public String getChannel_hawbcode() {
            return channel_hawbcode;
        }

        public void setChannel_hawbcode(String channel_hawbcode) {
            this.channel_hawbcode = channel_hawbcode;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
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

        public String getCustomer_channelid() {
            return customer_channelid;
        }

        public void setCustomer_channelid(String customer_channelid) {
            this.customer_channelid = customer_channelid;
        }

        public String getCustomer_channelcode() {
            return customer_channelcode;
        }

        public void setCustomer_channelcode(String customer_channelcode) {
            this.customer_channelcode = customer_channelcode;
        }

        public String getCustomer_reference_number() {
            return customer_reference_number;
        }

        public void setCustomer_reference_number(String customer_reference_number) {
            this.customer_reference_number = customer_reference_number;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public String getProduct_cnname() {
            return product_cnname;
        }

        public void setProduct_cnname(String product_cnname) {
            this.product_cnname = product_cnname;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getConsignee_postcode() {
            return consignee_postcode;
        }

        public void setConsignee_postcode(String consignee_postcode) {
            this.consignee_postcode = consignee_postcode;
        }

        public String getConsignee_city() {
            return consignee_city;
        }

        public void setConsignee_city(String consignee_city) {
            this.consignee_city = consignee_city;
        }

        public String getConsignee_address() {
            return consignee_address;
        }

        public void setConsignee_address(String consignee_address) {
            this.consignee_address = consignee_address;
        }

        public String getOrder_weight() {
            return order_weight;
        }

        public void setOrder_weight(String order_weight) {
            this.order_weight = order_weight;
        }

        public String getOrder_pieces() {
            return order_pieces;
        }

        public void setOrder_pieces(String order_pieces) {
            this.order_pieces = order_pieces;
        }

        public String getApi_post_date() {
            return api_post_date;
        }

        public void setApi_post_date(String api_post_date) {
            this.api_post_date = api_post_date;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_status_cnname() {
            return order_status_cnname;
        }

        public void setOrder_status_cnname(String order_status_cnname) {
            this.order_status_cnname = order_status_cnname;
        }

        public String getOrder_status_enname() {
            return order_status_enname;
        }

        public void setOrder_status_enname(String order_status_enname) {
            this.order_status_enname = order_status_enname;
        }

        public String getOrder_info() {
            return order_info;
        }

        public void setOrder_info(String order_info) {
            this.order_info = order_info;
        }

        public String getMergebox_id() {
            return mergebox_id;
        }

        public void setMergebox_id(String mergebox_id) {
            this.mergebox_id = mergebox_id;
        }

        public int getMergebox_count() {
            return mergebox_count;
        }

        public void setMergebox_count(int mergebox_count) {
            this.mergebox_count = mergebox_count;
        }

        public String getMergebox_arrivalno() {
            return mergebox_arrivalno;
        }

        public void setMergebox_arrivalno(String mergebox_arrivalno) {
            this.mergebox_arrivalno = mergebox_arrivalno;
        }

        public String getMergebox_order() {
            return mergebox_order;
        }

        public void setMergebox_order(String mergebox_order) {
            this.mergebox_order = mergebox_order;
        }

        public String getSmt_customer() {
            return smt_customer;
        }

        public void setSmt_customer(String smt_customer) {
            this.smt_customer = smt_customer;
        }

        public String getCargo_type() {
            return cargo_type;
        }

        public void setCargo_type(String cargo_type) {
            this.cargo_type = cargo_type;
        }

        public String getServer_channelid() {
            return server_channelid;
        }

        public void setServer_channelid(String server_channelid) {
            this.server_channelid = server_channelid;
        }

        public List<ExtraserviceEntity> getExtraservice() {
            return extraservice;
        }

        public void setExtraservice(List<ExtraserviceEntity> extraservice) {
            this.extraservice = extraservice;
        }

        public static class ExtraserviceEntity {
            /**
             * order_id : 337
             * extra_servicecode : A7
             * extra_service_cnname : 到付手续费
             * extra_servicevalue :
             * extra_servicenote :
             * extra_paytype : 1
             * extra_paytypename : 到付
             * extra_createdate : 2018-07-17 16:10:58
             * extra_createrid :
             */

            private int order_id;
            private String extra_servicecode;
            private String extra_service_cnname;
            private String extra_servicevalue;
            private String extra_servicenote;
            private int extra_paytype;
            private String extra_paytypename;
            private String extra_createdate;
            private String extra_createrid;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getExtra_servicecode() {
                return extra_servicecode;
            }

            public void setExtra_servicecode(String extra_servicecode) {
                this.extra_servicecode = extra_servicecode;
            }

            public String getExtra_service_cnname() {
                return extra_service_cnname;
            }

            public void setExtra_service_cnname(String extra_service_cnname) {
                this.extra_service_cnname = extra_service_cnname;
            }

            public String getExtra_servicevalue() {
                return extra_servicevalue;
            }

            public void setExtra_servicevalue(String extra_servicevalue) {
                this.extra_servicevalue = extra_servicevalue;
            }

            public String getExtra_servicenote() {
                return extra_servicenote;
            }

            public void setExtra_servicenote(String extra_servicenote) {
                this.extra_servicenote = extra_servicenote;
            }

            public int getExtra_paytype() {
                return extra_paytype;
            }

            public void setExtra_paytype(int extra_paytype) {
                this.extra_paytype = extra_paytype;
            }

            public String getExtra_paytypename() {
                return extra_paytypename;
            }

            public void setExtra_paytypename(String extra_paytypename) {
                this.extra_paytypename = extra_paytypename;
            }

            public String getExtra_createdate() {
                return extra_createdate;
            }

            public void setExtra_createdate(String extra_createdate) {
                this.extra_createdate = extra_createdate;
            }

            public String getExtra_createrid() {
                return extra_createrid;
            }

            public void setExtra_createrid(String extra_createrid) {
                this.extra_createrid = extra_createrid;
            }
        }
    }
}
