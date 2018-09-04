package com.sz.ljs.articlescan.presenter;

import com.google.gson.Gson;
import com.sz.ljs.articlescan.contract.ArticleScanContract;
import com.sz.ljs.articlescan.model.ArticleScanModel;
import com.sz.ljs.articlescan.model.GsonOrgServerModel;
import com.sz.ljs.articlescan.model.GsonSelectShipmentBagReceiveModel;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArticleScanPresenter implements ArticleScanContract.Presenter {
    private ArticleScanContract.View mContract;

    public ArticleScanPresenter(ArticleScanContract.View view) {
        mContract = view;
    }

    //TODO 到件扫描初始化数据接口  arrival_date:到件时间  from_og_id:上一站  server_channelid:服务渠道
    @Override
    public void SelectShipmentBagReceive(String arrival_date, String from_og_id, String server_channelid) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ArticleScanContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        param.put("to_og_id", "" + UserModel.getInstance().getOg_id());
        param.put("arrival_date", arrival_date);
        param.put("from_og_id", from_og_id);
        param.put("server_channelid", server_channelid);
        HttpUtils.post(GenApi.URL + ApiUrl.SELECT_SHIPMENT_BAGRECEIVE, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, error_msg);
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
                            handShipmentBagResult(result);
                            mContract.onResult(ArticleScanContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handShipmentBagResult(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonSelectShipmentBagReceiveModel model = gson.fromJson(result, GsonSelectShipmentBagReceiveModel.class);
        if (null != model && null != model.getData()) {
            ArticleScanModel.getInstance().setShipmentBagList(model.getData());
        }
    }

    //TODO 到件扫描 扫描 包编号就调用接口 strTb_id:包号码
    @Override
    public void TransportBatchBusinessReceipt(String strTb_id) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ArticleScanContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        param.put("OG_ShortName", UserModel.getInstance().getOg_cityenname());
        param.put("strTb_id", strTb_id);
        HttpUtils.post(GenApi.URL + ApiUrl.TRANSPORTBATCHBUSINESS_RECEIPT, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(ArticleScanContract.BUSINESS_RECEIPT_SUCCESS_ID, message);
                        } else {
                            mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });

    }


    //TODO 查看收货服务商跟机构
    @Override
    public void getOrgServer() {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ArticleScanContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_ORG_SERVER, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
//                    Log.i("查看收货服务商跟机构", "result=" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handOrgServer(result);
                            mContract.onResult(ArticleScanContract.GET_ORG_SERVER_SUCCESS, message);
                        } else {
                            mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, message);
                        }

                    } catch (JSONException e) {
                        mContract.onResult(ArticleScanContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handOrgServer(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonOrgServerModel gsonOrgServerModel = gson.fromJson(result, GsonOrgServerModel.class);
        if (null != gsonOrgServerModel && null != gsonOrgServerModel.getData()
                && null != gsonOrgServerModel.getData().getOrg_list() && gsonOrgServerModel.getData().getOrg_list().size() > 0) {
            ArticleScanModel.getInstance().setOrg_list(gsonOrgServerModel.getData().getOrg_list());
        }
        if (null != gsonOrgServerModel && null != gsonOrgServerModel.getData()
                && null != gsonOrgServerModel.getData().getServer_list() && gsonOrgServerModel.getData().getServer_list().size() > 0) {
            ArticleScanModel.getInstance().setServer_list(gsonOrgServerModel.getData().getServer_list());
        }

    }
}
