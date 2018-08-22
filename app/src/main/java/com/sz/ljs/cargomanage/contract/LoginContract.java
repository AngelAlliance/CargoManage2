package com.sz.ljs.cargomanage.contract;

import com.sz.ljs.base.interfacecallback.IBaseView;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.model.ScanNumberLengModel;

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
 * Created by liujs on 2018/8/9.
 */

public interface LoginContract {
    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "pass_word";
    public static final String TIME_STAMP = "timeStamp"; //请求得时间戳
    public static final String SUMMARY = "summary";  //username+password+“zhbg_ips2018_cn”得MD532位小写加密
    @POST("user/UserLogin")
    @FormUrlEncoded
    Flowable<LoginModel> doLogin(@FieldMap Map<String, String> param);

    @POST("user/GetScanNumberLeng")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Flowable<ScanNumberLengModel> getScanNumberLeng(@Header("token") String token, @Body RequestBody route);
}
