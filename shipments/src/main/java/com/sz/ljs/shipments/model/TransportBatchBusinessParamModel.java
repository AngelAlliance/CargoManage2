package com.sz.ljs.shipments.model;

import java.util.List;

public class TransportBatchBusinessParamModel {

    /**
     * businessTR : {"receive_date":"","receiver_id":"","receive_check_weight":"","business_gross_weight":"25.400","checkout_gross_weight":"25.550","hawb_bs_id":"809846","hawb_code":"PPNO-19580","hawb_type":"B","tra_id":"","tb_id":"","TableName":"","PrimaryKeys":"","ReturnName":""}
     * list_business : [{"bs_id":"1048261","child_number":"300611253"}]
     */

    private BusinessTRBean businessTR;
    private List<ListBusinessBean> list_business;

    public BusinessTRBean getBusinessTR() {
        return businessTR;
    }

    public void setBusinessTR(BusinessTRBean businessTR) {
        this.businessTR = businessTR;
    }

    public List<ListBusinessBean> getList_business() {
        return list_business;
    }

    public void setList_business(List<ListBusinessBean> list_business) {
        this.list_business = list_business;
    }

    public static class BusinessTRBean {
        /**
         * receive_date :
         * receiver_id :
         * receive_check_weight :
         * business_gross_weight : 25.400
         * checkout_gross_weight : 25.550
         * hawb_bs_id : 809846
         * hawb_code : PPNO-19580
         * hawb_type : B
         * tra_id :
         * tb_id :
         * TableName :
         * PrimaryKeys :
         * ReturnName :
         */

        private String receive_date;
        private String receiver_id;
        private String receive_check_weight;
        private String business_gross_weight;
        private String checkout_gross_weight;
        private String hawb_bs_id;
        private String hawb_code;
        private String hawb_type;
        private String tra_id;
        private String tb_id;
        private String TableName;
        private String PrimaryKeys;
        private String ReturnName;

        public String getReceive_date() {
            return receive_date;
        }

        public void setReceive_date(String receive_date) {
            this.receive_date = receive_date;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getReceive_check_weight() {
            return receive_check_weight;
        }

        public void setReceive_check_weight(String receive_check_weight) {
            this.receive_check_weight = receive_check_weight;
        }

        public String getBusiness_gross_weight() {
            return business_gross_weight;
        }

        public void setBusiness_gross_weight(String business_gross_weight) {
            this.business_gross_weight = business_gross_weight;
        }

        public String getCheckout_gross_weight() {
            return checkout_gross_weight;
        }

        public void setCheckout_gross_weight(String checkout_gross_weight) {
            this.checkout_gross_weight = checkout_gross_weight;
        }

        public String getHawb_bs_id() {
            return hawb_bs_id;
        }

        public void setHawb_bs_id(String hawb_bs_id) {
            this.hawb_bs_id = hawb_bs_id;
        }

        public String getHawb_code() {
            return hawb_code;
        }

        public void setHawb_code(String hawb_code) {
            this.hawb_code = hawb_code;
        }

        public String getHawb_type() {
            return hawb_type;
        }

        public void setHawb_type(String hawb_type) {
            this.hawb_type = hawb_type;
        }

        public String getTra_id() {
            return tra_id;
        }

        public void setTra_id(String tra_id) {
            this.tra_id = tra_id;
        }

        public String getTb_id() {
            return tb_id;
        }

        public void setTb_id(String tb_id) {
            this.tb_id = tb_id;
        }

        public String getTableName() {
            return TableName;
        }

        public void setTableName(String TableName) {
            this.TableName = TableName;
        }

        public String getPrimaryKeys() {
            return PrimaryKeys;
        }

        public void setPrimaryKeys(String PrimaryKeys) {
            this.PrimaryKeys = PrimaryKeys;
        }

        public String getReturnName() {
            return ReturnName;
        }

        public void setReturnName(String ReturnName) {
            this.ReturnName = ReturnName;
        }
    }

    public static class ListBusinessBean {
        /**
         * bs_id : 1048261
         * child_number : 300611253
         */

        private String bs_id;
        private String child_number;

        public String getBs_id() {
            return bs_id;
        }

        public void setBs_id(String bs_id) {
            this.bs_id = bs_id;
        }

        public String getChild_number() {
            return child_number;
        }

        public void setChild_number(String child_number) {
            this.child_number = child_number;
        }
    }
}
