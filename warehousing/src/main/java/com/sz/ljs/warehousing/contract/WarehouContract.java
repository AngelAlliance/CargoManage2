package com.sz.ljs.warehousing.contract;

import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.CountryModel;
import com.sz.ljs.warehousing.model.CustomerModel;
import com.sz.ljs.warehousing.model.GsonIncidentalModel;
import com.sz.ljs.warehousing.model.ProductModel;
import com.sz.ljs.warehousing.model.SelectCurrentDayBatchModel;

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
 * 入库接口相关处理
 */
public interface WarehouContract {
    public static final String summary= MD5Util.get32MD5Str("zhbg_ips2018_cn");//请求接口summary参数MD5加密原始数据模型

    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密
    @POST("user/GetExtraservice")
    @FormUrlEncoded
    Flowable<GsonIncidentalModel> getIncidental(@FieldMap Map<String, String> param);

    @POST("user/GetCountry")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<CountryModel> getCountry(@Header("token") String token, @Body RequestBody route);

    @POST("user/GetProduct")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<ProductModel> getProduct(@Header("token") String token, @Body RequestBody route);

    public static final String CUSTOMER_CODE="customer_code";
    public static final String CUSTOMER_NAME="customer_name";
    @POST("user/GetCustomer")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<CustomerModel> getCustomer(@Header("token") String token, @Body RequestBody route);

    public static final String CUSTOMER_ID="customer_id";
    public static final String USERID="userId";
    public static final String OG_ID="og_id";
    public static final String OG_SHORT_CODE="og_short_code";
    @POST("user/SelectCurrentDayBatch")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<SelectCurrentDayBatchModel> selectCurrentDayBatch(@Header("token") String token, @Body RequestBody route);


    public static final String GROSSWEIGHT="grossweight";
    public static final String LENGTH="length";
    public static final String WIDTH="width";
    public static final String HEIGHT="height";
    public static final String PRODUCT_CODE="product_code";
    public static final String COUNTRY_CODE="country_code";
    public static final String ARRIVAL_DATE="arrival_date";
    @POST("user/CalculationVolumeWeight")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<CalculationVolumeWeightModel> calculationVolumeWeight(@Header("token") String token, @Body RequestBody route);


    public static final String NUMBER = "number";   //运单号码
    @POST("user/GetOrderbyNumber")
    @FormUrlEncoded
    Flowable<OrderModel> getOrderByNumber(@Header("token") String token, @FieldMap Map<String, String> param);


    @POST("user/ChenckIn")
    @FormUrlEncoded
    Flowable<BaseResultModel> chenckIn(@Header("token") String token, @FieldMap Map<String, String> param);

}
