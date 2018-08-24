package com.sz.ljs.warehousing.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.ChenckInRequestModel;
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

    public WarehouPresenter() {
        warehouContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(WarehouContract.class);
    }

    public void release() {
        warehouContract = null;
    }

    //TODO 获取杂费项
    public Flowable<GsonIncidentalModel> getIncidental() {
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        return warehouContract.getIncidental(param);
    }

    //TODO 查询国家
    public Flowable<CountryModel> getCountry() {
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
        return warehouContract.getCountry(token, requestBody);
    }

    //TODO 查询生效得销售产品
    public Flowable<ProductModel> getProduct() {
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
        return warehouContract.getProduct(token, requestBody);
    }

    //TODO 根据客户代码或者客户名称查询客户资料 Customer_code:客户代码(必填) customer_name:客户名称(选填)
    public Flowable<CustomerModel> getCustomer(String Customer_code, String customer_name) {
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
        return warehouContract.getCustomer(token, requestBody);
    }

    //TODO 入库时选择客户生成到货总单 customer_id:客户id  customer_code:客户代码  userId:员工id   og_id:机构id 深圳、广州  og_short_code:地区简码
    public Flowable<SelectCurrentDayBatchModel> selectCurrentDayBatch(String customer_id, String customer_code) {
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        param.put(WarehouContract.CUSTOMER_ID, customer_id);
        param.put(WarehouContract.CUSTOMER_CODE, customer_code);
        param.put(WarehouContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(WarehouContract.OG_ID, "" + UserModel.getInstance().getOg_id());
        param.put(WarehouContract.OG_SHORT_CODE, "" + UserModel.getInstance().getOg_shortcode());
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        String json = new Gson().toJson(param);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return warehouContract.selectCurrentDayBatch(token, requestBody);
    }

    //TODO 查询材积重、计费重 grossweight:重量  length:长  width:宽  height:高  product_code：产品代码(选填)  country_code:国家简码(选填)  arrival_date:到货时间  customer_id:客户id
    public Flowable<CalculationVolumeWeightModel> calculationVolumeWeight(String grossweight, String length, String width, String height
            , String product_code, String country_code, String arrival_date, String customer_id) {
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
        return warehouContract.calculationVolumeWeight(token, param);
    }

    //TODO 根据订单号查询订单信息
    public Flowable<OrderModel> getOrderByNumber(String number) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(WarehouContract.NUMBER, number);
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
//        String json= new Gson().toJson(param);
//        RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        return warehouContract.getOrderByNumber(token, param);
    }

    //TODO 入库接口
    public Flowable<BaseResultModel> chenckIn(ChenckInRequestModel requestModel) {
        Map<String, String> param = new HashMap<>();
        if (!TextUtils.isEmpty(requestModel.getOrder_id())) {
            param.put("order_id", requestModel.getOrder_id());
        } else {
            param.put("order_id", "");
        }
        if (!TextUtils.isEmpty(requestModel.getShipper_number())) {
            param.put("shipper_number", requestModel.getShipper_number());
        } else {
            param.put("shipper_number", "");
        }
        if (!TextUtils.isEmpty(requestModel.getReference_number())) {
            param.put("reference_number", requestModel.getReference_number());
        } else {
            param.put("reference_number", "");
        }
        if (!TextUtils.isEmpty(requestModel.getCustomer_id())) {
            param.put("customer_id", requestModel.getCustomer_id());
        } else {
            param.put("customer_id", "");
        }
        if (!TextUtils.isEmpty(requestModel.getPk_code())) {
            param.put("pk_code", requestModel.getPk_code());
        } else {
            param.put("pk_code", "");
        }
        if (!TextUtils.isEmpty(requestModel.getCountry_code())) {
            param.put("country_code", requestModel.getCountry_code());
        } else {
            param.put("country_code", "");
        }
        if (!TextUtils.isEmpty(requestModel.getArrivalbatch_id())) {
            param.put("arrivalbatch_id", requestModel.getArrivalbatch_id());
        } else {
            param.put("arrivalbatch_id", "");
        }
        if (!TextUtils.isEmpty(requestModel.getArrival_date())) {
            param.put("arrival_date", requestModel.getArrival_date());
        } else {
            param.put("arrival_date", "");
        }
        if (!TextUtils.isEmpty(requestModel.getCustomer_channelid())) {
            param.put("customer_channelid", requestModel.getCustomer_channelid());
        } else {
            param.put("customer_channelid", "");
        }
        if (!TextUtils.isEmpty(requestModel.getCheckin_og_id())) {
            param.put("checkin_og_id", requestModel.getCheckin_og_id());
        } else {
            param.put("checkin_og_id", "");
        }
        if (!TextUtils.isEmpty(requestModel.getShipper_weight())) {
            param.put("shipper_weight", requestModel.getShipper_weight());
        } else {
            param.put("shipper_weight", "");
        }
        if (!TextUtils.isEmpty(requestModel.getShipper_pieces())) {
            param.put("shipper_pieces", requestModel.getShipper_pieces());
        } else {
            param.put("shipper_pieces", "");
        }
        if (!TextUtils.isEmpty(requestModel.getLen())) {
            param.put("Len", requestModel.getLen());
        } else {
            param.put("Len", "");
        }
        if (!TextUtils.isEmpty(requestModel.getHeight())) {
            param.put("Height", requestModel.getHeight());
        } else {
            param.put("Height", "");
        }
        if (!TextUtils.isEmpty(requestModel.getWidth())) {
            param.put("Width", requestModel.getWidth());
        } else {
            param.put("Width", "");
        }
        if (!TextUtils.isEmpty(requestModel.getUser_Name())) {
            param.put("User_Name", requestModel.getUser_Name());
        } else {
            param.put("User_Name", "");
        }
        if (!TextUtils.isEmpty(requestModel.getOG_CityEnName())) {
            param.put("OG_CityEnName", requestModel.getOG_CityEnName());
        } else {
            param.put("OG_CityEnName", "");
        }
        if (!TextUtils.isEmpty(requestModel.getCargoVolumes())) {
            param.put("CargoVolumes", requestModel.getCargoVolumes());
        } else {
            param.put("CargoVolumes", "");
        }
        if (!TextUtils.isEmpty(requestModel.getM_lstExtraService())) {
            param.put("m_lstExtraService", requestModel.getM_lstExtraService());
        } else {
            param.put("m_lstExtraService", "");
        }
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        return warehouContract.chenckIn(token, param);
    }
}
