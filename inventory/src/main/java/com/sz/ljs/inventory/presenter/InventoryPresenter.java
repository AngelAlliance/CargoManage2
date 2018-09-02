package com.sz.ljs.inventory.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.GsonResponseHandler;
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
        HttpUtils.post(GenApi.URL + ApiUrl.FIND_EXPRESS_BYID, token, param, new GsonResponseHandler<FindExpressRuesltModel>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                resultMessage = error_msg;
                mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
            }

            @Override
            public void onSuccess(int statusCode, FindExpressRuesltModel result) {
                handDepltListResult(result);

            }
        });
    }


    //TODO 处理运单数据
    private void handDepltListResult(FindExpressRuesltModel result) {
        mContract.showWaiting(false);
        resultMessage = result.getMsg();
        try {
            int type = result.getCode();
            if (type == 1) {
                if (null != result.getData()) {
                    mContract.OnSuccess(InventoryContract.REQUEST_SUCCESS_ID);
                    if (null != result.getData().getExpressEntity()) {
                        result.getData().getExpressEntity().setIsSelect("true");
                        result.getData().getExpressEntity().setOrder_status("选中");
                        InventoryModel.getInstance().setExpress(result.getData().getExpressEntity());
                    }
                }
            } else {
                mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        HttpUtils.post(GenApi.URL + ApiUrl.ADD_EXPRESSS_TRACK, token, param, new GsonResponseHandler<BaseResultModel>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                resultMessage = error_msg;
                mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
            }

            @Override
            public void onSuccess(int statusCode, BaseResultModel result) {
                mContract.showWaiting(false);
                if (null != result) {
                    int type = result.getCode();
                    resultMessage = result.getMsg();
                    if (1 == type) {
                        mContract.OnSuccess(InventoryContract.ADD_EXPRESSS_TRACK_SUCCESS);
                    } else {
                        mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
                    }
                } else {
                    mContract.OnError(InventoryContract.REQUEST_FAIL_ID);
                }

            }
        });
    }


    public String getMessage() {
        return resultMessage;
    }
}
