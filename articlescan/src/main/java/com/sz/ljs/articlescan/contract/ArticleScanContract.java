package com.sz.ljs.articlescan.contract;

import com.sz.ljs.base.interfacecallback.IGenView;

public interface ArticleScanContract {
    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int REQUEST_SUCCESS_ID = 1;//网络请求成功
    public static final int BUSINESS_RECEIPT_SUCCESS_ID = 2;//网络请求成功
    public static final int GET_ORG_SERVER_SUCCESS = 3;//查看收货服务商跟机构
    public static final String USERID = "userId";

    interface View extends IGenView {

    }

    interface Presenter {
        public void SelectShipmentBagReceive(String arrival_date,String from_og_id,String server_channelid);

        public void TransportBatchBusinessReceipt(String strTb_id);
        //TODO 查看收货服务商跟机构
        public void getOrgServer();
    }
}
