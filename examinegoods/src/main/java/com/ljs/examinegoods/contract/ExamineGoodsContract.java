package com.ljs.examinegoods.contract;

import com.ljs.examinegoods.model.DetectionByModel;
import com.ljs.examinegoods.model.ItemTypeModel;
import com.sz.ljs.common.model.OrderModel;
import com.ljs.examinegoods.model.SaveDetecTionOrderResultModel;
import com.ljs.examinegoods.model.UploadFileResultModel;
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

    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");


    public static final String NUMBER = "number";   //运单号码
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密

    @POST("user/GetOrderbyNumber")
    @FormUrlEncoded
    Flowable<OrderModel> getOrderByNumber(@Header("token") String token, @FieldMap Map<String, String> param);
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @POST("user/GetOrderbyNumber")
    //    Flowable<OrderModel> getOrderByNumber(@Header("token") String token, @Body RequestBody route);

    @POST("user/GetItemType")
    @FormUrlEncoded
    Flowable<ItemTypeModel> getItemType(@FieldMap Map<String, String> param);


    public static final String DETECTIONNAME = "detection_name";

    @POST("user/GetDetectionBy")
    @FormUrlEncoded
    Flowable<DetectionByModel> getDetectionBy(@Header("token") String token,@FieldMap Map<String, String> param);

    public static final String REFERENCE_NUMBER = "reference_number";  //客户参考单号
    public static final String REQUEST_TYPE = "request_type";          //问题件还是不是问题件 Y问题件N不是问题件
    public static final String DETECTION_NOTE = "detection_note";      // 检测结果 如（带电，没有发票……….）
    public static final String IMAGE_URL = "image_url";               //图片集合 LISt<string>
    public static final String ORDER_ID = "order_id";                // 订单id
    public static final String USERID = "userId";                    //用户id
    @POST("user/SaveDetection")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<SaveDetecTionOrderResultModel> saveDetecTionOrder(@Header("token") String token,@Body RequestBody route);

    public static final String HEXADECIMAL_STR = "hexadecimal_str";                    //十六进制字符串

    @POST("user/UploadFile")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<UploadFileResultModel> uploadFile(@Header("token") String token, @Body RequestBody route);
}
