package com.sz.ljs.setting.presenter;

import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.setting.contract.SettingContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mContract;

    public SettingPresenter(SettingContract.View view){
        mContract=view;
    }

    @Override
    public void updatePassword(String old_password, String confim_password, String new_password) {
        mContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(SettingContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(ApiUrl.SUMMARY, ApiUrl.summary);
        param.put("old_password", old_password);
        param.put("confim_password", confim_password);
        param.put("new_password", new_password);
        HttpUtils.post(GenApi.URL + ApiUrl.UPDATE_PASSWORD, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mContract.showWaiting(false);
                mContract.onResult(SettingContract.REQUEST_FAIL_ID, error_msg);
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
                            mContract.onResult(SettingContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            mContract.onResult(SettingContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        mContract.onResult(SettingContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }

            }
        });
    }
}
