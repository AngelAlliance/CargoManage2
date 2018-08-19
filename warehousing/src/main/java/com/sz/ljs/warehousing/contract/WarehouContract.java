package com.sz.ljs.warehousing.contract;

import com.sz.ljs.warehousing.model.GsonIncidentalModel;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 入库接口相关处理
 */
public interface WarehouContract {
    public static final String summary="zhbg_ips2018_cn";//请求接口summary参数MD5加密原始数据模型
    //TODO 获取杂费项
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密
    @POST("user/GetExtraservice")
    @FormUrlEncoded
    Flowable<GsonIncidentalModel> getIncidental(@FieldMap Map<String, String> param);

}
