package com.sz.ljs.warehousing.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.CountryModel;
import com.sz.ljs.warehousing.model.CustomerModel;
import com.sz.ljs.warehousing.model.GsonIncidentalModel;
import com.sz.ljs.warehousing.model.ProductModel;
import com.sz.ljs.warehousing.model.SelectCurrentDayBatchModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 入库请求接口处理类
 */
public class WarehouPresenter {

    private WarehouContract warehouContract;

    public WarehouPresenter(){
        warehouContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(WarehouContract.class);
    }
    public void release(){
        warehouContract = null;
    }

    //TODO 获取杂费项
    public Flowable<GsonIncidentalModel> getIncidental(){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        return warehouContract.getIncidental(param);
    }

    //TODO 查询国家
    public Flowable<CountryModel> getCountry(){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.getCountry(token,requestBody);
    }

    //TODO 查询生效得销售产品
    public Flowable<ProductModel> getProduct(){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.getProduct(token,requestBody);
    }

    //TODO 根据客户代码或者客户名称查询客户资料 Customer_code:客户代码(必填) customer_name:客户名称(选填)
    public Flowable<CustomerModel> getCustomer(String Customer_code,String customer_name){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        param.put(WarehouContract.CUSTOMER_CODE, Customer_code);
        param.put(WarehouContract.CUSTOMER_NAME, customer_name);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.getCustomer(token,requestBody);
    }

    //TODO 入库时选择客户生成到货总单 customer_id:客户id  customer_code:客户代码  userId:员工id   og_id:机构id 深圳、广州  og_short_code:地区简码
    public Flowable<SelectCurrentDayBatchModel> selectCurrentDayBatch(String customer_id,String customer_code,String userId,String og_id,String og_short_code){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        param.put(WarehouContract.CUSTOMER_ID, customer_id);
        param.put(WarehouContract.CUSTOMER_CODE, customer_code);
        param.put(WarehouContract.USERID, userId);
        param.put(WarehouContract.OG_ID, og_id);
        param.put(WarehouContract.OG_SHORT_CODE, og_short_code);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.selectCurrentDayBatch(token,requestBody);
    }

    //TODO 查询材积重、计费重 grossweight:重量  length:长  width:宽  height:高  product_code：产品代码(选填)  country_code:国家简码(选填)  arrival_date:到货时间  customer_id:客户id
    public Flowable<CalculationVolumeWeightModel> calculationVolumeWeight(String grossweight,String length,String width,String height
            ,String product_code,String country_code,String arrival_date,String customer_id){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        param.put(WarehouContract.GROSSWEIGHT, grossweight);
        param.put(WarehouContract.LENGTH, length);
        param.put(WarehouContract.WIDTH, width);
        param.put(WarehouContract.HEIGHT, height);
        param.put(WarehouContract.PRODUCT_CODE, product_code);
        param.put(WarehouContract.COUNTRY_CODE, country_code);
        param.put(WarehouContract.ARRIVAL_DATE, arrival_date);
        param.put(WarehouContract.CUSTOMER_ID, customer_id);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.calculationVolumeWeight(token,requestBody);
    }
}
