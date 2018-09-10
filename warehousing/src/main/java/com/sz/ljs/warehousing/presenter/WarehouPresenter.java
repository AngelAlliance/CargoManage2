package com.sz.ljs.warehousing.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.ChenckInModel;
import com.sz.ljs.warehousing.model.ChenckInRequestModel;
import com.sz.ljs.warehousing.model.CountryModel;
import com.sz.ljs.warehousing.model.CustomerModel;
import com.sz.ljs.warehousing.model.GsonIncidentalModel;
import com.sz.ljs.warehousing.model.ProductModel;
import com.sz.ljs.warehousing.model.SelectCurrentDayBatchModel;
import com.sz.ljs.warehousing.model.WareHouSingModel;

import org.json.JSONException;
import org.json.JSONObject;

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
public class WarehouPresenter implements WarehouContract.Presenter {

    private WarehouContract.View warehouContract;

    public WarehouPresenter(WarehouContract.View view) {
        warehouContract = view;
    }

    public void release() {
        warehouContract = null;
    }

    //TODO 获取杂费项
    public void getIncidental() {
        warehouContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(WarehouContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_EXTRASERVICE, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetIncidental(result);
                            warehouContract.onResult(WarehouContract.GET_INCIDENTAL_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetIncidental(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonIncidentalModel model = gson.fromJson(result, GsonIncidentalModel.class);
        if (null != model && null != model.getData() && model.getData().size() > 0) {
            WareHouSingModel.getInstance().setIncidentalList(model.getData());
        }
    }

    //TODO 查询国家
    public void getCountry() {
        warehouContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(WarehouContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_GETCOUNTRY, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetCountry(result);
                            warehouContract.onResult(WarehouContract.GET_COUNTRY_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetCountry(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        CountryModel model = gson.fromJson(result, CountryModel.class);
        if (null != model && null != model.getData() && model.getData().size() > 0) {
            WareHouSingModel.getInstance().setCountryList(model.getData());
        }
    }

    //TODO 查询生效得销售产品
    public void getProduct(String country_code) {
        warehouContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(WarehouContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_GETPRODUCT, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetProduct(result);
                            warehouContract.onResult(WarehouContract.GET_PRODUCT_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetProduct(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        ProductModel model = gson.fromJson(result, ProductModel.class);
        if (null != model && null != model.getData() && model.getData().size() > 0) {
            WareHouSingModel.getInstance().setProductList(model.getData());
        }
    }

    //TODO 根据客户名称查询客户资料  customer_name:客户名称(必填)
    public void getCustomer(String customer_name) {
        warehouContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        param.put(WarehouContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(WarehouContract.CUSTOMER_NAME, customer_name);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        HttpUtils.post(GenApi.URL + ApiUrl.GET_GETCUSTOMER, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetCustomer(result);
                            warehouContract.onResult(WarehouContract.GET_CUSTOMER_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetCustomer(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        CustomerModel model = gson.fromJson(result, CustomerModel.class);
        if (null != model && null != model.getData() && model.getData().size() > 0) {
            WareHouSingModel.getInstance().setCustomerResultList(model.getData());
        }
    }

    //TODO 入库时选择客户生成到货总单 customer_id:客户id  customer_code:客户代码  userId:员工id   og_id:机构id 深圳、广州  og_short_code:地区简码
    public void selectCurrentDayBatch(String customer_id, String customer_code) {
        warehouContract.showWaiting(true);
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
        HttpUtils.post(GenApi.URL + ApiUrl.SELECT_CURRENTDAY_BATCH, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handSelectCurrentDayBatch(result);
                            warehouContract.onResult(WarehouContract.SELECTCURRENT_DAYBATCH, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handSelectCurrentDayBatch(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        SelectCurrentDayBatchModel model = gson.fromJson(result, SelectCurrentDayBatchModel.class);
        if (null != model && null != model.getData()) {
            WareHouSingModel.getInstance().setSelectCurrentDayBatchModel(model.getData());
        }
    }

    //TODO 查询材积重、计费重 grossweight:重量  length:长  width:宽  height:高  product_code：产品代码(选填)  country_code:国家简码(选填)  arrival_date:到货时间  customer_id:客户id
    public void calculationVolumeWeight(String grossweight, String length, String width, String height
            , String product_code, String country_code, String arrival_date, String customer_id) {
        warehouContract.showWaiting(true);
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
        HttpUtils.post(GenApi.URL + ApiUrl.CALCULATION_VOLUMEWEIGHT, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handCalculationVolumeWeight(result);
                            warehouContract.onResult(WarehouContract.CALCULATION_VOLUME_WEIGHT, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handCalculationVolumeWeight(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        CalculationVolumeWeightModel model = gson.fromJson(result, CalculationVolumeWeightModel.class);
        if (null != model && null != model.getData()) {
            WareHouSingModel.getInstance().setCalculationVolumWeightModel(model.getData());
        }
    }

    //TODO 根据订单号查询订单信息
    public void getOrderByNumber(String number) {
        warehouContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(WarehouContract.NUMBER, number);
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_ORDERBY_NUMBER, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetOrderByNumber(result);
                            warehouContract.onResult(WarehouContract.GET_ORDER_BY_NUMBER_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetOrderByNumber(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        OrderModel model = gson.fromJson(result, OrderModel.class);
        if (null != model && null != model.getData()) {
            WareHouSingModel.getInstance().setOrderModel(model);
        }
    }

    //TODO 入库接口
    public void chenckIn(ChenckInRequestModel requestModel) {
        warehouContract.showWaiting(true);
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
        HttpUtils.post(GenApi.URL + ApiUrl.CHENCK_IN, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                warehouContract.showWaiting(false);
                warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                warehouContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handChenckIn(result);
                            warehouContract.onResult(WarehouContract.CHENCK_IN_SUCCESS, message);
                        } else {
                            warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        warehouContract.onResult(WarehouContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handChenckIn(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        ChenckInModel model = gson.fromJson(result, ChenckInModel.class);
        if (null != model && null != model.getData()) {
            WareHouSingModel.getInstance().setChenckInResultModel(model.getData());
        }
    }
}
