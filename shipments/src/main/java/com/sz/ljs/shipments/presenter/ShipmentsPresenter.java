package com.sz.ljs.shipments.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.shipments.contract.ShipmentsContract;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.GsonSaveTransportBatchAndBusinessModel;
import com.sz.ljs.shipments.model.GsonServiceChannelModel;
import com.sz.ljs.shipments.model.ShipMentsModel;
import com.sz.ljs.shipments.model.TransportBatchBusinessParamModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipmentsPresenter implements ShipmentsContract.Presenter {
    private ShipmentsContract.View mContract;

    public ShipmentsPresenter(ShipmentsContract.View view) {
        mContract = view;
    }

    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    @Override
    public void getDepltList(String og_id, String service_id, String server_channelid) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.OG_ID, og_id);
        param.put(ShipmentsContract.SERVRE_ID, service_id);
        param.put(ShipmentsContract.SERVRE_CHANNELID, server_channelid);
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_DEPLT_LIST, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
//                    Log.i("获取打包页面初始化数据", "result=" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handDepltListResult(result);
                            mContract.onResult(ShipmentsContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handDepltListResult(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonDepltListModel gsonDepltListModel = gson.fromJson(result, GsonDepltListModel.class);
        if (null != gsonDepltListModel && null != gsonDepltListModel.getData()
                && null != gsonDepltListModel.getData().getBaleList()
                && gsonDepltListModel.getData().getBaleList().size() > 0) {
            ShipMentsModel.getInstance().setBaleList(gsonDepltListModel.getData().getBaleList());
        }
        if (null != gsonDepltListModel && null != gsonDepltListModel.getData()
                && null != gsonDepltListModel.getData().getShppingCnList()
                && gsonDepltListModel.getData().getShppingCnList().size() > 0) {
            ShipMentsModel.getInstance().setShppingCnList(gsonDepltListModel.getData().getShppingCnList());
        }

    }

    //TODO 生效渠道
    @Override
    public void getServiceChannel() {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_SERVICE_CHANNEL, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                mContract.showWaiting(false);
                if (null != result) {
//                    Log.i("生效渠道", "result=" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handServiceChannel(result);
                            mContract.onResult(ShipmentsContract.GET_SERVICE_CHANNEL_SUCCESS, message);
                        } else {
                            mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, message);
                        }

                    } catch (JSONException e) {
                        mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handServiceChannel(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonServiceChannelModel gsonServiceChannelModel = gson.fromJson(result, GsonServiceChannelModel.class);
        if (null != gsonServiceChannelModel && null != gsonServiceChannelModel.getData()
                && gsonServiceChannelModel.getData().size() > 0) {
            ShipMentsModel.getInstance().setServiceChannelList(gsonServiceChannelModel.getData());
        }
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
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_ORG_SERVER, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(ShipmentsContract.GET_ORG_SERVER_SUCCESS, message);
                        } else {
                            mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, message);
                        }

                    } catch (JSONException e) {
                        mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, e.getMessage());
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
            ShipMentsModel.getInstance().setOrg_list(gsonOrgServerModel.getData().getOrg_list());
        }
        if (null != gsonOrgServerModel && null != gsonOrgServerModel.getData()
                && null != gsonOrgServerModel.getData().getServer_list() && gsonOrgServerModel.getData().getServer_list().size() > 0) {
            ShipMentsModel.getInstance().setServer_list(gsonOrgServerModel.getData().getServer_list());
        }

    }

    //TODO 出库生成主单 forecast_arrival_date:生成时间  to_og_id:下一站机构id  to_server_id:收货服务商id 数据在查询服务商接口  to_server_code:收货服务商代码  list:运单包集合
    @Override
    public void saveTransportBatchAndBusiness(String forecast_arrival_date, String to_og_id, String to_server_id, String to_server_code, List<TransportBatchBusinessParamModel> list) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ShipmentsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ShipmentsContract.SUMMARY, ShipmentsContract.summary);
        param.put("forecast_arrival_date", forecast_arrival_date);
        param.put("from_og_code", UserModel.getInstance().getOg_shortcode());
        param.put("from_og_id", "" + UserModel.getInstance().getOg_id());
        param.put("to_og_id", to_og_id);
        param.put("to_server_id", to_server_id);
        param.put("to_server_code", to_server_code);
        param.put("TransportBatchBusinessParam", new Gson().toJson(list));
        Log.i("生成主单请求参数", "param=" + param);
        HttpUtils.post(GenApi.URL + ApiUrl.SAVE_TRANSPORTBATCH_AND_BUSINESS, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, error_msg);
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
                            handTransportBatchAndBusiness(result);
                            mContract.onResult(ShipmentsContract.SAVE_TRANSPORTBATCH_AND_BUSINESS_SUCCESS, message);
                        }else {
                            mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, message);
                        }

                    } catch (JSONException e) {
                        mContract.onResult(ShipmentsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }


    private void handTransportBatchAndBusiness(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonSaveTransportBatchAndBusinessModel model = gson.fromJson(result, GsonSaveTransportBatchAndBusinessModel.class);
        if (null != model && null != model.getData()) {
            ShipMentsModel.getInstance().setBusinessDataBean(model.getData());
        }
    }


}
