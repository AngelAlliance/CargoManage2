package com.sz.ljs.warehousing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/27.
 */

public class ChenckInModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"Bs_id":"1948291","Server_channelid":"1185","Server_channelcode":"IPS-CHINA","Server_code":"IPS-CHINA","Shipper_hawbcode":"3336669993","Serve_hawbcode":"3336669993","Country_Code":"IR","Shipper_Chargeweight":"334.000","Estimate_Chargeweight":"334.000","Shipper_Pieces":"1","Cargo_Type":"W","Post_Code":"","Address":"  ","IsSuccess":true,"CheckedOutSuccess":false,"ErrorMsg":null,"Group_Concat_Invoice_Cname":null,"Group_Concat_Invoice_Ename":null,"Sum_Invoice_Totalcharge":null,"PromptMsgList":[],"ChildNumber":null,"Elapsed_StartTime":"2018-08-25T17:02:22.1858531+08:00","Elapsed_EndTime":"2018-08-25T17:02:24.1614951+08:00"}
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
         * Bs_id : 1948291
         * Server_channelid : 1185
         * Server_channelcode : IPS-CHINA
         * Server_code : IPS-CHINA
         * Shipper_hawbcode : 3336669993
         * Serve_hawbcode : 3336669993
         * Country_Code : IR
         * Shipper_Chargeweight : 334.000
         * Estimate_Chargeweight : 334.000
         * Shipper_Pieces : 1
         * Cargo_Type : W
         * Post_Code :
         * Address :
         * IsSuccess : true
         * CheckedOutSuccess : false
         * ErrorMsg : null
         * Group_Concat_Invoice_Cname : null
         * Group_Concat_Invoice_Ename : null
         * Sum_Invoice_Totalcharge : null
         * PromptMsgList : []
         * ChildNumber : null
         * Elapsed_StartTime : 2018-08-25T17:02:22.1858531+08:00
         * Elapsed_EndTime : 2018-08-25T17:02:24.1614951+08:00
         */

        private String Bs_id;
        private String Server_channelid;
        private String Server_channelcode;
        private String Server_code;
        private String Shipper_hawbcode;
        private String Serve_hawbcode;
        private String Country_Code;
        private String Shipper_Chargeweight;
        private String Estimate_Chargeweight;
        private String Shipper_Pieces;
        private String Cargo_Type;
        private String Post_Code;
        private String Address;
        private boolean IsSuccess;
        private boolean CheckedOutSuccess;
        private Object ErrorMsg;
        private Object Group_Concat_Invoice_Cname;
        private Object Group_Concat_Invoice_Ename;
        private Object Sum_Invoice_Totalcharge;
        private Object ChildNumber;
        private String Elapsed_StartTime;
        private String Elapsed_EndTime;
        private List<?> PromptMsgList;

        public String getBs_id() {
            return Bs_id;
        }

        public void setBs_id(String Bs_id) {
            this.Bs_id = Bs_id;
        }

        public String getServer_channelid() {
            return Server_channelid;
        }

        public void setServer_channelid(String Server_channelid) {
            this.Server_channelid = Server_channelid;
        }

        public String getServer_channelcode() {
            return Server_channelcode;
        }

        public void setServer_channelcode(String Server_channelcode) {
            this.Server_channelcode = Server_channelcode;
        }

        public String getServer_code() {
            return Server_code;
        }

        public void setServer_code(String Server_code) {
            this.Server_code = Server_code;
        }

        public String getShipper_hawbcode() {
            return Shipper_hawbcode;
        }

        public void setShipper_hawbcode(String Shipper_hawbcode) {
            this.Shipper_hawbcode = Shipper_hawbcode;
        }

        public String getServe_hawbcode() {
            return Serve_hawbcode;
        }

        public void setServe_hawbcode(String Serve_hawbcode) {
            this.Serve_hawbcode = Serve_hawbcode;
        }

        public String getCountry_Code() {
            return Country_Code;
        }

        public void setCountry_Code(String Country_Code) {
            this.Country_Code = Country_Code;
        }

        public String getShipper_Chargeweight() {
            return Shipper_Chargeweight;
        }

        public void setShipper_Chargeweight(String Shipper_Chargeweight) {
            this.Shipper_Chargeweight = Shipper_Chargeweight;
        }

        public String getEstimate_Chargeweight() {
            return Estimate_Chargeweight;
        }

        public void setEstimate_Chargeweight(String Estimate_Chargeweight) {
            this.Estimate_Chargeweight = Estimate_Chargeweight;
        }

        public String getShipper_Pieces() {
            return Shipper_Pieces;
        }

        public void setShipper_Pieces(String Shipper_Pieces) {
            this.Shipper_Pieces = Shipper_Pieces;
        }

        public String getCargo_Type() {
            return Cargo_Type;
        }

        public void setCargo_Type(String Cargo_Type) {
            this.Cargo_Type = Cargo_Type;
        }

        public String getPost_Code() {
            return Post_Code;
        }

        public void setPost_Code(String Post_Code) {
            this.Post_Code = Post_Code;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public boolean isIsSuccess() {
            return IsSuccess;
        }

        public void setIsSuccess(boolean IsSuccess) {
            this.IsSuccess = IsSuccess;
        }

        public boolean isCheckedOutSuccess() {
            return CheckedOutSuccess;
        }

        public void setCheckedOutSuccess(boolean CheckedOutSuccess) {
            this.CheckedOutSuccess = CheckedOutSuccess;
        }

        public Object getErrorMsg() {
            return ErrorMsg;
        }

        public void setErrorMsg(Object ErrorMsg) {
            this.ErrorMsg = ErrorMsg;
        }

        public Object getGroup_Concat_Invoice_Cname() {
            return Group_Concat_Invoice_Cname;
        }

        public void setGroup_Concat_Invoice_Cname(Object Group_Concat_Invoice_Cname) {
            this.Group_Concat_Invoice_Cname = Group_Concat_Invoice_Cname;
        }

        public Object getGroup_Concat_Invoice_Ename() {
            return Group_Concat_Invoice_Ename;
        }

        public void setGroup_Concat_Invoice_Ename(Object Group_Concat_Invoice_Ename) {
            this.Group_Concat_Invoice_Ename = Group_Concat_Invoice_Ename;
        }

        public Object getSum_Invoice_Totalcharge() {
            return Sum_Invoice_Totalcharge;
        }

        public void setSum_Invoice_Totalcharge(Object Sum_Invoice_Totalcharge) {
            this.Sum_Invoice_Totalcharge = Sum_Invoice_Totalcharge;
        }

        public Object getChildNumber() {
            return ChildNumber;
        }

        public void setChildNumber(Object ChildNumber) {
            this.ChildNumber = ChildNumber;
        }

        public String getElapsed_StartTime() {
            return Elapsed_StartTime;
        }

        public void setElapsed_StartTime(String Elapsed_StartTime) {
            this.Elapsed_StartTime = Elapsed_StartTime;
        }

        public String getElapsed_EndTime() {
            return Elapsed_EndTime;
        }

        public void setElapsed_EndTime(String Elapsed_EndTime) {
            this.Elapsed_EndTime = Elapsed_EndTime;
        }

        public List<?> getPromptMsgList() {
            return PromptMsgList;
        }

        public void setPromptMsgList(List<?> PromptMsgList) {
            this.PromptMsgList = PromptMsgList;
        }
    }
}
