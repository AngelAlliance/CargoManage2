package com.sz.ljs.articlescan.model;

import java.util.List;

public class GsonSelectShipmentBagReceiveModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"tra_batch_code":"F-101SZX-T-101CAN-2018090402","BagReceiveList":[{"hawb_code":"PPNO-QWSS","from_og_name":"深圳","server_channel_cnname":"IPS-香港","bag_countpieces":"1","bag_totalshipperpieces":"1","tb_id":"1860"}]}]
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
         * tra_batch_code : F-101SZX-T-101CAN-2018090402
         * BagReceiveList : [{"hawb_code":"PPNO-QWSS","from_og_name":"深圳","server_channel_cnname":"IPS-香港","bag_countpieces":"1","bag_totalshipperpieces":"1","tb_id":"1860"}]
         */

        private String tra_batch_code; //运输编号
        private List<BagReceiveListBean> BagReceiveList;

        public String getTra_batch_code() {
            return tra_batch_code;
        }

        public void setTra_batch_code(String tra_batch_code) {
            this.tra_batch_code = tra_batch_code;
        }

        public List<BagReceiveListBean> getBagReceiveList() {
            return BagReceiveList;
        }

        public void setBagReceiveList(List<BagReceiveListBean> BagReceiveList) {
            this.BagReceiveList = BagReceiveList;
        }

        public static class BagReceiveListBean {
            /**
             * hawb_code : PPNO-QWSS
             * from_og_name : 深圳
             * server_channel_cnname : IPS-香港
             * bag_countpieces : 1
             * bag_totalshipperpieces : 1
             * tb_id : 1860
             */

            private String hawb_code; //包编号
            private String from_og_name; //机构
            private String server_channel_cnname; //渠道
            private String bag_countpieces; //件
            private String bag_totalshipperpieces;//运单数量
            private String tb_id;//包id

            public String getHawb_code() {
                return hawb_code;
            }

            public void setHawb_code(String hawb_code) {
                this.hawb_code = hawb_code;
            }

            public String getFrom_og_name() {
                return from_og_name;
            }

            public void setFrom_og_name(String from_og_name) {
                this.from_og_name = from_og_name;
            }

            public String getServer_channel_cnname() {
                return server_channel_cnname;
            }

            public void setServer_channel_cnname(String server_channel_cnname) {
                this.server_channel_cnname = server_channel_cnname;
            }

            public String getBag_countpieces() {
                return bag_countpieces;
            }

            public void setBag_countpieces(String bag_countpieces) {
                this.bag_countpieces = bag_countpieces;
            }

            public String getBag_totalshipperpieces() {
                return bag_totalshipperpieces;
            }

            public void setBag_totalshipperpieces(String bag_totalshipperpieces) {
                this.bag_totalshipperpieces = bag_totalshipperpieces;
            }

            public String getTb_id() {
                return tb_id;
            }

            public void setTb_id(String tb_id) {
                this.tb_id = tb_id;
            }
        }
    }
}
