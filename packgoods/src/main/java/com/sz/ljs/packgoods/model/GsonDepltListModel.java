package com.sz.ljs.packgoods.model;

import java.util.List;

public class GsonDepltListModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"BaleList":[{"id":"1047583-1946565","bs_id":"1047583","shipper_hawbcode":"300601604","serve_hawbcode":"300601604","child_number":"300601604","server_id":"591","server_channelid":"591","formal_code":"IPS-CHINA","server_channel_cnname":"IPS-大陆","invoice_totalcharge":"0.00","cargo_type_cnname":"文件","last_comment":"运单 300601604从袋号：PPNO-123WQ中提出","checkin_date":"2018-03-17 10:33:08","shipper_pieces":"1","outvolume_grossweight":"0.100","outvolume_volumeweight":"0.142","outvolume_chargeweight":"0.142","outvolume_length":"1.0","outvolume_width":"25.0","outvolume_height":"34.0","balance_sign":"未欠费","holding":"未扣件"}],"ShppingCnList":[{"bag_note":"包装号：PPNO-2254WW(件数：1,重量：21.000,运单数：1,称重：)","bag_lable_code":"PPNO-2254WW","cn_list":[{"bs_id":"1048354","shipper_hawbcode":"300801933","serve_hawbcode":"300801933","child_number":"300801933","server_id":"592","server_channelid":"1186","formal_code":"IPS-HK","server_channel_cnname":"IPS-香港","last_comment":"装袋成功，袋号：PPNO-2254WW","checkin_date":"2018-07-09 18:58:37","scan_date":"2018-08-23 15:13:40","shipper_pieces":"1","bag_id":"809870","bag_countpieces":"1","bag_grossweight":"21.000","bag_labelcode":"PPNO-2254WW","bag_server_channelid":"1186","bag_totalshipperpieces":"1","bag_weigh":"","outvolume_chargeweight":"21.000"}]}]}
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
        private List<BaleListBean> BaleList;
        private List<ShppingCnListBean> ShppingCnList;

        public List<BaleListBean> getBaleList() {
            return BaleList;
        }

        public void setBaleList(List<BaleListBean> BaleList) {
            this.BaleList = BaleList;
        }

        public List<ShppingCnListBean> getShppingCnList() {
            return ShppingCnList;
        }

        public void setShppingCnList(List<ShppingCnListBean> ShppingCnList) {
            this.ShppingCnList = ShppingCnList;
        }

        public static class BaleListBean {
            /**
             * id : 1047583-1946565
             * bs_id : 1047583
             * shipper_hawbcode : 300601604
             * serve_hawbcode : 300601604
             * child_number : 300601604
             * server_id : 591
             * server_channelid : 591
             * formal_code : IPS-CHINA
             * server_channel_cnname : IPS-大陆
             * invoice_totalcharge : 0.00
             * cargo_type_cnname : 文件
             * last_comment : 运单 300601604从袋号：PPNO-123WQ中提出
             * checkin_date : 2018-03-17 10:33:08
             * shipper_pieces : 1
             * outvolume_grossweight : 0.100
             * outvolume_volumeweight : 0.142
             * outvolume_chargeweight : 0.142
             * outvolume_length : 1.0
             * outvolume_width : 25.0
             * outvolume_height : 34.0
             * balance_sign : 未欠费
             * holding : 未扣件
             */

            private String id;
            private String bs_id;
            private String shipper_hawbcode;
            private String serve_hawbcode;
            private String child_number;
            private String server_id;
            private String server_channelid;
            private String formal_code;
            private String server_channel_cnname;
            private String invoice_totalcharge;
            private String cargo_type_cnname;
            private String last_comment;
            private String checkin_date;
            private String shipper_pieces;
            private String outvolume_grossweight;
            private String outvolume_volumeweight;
            private String outvolume_chargeweight;
            private String outvolume_length;
            private String outvolume_width;
            private String outvolume_height;
            private String balance_sign;
            private String holding;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBs_id() {
                return bs_id;
            }

            public void setBs_id(String bs_id) {
                this.bs_id = bs_id;
            }

            public String getShipper_hawbcode() {
                return shipper_hawbcode;
            }

            public void setShipper_hawbcode(String shipper_hawbcode) {
                this.shipper_hawbcode = shipper_hawbcode;
            }

            public String getServe_hawbcode() {
                return serve_hawbcode;
            }

            public void setServe_hawbcode(String serve_hawbcode) {
                this.serve_hawbcode = serve_hawbcode;
            }

            public String getChild_number() {
                return child_number;
            }

            public void setChild_number(String child_number) {
                this.child_number = child_number;
            }

            public String getServer_id() {
                return server_id;
            }

            public void setServer_id(String server_id) {
                this.server_id = server_id;
            }

            public String getServer_channelid() {
                return server_channelid;
            }

            public void setServer_channelid(String server_channelid) {
                this.server_channelid = server_channelid;
            }

            public String getFormal_code() {
                return formal_code;
            }

            public void setFormal_code(String formal_code) {
                this.formal_code = formal_code;
            }

            public String getServer_channel_cnname() {
                return server_channel_cnname;
            }

            public void setServer_channel_cnname(String server_channel_cnname) {
                this.server_channel_cnname = server_channel_cnname;
            }

            public String getInvoice_totalcharge() {
                return invoice_totalcharge;
            }

            public void setInvoice_totalcharge(String invoice_totalcharge) {
                this.invoice_totalcharge = invoice_totalcharge;
            }

            public String getCargo_type_cnname() {
                return cargo_type_cnname;
            }

            public void setCargo_type_cnname(String cargo_type_cnname) {
                this.cargo_type_cnname = cargo_type_cnname;
            }

            public String getLast_comment() {
                return last_comment;
            }

            public void setLast_comment(String last_comment) {
                this.last_comment = last_comment;
            }

            public String getCheckin_date() {
                return checkin_date;
            }

            public void setCheckin_date(String checkin_date) {
                this.checkin_date = checkin_date;
            }

            public String getShipper_pieces() {
                return shipper_pieces;
            }

            public void setShipper_pieces(String shipper_pieces) {
                this.shipper_pieces = shipper_pieces;
            }

            public String getOutvolume_grossweight() {
                return outvolume_grossweight;
            }

            public void setOutvolume_grossweight(String outvolume_grossweight) {
                this.outvolume_grossweight = outvolume_grossweight;
            }

            public String getOutvolume_volumeweight() {
                return outvolume_volumeweight;
            }

            public void setOutvolume_volumeweight(String outvolume_volumeweight) {
                this.outvolume_volumeweight = outvolume_volumeweight;
            }

            public String getOutvolume_chargeweight() {
                return outvolume_chargeweight;
            }

            public void setOutvolume_chargeweight(String outvolume_chargeweight) {
                this.outvolume_chargeweight = outvolume_chargeweight;
            }

            public String getOutvolume_length() {
                return outvolume_length;
            }

            public void setOutvolume_length(String outvolume_length) {
                this.outvolume_length = outvolume_length;
            }

            public String getOutvolume_width() {
                return outvolume_width;
            }

            public void setOutvolume_width(String outvolume_width) {
                this.outvolume_width = outvolume_width;
            }

            public String getOutvolume_height() {
                return outvolume_height;
            }

            public void setOutvolume_height(String outvolume_height) {
                this.outvolume_height = outvolume_height;
            }

            public String getBalance_sign() {
                return balance_sign;
            }

            public void setBalance_sign(String balance_sign) {
                this.balance_sign = balance_sign;
            }

            public String getHolding() {
                return holding;
            }

            public void setHolding(String holding) {
                this.holding = holding;
            }
        }

        public static class ShppingCnListBean {
            /**
             * bag_note : 包装号：PPNO-2254WW(件数：1,重量：21.000,运单数：1,称重：)
             * bag_lable_code : PPNO-2254WW
             * cn_list : [{"bs_id":"1048354","shipper_hawbcode":"300801933","serve_hawbcode":"300801933","child_number":"300801933","server_id":"592","server_channelid":"1186","formal_code":"IPS-HK","server_channel_cnname":"IPS-香港","last_comment":"装袋成功，袋号：PPNO-2254WW","checkin_date":"2018-07-09 18:58:37","scan_date":"2018-08-23 15:13:40","shipper_pieces":"1","bag_id":"809870","bag_countpieces":"1","bag_grossweight":"21.000","bag_labelcode":"PPNO-2254WW","bag_server_channelid":"1186","bag_totalshipperpieces":"1","bag_weigh":"","outvolume_chargeweight":"21.000"}]
             */

            private String bag_note;
            private String bag_lable_code;
            private List<CnListBean> cn_list;

            public String getBag_note() {
                return bag_note;
            }

            public void setBag_note(String bag_note) {
                this.bag_note = bag_note;
            }

            public String getBag_lable_code() {
                return bag_lable_code;
            }

            public void setBag_lable_code(String bag_lable_code) {
                this.bag_lable_code = bag_lable_code;
            }

            public List<CnListBean> getCn_list() {
                return cn_list;
            }

            public void setCn_list(List<CnListBean> cn_list) {
                this.cn_list = cn_list;
            }

            public static class CnListBean {
                /**
                 * bs_id : 1048354
                 * shipper_hawbcode : 300801933
                 * serve_hawbcode : 300801933
                 * child_number : 300801933
                 * server_id : 592
                 * server_channelid : 1186
                 * formal_code : IPS-HK
                 * server_channel_cnname : IPS-香港
                 * last_comment : 装袋成功，袋号：PPNO-2254WW
                 * checkin_date : 2018-07-09 18:58:37
                 * scan_date : 2018-08-23 15:13:40
                 * shipper_pieces : 1
                 * bag_id : 809870
                 * bag_countpieces : 1
                 * bag_grossweight : 21.000
                 * bag_labelcode : PPNO-2254WW
                 * bag_server_channelid : 1186
                 * bag_totalshipperpieces : 1
                 * bag_weigh :
                 * outvolume_chargeweight : 21.000
                 */

                private String bs_id;
                private String shipper_hawbcode;
                private String serve_hawbcode;
                private String child_number;
                private String server_id;
                private String server_channelid;
                private String formal_code;
                private String server_channel_cnname;
                private String last_comment;
                private String checkin_date;
                private String scan_date;
                private String shipper_pieces;
                private String bag_id;
                private String bag_countpieces;
                private String bag_grossweight;
                private String bag_labelcode;
                private String bag_server_channelid;
                private String bag_totalshipperpieces;
                private String bag_weigh;
                private String outvolume_chargeweight;

                public String getBs_id() {
                    return bs_id;
                }

                public void setBs_id(String bs_id) {
                    this.bs_id = bs_id;
                }

                public String getShipper_hawbcode() {
                    return shipper_hawbcode;
                }

                public void setShipper_hawbcode(String shipper_hawbcode) {
                    this.shipper_hawbcode = shipper_hawbcode;
                }

                public String getServe_hawbcode() {
                    return serve_hawbcode;
                }

                public void setServe_hawbcode(String serve_hawbcode) {
                    this.serve_hawbcode = serve_hawbcode;
                }

                public String getChild_number() {
                    return child_number;
                }

                public void setChild_number(String child_number) {
                    this.child_number = child_number;
                }

                public String getServer_id() {
                    return server_id;
                }

                public void setServer_id(String server_id) {
                    this.server_id = server_id;
                }

                public String getServer_channelid() {
                    return server_channelid;
                }

                public void setServer_channelid(String server_channelid) {
                    this.server_channelid = server_channelid;
                }

                public String getFormal_code() {
                    return formal_code;
                }

                public void setFormal_code(String formal_code) {
                    this.formal_code = formal_code;
                }

                public String getServer_channel_cnname() {
                    return server_channel_cnname;
                }

                public void setServer_channel_cnname(String server_channel_cnname) {
                    this.server_channel_cnname = server_channel_cnname;
                }

                public String getLast_comment() {
                    return last_comment;
                }

                public void setLast_comment(String last_comment) {
                    this.last_comment = last_comment;
                }

                public String getCheckin_date() {
                    return checkin_date;
                }

                public void setCheckin_date(String checkin_date) {
                    this.checkin_date = checkin_date;
                }

                public String getScan_date() {
                    return scan_date;
                }

                public void setScan_date(String scan_date) {
                    this.scan_date = scan_date;
                }

                public String getShipper_pieces() {
                    return shipper_pieces;
                }

                public void setShipper_pieces(String shipper_pieces) {
                    this.shipper_pieces = shipper_pieces;
                }

                public String getBag_id() {
                    return bag_id;
                }

                public void setBag_id(String bag_id) {
                    this.bag_id = bag_id;
                }

                public String getBag_countpieces() {
                    return bag_countpieces;
                }

                public void setBag_countpieces(String bag_countpieces) {
                    this.bag_countpieces = bag_countpieces;
                }

                public String getBag_grossweight() {
                    return bag_grossweight;
                }

                public void setBag_grossweight(String bag_grossweight) {
                    this.bag_grossweight = bag_grossweight;
                }

                public String getBag_labelcode() {
                    return bag_labelcode;
                }

                public void setBag_labelcode(String bag_labelcode) {
                    this.bag_labelcode = bag_labelcode;
                }

                public String getBag_server_channelid() {
                    return bag_server_channelid;
                }

                public void setBag_server_channelid(String bag_server_channelid) {
                    this.bag_server_channelid = bag_server_channelid;
                }

                public String getBag_totalshipperpieces() {
                    return bag_totalshipperpieces;
                }

                public void setBag_totalshipperpieces(String bag_totalshipperpieces) {
                    this.bag_totalshipperpieces = bag_totalshipperpieces;
                }

                public String getBag_weigh() {
                    return bag_weigh;
                }

                public void setBag_weigh(String bag_weigh) {
                    this.bag_weigh = bag_weigh;
                }

                public String getOutvolume_chargeweight() {
                    return outvolume_chargeweight;
                }

                public void setOutvolume_chargeweight(String outvolume_chargeweight) {
                    this.outvolume_chargeweight = outvolume_chargeweight;
                }
            }
        }
    }
}
