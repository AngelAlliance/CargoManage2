package com.sz.ljs.inventory.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.GsonResponseHandler;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.base.IResponseHandler;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.inventory.contract.InventoryContract;
import com.sz.ljs.inventory.model.BsListModel;
import com.sz.ljs.inventory.model.FindExpressRuesltModel;
import com.sz.ljs.inventory.model.InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Author: Mr. Duan
 * Date: 2018/8/29
 * Description:盘库 P
 */

public class InventoryPresenter implements InventoryContract.Presenter {
    private final InventoryContract.View mContract;
    private String resultMessage = "";

    public InventoryPresenter(InventoryContract.View view) {
        mContract = view;
    }

    //TODO 根据订单号查询订单信息
    @Override
    public void getFindExpressByID(String number) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ApiUrl.NUMBER, number);
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        param.put(ApiUrl.USERID, "" + UserModel.getInstance().getSt_id());
        HttpUtils.post(GenApi.URL + ApiUrl.FIND_EXPRESS_BYID, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                resultMessage = error_msg;
                mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        resultMessage = message;
                        if (1 == result_type) {
                            handDepltListResult(result);
                            mContract.OnSuccess(InventoryContract.REQUEST_SUCCESS_ID);
                        }
                    } catch (JSONException e) {
                        resultMessage =  e.getMessage();
                        mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
                    }
                }
            }
        });
    }


    //TODO 处理运单数据
    private void handDepltListResult(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        FindExpressRuesltModel model=gson.fromJson(result,FindExpressRuesltModel.class);
        if (null!=model&&null != model.getData()) {
            if (null != model.getData().getExpressEntity()) {
                model.getData().getExpressEntity().setIsSelect("true");
                model.getData().getExpressEntity().setOrder_status("选中");
                InventoryModel.getInstance().setExpress(model.getData().getExpressEntity());
            }
        }
    }

    //TODO 批量提交盘货数据
    @Override
    public void addExpressTrack(List<BsListModel> list) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        param.put(ApiUrl.USERID, "" + UserModel.getInstance().getSt_id());
        param.put("og_en_name", "" + UserModel.getInstance().getOg_cityenname());
        param.put("BsListString", new Gson().toJson(list));
        HttpUtils.post(GenApi.URL + ApiUrl.ADD_EXPRESSS_TRACK, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                resultMessage = error_msg;
                mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        resultMessage = message;
                        if (1 == result_type) {
                            mContract.OnSuccess(InventoryContract.ADD_EXPRESSS_TRACK_SUCCESS);
                        }
                    } catch (JSONException e) {
                        resultMessage =  e.getMessage();
                        mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
                    }
                }
            }
        });
    }


    public String getMessage() {
        return resultMessage;
    }
}
