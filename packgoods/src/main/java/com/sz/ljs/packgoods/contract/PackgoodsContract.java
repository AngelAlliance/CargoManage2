package com.sz.ljs.packgoods.contract;

import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.packgoods.model.GsonDepltListModel;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PackgoodsContract {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");



    public static final String OG_ID ="og_id";
    public static final String SERVRE_ID ="service_id";
    public static final String SERVRE_CHANNELID ="server_channelid";

    @POST("user/GetDepltList")
    @FormUrlEncoded
    Flowable<GsonDepltListModel> getDepltList(@Header("token") String token, @FieldMap Map<String, String> param);
}
