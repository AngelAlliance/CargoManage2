package com.sz.ljs.cargomanage.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sz.ljs.articlescan.contract.ArticleScanContract;
import com.sz.ljs.cargomanage.contract.LoginContract;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.model.ScanNumberLengModel;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.base.IHttpUtilsCallBack;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.constant.ApiUrl;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.HttpUtils;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujs on 2018/8/9.
 * 登录相关业务
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View loginContract;
    public LoginPresenter(LoginContract.View view){
        loginContract =view;
    }

    public void release(){
        loginContract = null;
    }

    public void doLogin(final String userName,final String pwd){
        loginContract.showWaiting(true);
        Map<String, String> param = new HashMap<>();
        param.put(LoginContract.USER_NAME,userName);
        param.put(LoginContract.PASS_WORD,pwd);
        param.put(LoginContract.TIME_STAMP, ApiTimestampToken.getTimestampString());
        param.put(LoginContract.SUMMARY, MD5Util.get32MD5Str(userName+pwd+"zhbg_ips2018_cn"));
        HttpUtils.post(GenApi.URL + ApiUrl.USER_LOGIN, "", param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                loginContract.showWaiting(false);
                loginContract.onResult(LoginContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                loginContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        if (1 == result_type) {
                            handLoginResult(result,userName,pwd);
                            loginContract.onResult(LoginContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            loginContract.onResult(LoginContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        loginContract.onResult(LoginContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });
    }

    private void handLoginResult(String result,final String userName,final String pwd){
        Gson gson = HDateGsonAdapter.createGson();
        LoginModel model = gson.fromJson(result, LoginModel.class);
        if (null != model && null != model.getData() && null != model.getData().getUserModel()) {
            LoginModel.DataEntity dataEntity = model.getData();
            UserModel.getInstance().setAuthentication_code(dataEntity.getUserModel().getAuthentication_code());
            UserModel.getInstance().setTms_id(dataEntity.getUserModel().getTms_id());
            UserModel.getInstance().setSt_id_ctreate(dataEntity.getUserModel().getSt_id_ctreate());
            UserModel.getInstance().setSp_id(dataEntity.getUserModel().getSp_id());
            UserModel.getInstance().setSt_ename(dataEntity.getUserModel().getSt_ename());
            UserModel.getInstance().setSt_name(dataEntity.getUserModel().getSt_name());
            UserModel.getInstance().setCompetence_og_id(dataEntity.getUserModel().getCompetence_og_id());
            UserModel.getInstance().setOg_id(dataEntity.getUserModel().getOg_id());
            UserModel.getInstance().setVs_code(dataEntity.getUserModel().getVs_code());
            UserModel.getInstance().setSt_code(dataEntity.getUserModel().getSt_code());
            UserModel.getInstance().setSt_id(dataEntity.getUserModel().getSt_id());
            UserModel.getInstance().setDing_user_id(dataEntity.getUserModel().getDing_user_id());
            UserModel.getInstance().setOg_shortcode(dataEntity.getUserModel().getOg_shortcode());
            UserModel.getInstance().setOg_cityenname(dataEntity.getUserModel().getOg_cityenname());
            UserModel.TokenModelEntity tokenModelEntity = new UserModel.TokenModelEntity();
            tokenModelEntity.setCreate_date(dataEntity.getTokenModel().getCreate_date());
            tokenModelEntity.setFailure_time(dataEntity.getTokenModel().getFailure_time());
            tokenModelEntity.setId(dataEntity.getTokenModel().getId());
            tokenModelEntity.setToken(dataEntity.getTokenModel().getToken());
            tokenModelEntity.setSt_id(dataEntity.getTokenModel().getSt_id());
            UserModel.getInstance().setTokenModel(tokenModelEntity);
            List<UserModel.PermissionEntity> list = new ArrayList<UserModel.PermissionEntity>();
            for (LoginModel.DataEntity.PermissionEntity models : dataEntity.getPermission()) {
                UserModel.PermissionEntity permissionEntity = new UserModel.PermissionEntity();
                permissionEntity.setMi_ename(models.getMi_ename());
                permissionEntity.setMi_name(models.getMi_name());
                list.add(permissionEntity);
            }
            UserModel.getInstance().setPermission(list);
            UserModel.getInstance().setLocalSaveUserNo(userName);
            UserModel.getInstance().setLocalSavePassword(pwd);
            ApiTimestampToken.setToken(dataEntity.getTokenModel().getToken());
            ApiTimestampToken.setUserID(dataEntity.getUserModel().getSt_id());
        }
    }

    //TODO 返回输入运单N位得时候调用接口
    public void getScanNumberLeng(){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, WarehouContract.summary);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        HttpUtils.post(GenApi.URL + ApiUrl.GET_SCANNUMBER_LENG, token, param, new IHttpUtilsCallBack() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                loginContract.showWaiting(false);
                loginContract.onResult(LoginContract.REQUEST_FAIL_ID, error_msg);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                loginContract.showWaiting(false);
                if (null != result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int result_type = jsonObject.getInt(GenApi.JSON_KEY_TYPE);
                        String message = jsonObject.getString(GenApi.JSON_KEY_MESSAGE);
                        String data=jsonObject.getString("Data");
                        if (1 == result_type) {
                            if (!TextUtils.isEmpty(data)) {
                                Log.i("请求运单号位数", "length=" + data);
                                GenApi.ScanNumberLeng = Integer.parseInt(data);
                            }
                            loginContract.onResult(LoginContract.REQUEST_SUCCESS_ID, message);
                        } else {
                            loginContract.onResult(LoginContract.REQUEST_FAIL_ID, message);
                        }
                    } catch (JSONException e) {
                        loginContract.onResult(LoginContract.REQUEST_FAIL_ID, e.getMessage());
                    }
                }
            }
        });

    }


}
