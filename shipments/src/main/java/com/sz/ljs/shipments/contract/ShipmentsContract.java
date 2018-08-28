package com.sz.ljs.shipments.contract;

import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.GsonSaveTransportBatchAndBusinessModel;

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
    @POST("user/GetDepltList")
    @FormUrlEncoded
    Flowable<GsonDepltListModel> getDepltList(@Header("token") String token, @FieldMap Map<String, String> param);

    @POST("user/GetOrgServer")
    @FormUrlEncoded
    Flowable<GsonOrgServerModel> getOrgServer(@Header("token") String token, @FieldMap Map<String, String> param);

    @POST("user/SaveTransportBatchAndBusiness")
    @FormUrlEncoded
    Flowable<GsonSaveTransportBatchAndBusinessModel> saveTransportBatchAndBusiness(@Header("token") String token, @FieldMap Map<String, String> param);
}
