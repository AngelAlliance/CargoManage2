package com.sz.ljs.shipments.contract;

import com.sz.ljs.base.interfacecallback.IBaseView;
import com.sz.ljs.base.interfacecallback.IGenView;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.GsonSaveTransportBatchAndBusinessModel;
import com.sz.ljs.shipments.model.GsonServiceChannelModel;
import com.sz.ljs.shipments.model.TransportBatchBusinessParamModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShipmentsContract {

    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");
    public static final String SUMMARY="summary";
    public static final String USERID ="userId";

    public static final String OG_ID ="og_id";
    public static final String SERVRE_ID ="service_id";
    public static final String SERVRE_CHANNELID ="server_channelid";
    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int REQUEST_SUCCESS_ID = 1;//网络请求成功
    public static final int GET_ORG_SERVER_SUCCESS = 2;//查看收货服务商跟机构
    public static final int GET_SERVICE_CHANNEL_SUCCESS = 3;//生效渠道
    public static final int SAVE_TRANSPORTBATCH_AND_BUSINESS_SUCCESS = 4;//出库生成主单

    interface View extends IGenView {

    }

    interface Presenter {

        //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
        public void getDepltList(String og_id, String service_id, String server_channelid);

        //TODO 生效渠道
        public void getServiceChannel();

        //TODO 查看收货服务商跟机构
        public void getOrgServer();

        //TODO 出库生成主单 forecast_arrival_date:生成时间  to_og_id:下一站机构id  to_server_id:收货服务商id 数据在查询服务商接口  to_server_code:收货服务商代码  list:运单包集合
        public void saveTransportBatchAndBusiness(
                String forecast_arrival_date,String to_og_id,String to_server_id,String to_server_code
                ,List<TransportBatchBusinessParamModel> list);
    }

}
