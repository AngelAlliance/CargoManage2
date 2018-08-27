package com.sz.ljs.packgoods.contract;

import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PackgoodsContract {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");
    public static final String SUMMARY="summary";


    public static final String OG_ID ="og_id";
    public static final String SERVRE_ID ="service_id";
    public static final String SERVRE_CHANNELID ="server_channelid";
    @POST("user/GetDepltList")
    @FormUrlEncoded
    Flowable<GsonDepltListModel> getDepltList(@Header("token") String token, @FieldMap Map<String, String> param);


    public static final String LIST_PARAMSTRING ="listParamString";
    @POST("user/BagPutBusiness")
    @FormUrlEncoded
    Flowable<BaseResultModel> bagPutBusiness(@Header("token") String token, @FieldMap Map<String, String> param);


    public static final String STR_BAG_CODE ="strBagCode";
    public static final String STR_LENGTH ="strlength";
    public static final String STR_WIDTH ="strwidth";
    public static final String STR_HEIGHT ="strheight";
    public static final String TEXT_WEIGHT ="txtWeight";
    public static final String TEXTBAG_GROSSWEIGHT ="txtbag_grossweight";
    @POST("user/BagWeighing")
    @FormUrlEncoded
    Flowable<BaseResultModel> bagWeighing(@Header("token") String token, @FieldMap Map<String, String> param);

    @POST("user/GetServiceChannel")
    @FormUrlEncoded
    Flowable<GsonServiceChannelModel> getServiceChannel(@Header("token") String token, @FieldMap Map<String, String> param);

    public static final String USERID ="userId";
    public static final String STR_EXPRESS_CODE ="strExpressCode";
    public static final String BS_LIST ="bs_list";
    @POST("user/AddBussinessPackage")
    @FormUrlEncoded
    Flowable<GsonAddBussinessPackageModel> addBussinessPackage(@Header("token") String token, @FieldMap Map<String, String> param);


    public static final String BAG_LABEL_CODE ="bag_labelcode";
    @POST("user/Unpacking")
    @FormUrlEncoded
    Flowable<BaseResultModel> unpacking(@Header("token") String token, @FieldMap Map<String, String> param);


}
