package com.sz.ljs.common.model;

import java.util.List;

public class GsonDepltListModel {


    /**
     * Code : 1
     * Msg : OK
     * Data : {"BaleList":[{"bs_id":"1047383","shipper_hawbcode":"247241997394","child_number":"247241997394","server_id":"591","server_channelid":"591","checkin_date":"2018-03-12 10:25:02","shipper_pieces":"1","outvolume_grossweight":"0.200","outvolume_length":"5.0","outvolume_width":"10.0","outvolume_height":"14.0","balance_sign":"未欠费","holding":"未扣件","IsSelect":""}],"ShppingCnList":[{"bag_pieces":"1","number_pieces":"1","bag_weight":"21.000","weighing":"21.000","bag_lable_code":"PPNO-2254WW","cn_list":[{"bs_id":"1048354","shipper_hawbcode":"300801933","child_number":"300801933","server_id":"592","server_channelid":"1186","checkin_date":"2018-07-09 18:58:37","shipper_pieces":"1","outvolume_grossweight":"21.000","outvolume_length":"","outvolume_width":"","outvolume_height":"","balance_sign":"","holding":"","IsSelect":""}],"IsSelect":"","length":"0.0","width":"0.0","height":"0.0"}]}
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
        private List<ExpressModel> BaleList;
        private List<ExpressPackageModel> ShppingCnList;

        public List<ExpressModel> getBaleList() {
            return BaleList;
        }

        public void setBaleList(List<ExpressModel> BaleList) {
            this.BaleList = BaleList;
        }

        public List<ExpressPackageModel> getShppingCnList() {
            return ShppingCnList;
        }

        public void setShppingCnList(List<ExpressPackageModel> ShppingCnList) {
            this.ShppingCnList = ShppingCnList;
        }

    }
}
