package com.sz.ljs.cargomanage.presenter;

import com.google.gson.Gson;
import com.sz.ljs.cargomanage.contract.LoginContract;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.model.ScanNumberLengModel;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;

import java.util.HashMap;
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

public class LoginPresenter {
    private LoginContract loginContract;
    public LoginPresenter(){
        loginContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(LoginContract.class);
    }

    public void release(){
        loginContract = null;
    }

    public Flowable<LoginModel> doLogin(String userName,String pwd){
        Map<String, String> param = new HashMap<>();
        param.put(LoginContract.USER_NAME,userName);
        param.put(LoginContract.PASS_WORD,pwd);
        param.put(LoginContract.TIME_STAMP, ApiTimestampToken.getTimestampString());
        param.put(LoginContract.SUMMARY, MD5Util.get32MD5Str(userName+pwd+"zhbg_ips2018_cn"));
        return  loginContract.doLogin(param);
    }

    //TODO 返回输入运单N位得时候调用接口
    public Flowable<ScanNumberLengModel> getScanNumberLeng(){
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
        return loginContract.getScanNumberLeng(token,requestBody);
    }
}
