package com.sz.ljs.packgoods.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.packgoods.contract.PackgoodsContract;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.packgoods.model.BagPutBusinessReqModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;
import com.sz.ljs.packgoods.model.PackGoodsModel;
import com.sz.ljs.packgoods.model.PackGoodsRequestBsListMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackgoodsPresenter implements PackgoodsContract.Presenter {
    private PackgoodsContract.View mContract;

    public PackgoodsPresenter(PackgoodsContract.View view) {
        mContract = view;
    }

    public void release() {
        mContract = null;
    }


    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    public void getDepltList(String og_id, String service_id, String server_channelid) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.SERVRE_ID, service_id);
        param.put(PackgoodsContract.SERVRE_CHANNELID, server_channelid);
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_DEPLT_LIST, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            handDepltList(result);
                            mContract.onResult(PackgoodsContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handDepltList(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonDepltListModel gsonDepltListModel = gson.fromJson(result, GsonDepltListModel.class);
        if (null != gsonDepltListModel && null != gsonDepltListModel.getData()
                && null != gsonDepltListModel.getData().getBaleList()
                && gsonDepltListModel.getData().getBaleList().size() > 0) {
            PackGoodsModel.getInstance().setBaleList(gsonDepltListModel.getData().getBaleList());
        }
        if (null != gsonDepltListModel && null != gsonDepltListModel.getData()
                && null != gsonDepltListModel.getData().getShppingCnList()
                && gsonDepltListModel.getData().getShppingCnList().size() > 0) {
            PackGoodsModel.getInstance().setShppingCnList(gsonDepltListModel.getData().getShppingCnList());
        }
    }

    //TODO 把运单从某个包提出 listParamString:运单的集合 json字符串
    public void bagPutBusiness(List<BagPutBusinessReqModel> listParamString) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.LIST_PARAMSTRING, new Gson().toJson(listParamString));
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.BAG_PUT_BUSINESS, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(PackgoodsContract.BAG_PUTBUSINESS_SUCCESS, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    //TODO 称量包的重量  strBagCode:包号码   og_id:机构id  strlength:长  strwidth:宽  strheight:高  txtWeight:称重的重量  txtbag_grossweight:包重量
    public void bagWeighing(String strBagCode, String og_id, String strlength, String strwidth
            , String strheight, String txtWeight, String txtbag_grossweight) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.STR_BAG_CODE, strBagCode);
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.STR_LENGTH, strlength);
        param.put(PackgoodsContract.STR_WIDTH, strwidth);
        param.put(PackgoodsContract.STR_HEIGHT, strheight);
        param.put(PackgoodsContract.TEXT_WEIGHT, txtWeight);
        param.put(PackgoodsContract.TEXTBAG_GROSSWEIGHT, txtbag_grossweight);
        HttpUtils.post(GenApi.URL + ApiUrl.BAG_WEIFHING, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(PackgoodsContract.BAG_WEIGHING_SUCCESS, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    //TODO 生效渠道
    public void getServiceChannel() {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        HttpUtils.post(GenApi.URL + ApiUrl.GET_SERVICECHANNEL, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            handServiceChannelResult(result);
                            mContract.onResult(PackgoodsContract.GET_SERVICECHANNEL_SUCCESS, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handServiceChannelResult(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonServiceChannelModel gsonServiceChannelModel = gson.fromJson(result, GsonServiceChannelModel.class);
        if (null != gsonServiceChannelModel && null != gsonServiceChannelModel.getData()
                && gsonServiceChannelModel.getData().size() > 0) {
            PackGoodsModel.getInstance().setServiceChannelList(gsonServiceChannelModel.getData());
        }
    }


    //TODO 运单打包   strExpressCode:包的号码 例如：PPNO-9908    og_id:机构id 登陆时返回  server_channelid:服务渠道   list:运单集合
    public void addBussinessPackage(String strExpressCode, String og_id
            , String server_channelid, List<PackGoodsRequestBsListMode> list) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.STR_EXPRESS_CODE, strExpressCode);
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.SERVRE_CHANNELID, server_channelid);
        param.put(PackgoodsContract.BS_LIST, new Gson().toJson(list));
        HttpUtils.post(GenApi.URL + ApiUrl.ADD_BUSSINESS_PACKAGE, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            handBussinessPackageResult(result);
                            mContract.onResult(PackgoodsContract.ADD_BUSSINESSPACKAGE_SUCCESS, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }

    private void handBussinessPackageResult(String result) {
        Gson gson = HDateGsonAdapter.createGson();
        GsonAddBussinessPackageModel model = gson.fromJson(result, GsonAddBussinessPackageModel.class);
        if (null != model && null != model.getData()) {
            PackGoodsModel.getInstance().setBussinessPackageModel(model.getData());
        }

    }

    //TODO 拆包  bag_labelcode:包号码  og_id:机构id
    public void unpacking(String bag_labelcode, String og_id) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.BAG_LABEL_CODE, bag_labelcode);
        param.put(PackgoodsContract.OG_ID, og_id);
        HttpUtils.post(GenApi.URL + ApiUrl.UNPACKING, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(PackgoodsContract.UNPACKING_SUCCESS, message);
                        } else {
                            mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(PackgoodsContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }
}
