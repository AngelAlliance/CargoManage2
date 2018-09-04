package com.ljs.examinegoods.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ljs.examinegoods.contract.ExamineGoodsContract;
import com.ljs.examinegoods.model.DetectionByModel;
import com.ljs.examinegoods.model.ExamineGoodsModel;
import com.ljs.examinegoods.model.ItemTypeModel;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.model.OrderModel;
import com.ljs.examinegoods.model.SaveDeteTionOrderRequestModel;
import com.ljs.examinegoods.model.SaveDetecTionOrderResultModel;
import com.ljs.examinegoods.model.UploadFileResultModel;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;

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
 * Created by liujs on 2018/8/20.
 */

public class ExamineGoodsPresenter implements ExamineGoodsContract.Presenter {

    private ExamineGoodsContract.View mContract;

    public ExamineGoodsPresenter(ExamineGoodsContract.View view) {
        mContract = view;
    }

    public void release() {
        mContract = null;
    }

    //TODO 根据订单号查询订单信息
    public void getOrderByNumber(String number) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ExamineGoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ExamineGoodsContract.NUMBER, number);
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_ORDERBY_NUMBER, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetOrderByNumber(result);
                            mContract.onResult(ExamineGoodsContract.GETORDERBYNUMBER_SUCCESS, message);
                        } else {
                            mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetOrderByNumber(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        OrderModel orderModel = gson.fromJson(result, OrderModel.class);
        if (null != orderModel && null != orderModel.getData()) {
            ExamineGoodsModel.getInstance().setOrderModel(orderModel);
        }
    }

    //TODO 查询所有得货物类型
    public void getItemType() {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ExamineGoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_ITEM_TYPE, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetItemType(result);
                            mContract.onResult(ExamineGoodsContract.GET_ITEM_TYPE_SUCCESS, message);
                        } else {
                            mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetItemType(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        ItemTypeModel itemTypeModel = gson.fromJson(result, ItemTypeModel.class);
        if (null != itemTypeModel && null != itemTypeModel.getData() && itemTypeModel.getData().size() > 0) {
            ExamineGoodsModel.getInstance().setItemTypeList(itemTypeModel.getData());
        }
    }

    //TODO 根据货物类型差检查项  detection_name:货物类型中文名称
    public void getDetectionBy(String detection_name) {
        Map<String, String> param = new HashMap<>();
        param.put(ExamineGoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        param.put(ExamineGoodsContract.DETECTIONNAME, detection_name);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        HttpUtils.post(GenApi.URL + ApiUrl.GET_DETECTION_BY, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handGetDetection(result);
                            mContract.onResult(ExamineGoodsContract.GET_DETECTIONBY_SUCCESS, message);
                        } else {
                            mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handGetDetection(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        DetectionByModel detectionByModel = gson.fromJson(result, DetectionByModel.class);
        if (null != detectionByModel && null != detectionByModel.getData() && detectionByModel.getData().size() > 0) {
            ExamineGoodsModel.getInstance().setDetectionList(detectionByModel.getData());
        }

    }

    //TODO 添加问题件或者保存验货结果
    public void saveDetecTionOrder(SaveDeteTionOrderRequestModel requestModel) {
        Map<String, String> param = new HashMap<>();
        param.put(ExamineGoodsContract.NUMBER, requestModel.getNumber());
        if (TextUtils.isEmpty(requestModel.getReference_number())) {
            param.put(ExamineGoodsContract.REFERENCE_NUMBER, "");
        } else {
            param.put(ExamineGoodsContract.REFERENCE_NUMBER, requestModel.getReference_number());
        }
        param.put(ExamineGoodsContract.REQUEST_TYPE, requestModel.getRequest_type());
        if (TextUtils.isEmpty(requestModel.getDetection_note())) {
            param.put(ExamineGoodsContract.DETECTION_NOTE, "");
        } else {
            param.put(ExamineGoodsContract.DETECTION_NOTE, requestModel.getDetection_note());
        }
        if (null == requestModel.getImage_url() || requestModel.getImage_url().size() <= 0) {
            param.put(ExamineGoodsContract.IMAGE_LIST, "");
        } else {
            param.put(ExamineGoodsContract.IMAGE_LIST, new Gson().toJson(requestModel.getImage_url()));
        }
        param.put(ExamineGoodsContract.ORDER_ID, requestModel.getOrder_id());
        param.put(ExamineGoodsContract.USERID, requestModel.getUserId());
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        if (TextUtils.isEmpty(requestModel.getQuest_note())) {
            param.put(ExamineGoodsContract.QUEST_NOTE, "");
        } else {
            param.put(ExamineGoodsContract.QUEST_NOTE, requestModel.getQuest_note());
        }
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        HttpUtils.post(GenApi.URL + ApiUrl.SAVE_DETECTION, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        String rData = jsonObject.getString("Data");
                        if (1 == result_type) {
                            mContract.onResult(ExamineGoodsContract.SAVE_DETECTIONORDER_SUCCESS, rData);
                        } else {
                            mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    //TODO 图片上传
    public void uploadFile(String imageUrl) {
        Map<String, String> param = new HashMap<>();
        param.put(ExamineGoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ExamineGoodsContract.HEXADECIMAL_STR, imageUrl);
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        HttpUtils.post(GenApi.URL + ApiUrl.UPLOAD_FILE, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handUploadFile(result);
                            mContract.onResult(ExamineGoodsContract.UPLOAD_FILE_SUCCESS, message);
                        } else {
                            mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ExamineGoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handUploadFile(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        UploadFileResultModel model = gson.fromJson(result, UploadFileResultModel.class);
        if (null != model && !TextUtils.isEmpty(model.getData())) {
            ExamineGoodsModel.getInstance().setFileResultModel(model);
        }
    }
}
