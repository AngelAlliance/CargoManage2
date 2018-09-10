package com.sz.ljs.warehousing.contract;

import com.sz.ljs.base.interfacecallback.IGenView;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.ChenckInModel;
import com.sz.ljs.warehousing.model.ChenckInRequestModel;
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

    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");//请求接口summary参数MD5加密原始数据模型
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密
    public static final String CUSTOMER_CODE = "customer_code";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String USERID = "userId";
    public static final String OG_ID = "og_id";
    public static final String OG_SHORT_CODE = "og_short_code";
    public static final String GROSSWEIGHT = "grossweight";
    public static final String LENGTH = "length";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String PRODUCT_CODE = "product_code";
    public static final String COUNTRY_CODE = "country_code";
    public static final String ARRIVAL_DATE = "arrival_date";
    public static final String NUMBER = "number";   //运单号码

    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int GET_INCIDENTAL_SUCCESS = 1;//获取杂费项
    public static final int GET_COUNTRY_SUCCESS = 2;//查询国家
    public static final int GET_PRODUCT_SUCCESS = 3;//查询生效得销售产品
    public static final int GET_CUSTOMER_SUCCESS = 4;//根据客户名称查询客户资料 customer_name:客户名称(必填)
    public static final int SELECTCURRENT_DAYBATCH = 5;//入库时选择客户生成到货总单
    public static final int CALCULATION_VOLUME_WEIGHT = 6; // 查询材积重、计费重
    public static final int GET_ORDER_BY_NUMBER_SUCCESS = 7;//根据订单号查询订单信息
    public static final int CHENCK_IN_SUCCESS = 8;//入库接口

    interface View extends IGenView {

    }

    interface Presenter {
        //TODO 获取杂费项
        public void getIncidental();

        //TODO 查询国家
        public void getCountry();

        //TODO 查询生效得销售产品
        public void getProduct(String country_code);

        //TODO 根据客户名称查询客户资料 customer_name:客户名称(必填)
        public void getCustomer(String customer_name);

        //TODO 入库时选择客户生成到货总单 customer_id:客户id  customer_code:客户代码  userId:员工id   og_id:机构id 深圳、广州  og_short_code:地区简码
        public void selectCurrentDayBatch(String customer_id, String customer_code);

        //TODO 查询材积重、计费重 grossweight:重量  length:长  width:宽  height:高  product_code：产品代码(选填)  country_code:国家简码(选填)  arrival_date:到货时间  customer_id:客户id
        public void calculationVolumeWeight(String grossweight, String length, String width, String height
                , String product_code, String country_code, String arrival_date, String customer_id);

        //TODO 根据订单号查询订单信息
        public void getOrderByNumber(String number);

        //TODO 入库接口
        public void chenckIn(ChenckInRequestModel requestModel);
    }

    @POST("user/GetExtraservice")
    @FormUrlEncoded
    Flowable<GsonIncidentalModel> getIncidental(@FieldMap Map<String, String> param);

    @POST("user/GetCountry")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//需要添加头
    Flowable<CountryModel> getCountry(@Header("token") String token, @Body RequestBody route);

    @POST("user/GetProduct")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//需要添加头
    Flowable<ProductModel> getProduct(@Header("token") String token, @Body RequestBody route);

    @POST("user/GetCustomer")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//需要添加头
    Flowable<CustomerModel> getCustomer(@Header("token") String token, @Body RequestBody route);

    @POST("user/SelectCurrentDayBatch")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//需要添加头
    Flowable<SelectCurrentDayBatchModel> selectCurrentDayBatch(@Header("token") String token, @Body RequestBody route);

    @POST("user/CalculationVolumeWeight")
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    Flowable<CalculationVolumeWeightModel> calculationVolumeWeight(@Header("token") String token, @Body RequestBody route);
    @FormUrlEncoded
    Flowable<CalculationVolumeWeightModel> calculationVolumeWeight(@Header("token") String token, @FieldMap Map<String, String> param);

    @POST("user/GetOrderbyNumber")
    @FormUrlEncoded
    Flowable<OrderModel> getOrderByNumber(@Header("token") String token, @FieldMap Map<String, String> param);


    @POST("user/ChenckIn")
    @FormUrlEncoded
    Flowable<ChenckInModel> chenckIn(@Header("token") String token, @FieldMap Map<String, String> param);

}
