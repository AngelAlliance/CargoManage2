package com.sz.ljs.shipments.model;

public class GsonSaveTransportBatchAndBusinessModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"transportBatchResult":{"tra_id":"132","tra_batch_code":"F-101SZX-T-101CAN-2018082802","message":"","issuccess":false}}
     */

    private int Code;
    private String Msg;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * transportBatchResult : {"tra_id":"132","tra_batch_code":"F-101SZX-T-101CAN-2018082802","message":"","issuccess":false}
         */

        private TransportBatchResultBean transportBatchResult;

        public TransportBatchResultBean getTransportBatchResult() {
            return transportBatchResult;
        }

        public void setTransportBatchResult(TransportBatchResultBean transportBatchResult) {
            this.transportBatchResult = transportBatchResult;
        }

        public static class TransportBatchResultBean {
            /**
             * tra_id : 132
             * tra_batch_code : F-101SZX-T-101CAN-2018082802
             * message :
             * issuccess : false
             */

            private String tra_id; //运输id
            private String tra_batch_code;  //运输编号
            private String message;
            private boolean issuccess;

            public String getTra_id() {
                return tra_id;
            }

            public void setTra_id(String tra_id) {
                this.tra_id = tra_id;
            }

            public String getTra_batch_code() {
                return tra_batch_code;
            }

            public void setTra_batch_code(String tra_batch_code) {
                this.tra_batch_code = tra_batch_code;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public boolean isIssuccess() {
                return issuccess;
            }

            public void setIssuccess(boolean issuccess) {
                this.issuccess = issuccess;
            }
        }
    }
}
