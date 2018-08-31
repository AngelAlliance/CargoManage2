package com.sz.ljs.inventory.contract;

import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.inventory.model.ResultBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Author: Mr. Duan
 * Date: 2018/8/29
 * Description:请求调用接口
 */

public interface InventoryContract {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");

    public static final String NUMBER = "number";   //运单号码
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密

    @POST("user/FindExpressByID")
    @FormUrlEncoded
    Flowable<ResultBean> getFindExpressByID(@Header("token") String token, @FieldMap Map<String, String> param);
}
