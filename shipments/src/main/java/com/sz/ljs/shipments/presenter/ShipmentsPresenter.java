package com.sz.ljs.shipments.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.shipments.contract.ShipmentsContract;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.GsonSaveTransportBatchAndBusinessModel;
import com.sz.ljs.shipments.model.TransportBatchBusinessParamModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipmentsPresenter {
    private ShipmentsContract mContract;

    public ShipmentsPresenter() {
        mContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(ShipmentsContract.class);
    }

    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    public Flowable<GsonDepltListModel> getDepltList(String og_id, String service_id, String server_channelid) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.OG_ID, og_id);
        param.put(ShipmentsContract.SERVRE_ID, service_id);
        param.put(ShipmentsContract.SERVRE_CHANNELID, server_channelid);
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        return mContract.getDepltList(token, param);
    }


    //TODO 查看收货服务商跟机构
    public Flowable<GsonOrgServerModel> getOrgServer() {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        return mContract.getOrgServer(token, param);
    }

    //TODO 出库生成主单 forecast_arrival_date:生成时间  to_og_id:下一站机构id  to_server_id:收货服务商id 数据在查询服务商接口  to_server_code:收货服务商代码  list:运单包集合
    public Flowable<GsonSaveTransportBatchAndBusinessModel> saveTransportBatchAndBusiness(
            String forecast_arrival_date,String to_og_id,String to_server_id,String to_server_code
            ,List<TransportBatchBusinessParamModel>list){
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        param.put("forecast_arrival_date", forecast_arrival_date);
        param.put("from_og_code", UserModel.getInstance().getOg_shortcode());
        param.put("from_og_id", ""+UserModel.getInstance().getOg_id());
        param.put("to_og_id", to_og_id);
        param.put("to_server_id", to_server_id);
        param.put("to_server_code", to_server_code);
        param.put("TransportBatchBusinessParam", new Gson().toJson(list));
        return mContract.saveTransportBatchAndBusiness(token,param);
    }
}
