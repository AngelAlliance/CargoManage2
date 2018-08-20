package com.ljs.examinegoods.contract;

import com.ljs.examinegoods.model.OrderModel;
import com.sz.ljs.common.utils.MD5Util;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/8/20.
 */

public interface ExamineGoodsContract {

    public static final String summary= MD5Util.get32MD5Str("zhbg_ips2018_cn");

    public static final String TOKEN = "token";
    public static final String NUMBER = "number";
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密
    @POST("user/GetOrderbyNumber")
    @FormUrlEncoded
    Flowable<OrderModel> getOrderByNumber(@Header("token") String token,@FieldMap Map<String, String> param);
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @POST("user/GetOrderbyNumber")
//    Flowable<OrderModel> getOrderByNumber(@Header("token") String token, @Body RequestBody route);

}
