package com.sz.ljs.cargomanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.cargomanage.R;
import com.sz.ljs.cargomanage.contract.LoginContract;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.presenter.LoginPresenter;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.WaitingDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements LoginContract.View{
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 1500;
    private WaitingDialog mWaitingDialog;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        createDialog();
        loginPresenter = new LoginPresenter(this);
        doLogin();
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//
//            }
//        }, SPLASH_DELAY_MILLIS);
    }


    private void doLogin(){
        if(!TextUtils.isEmpty(UserModel.getInstance().getLocalSaveUserNo())
                &&!TextUtils.isEmpty(UserModel.getInstance().getLocalSavePassword())){
            loginPresenter.doLogin(UserModel.getInstance().getLocalSaveUserNo()
                    , UserModel.getInstance().getLocalSavePassword());
        }else {
            goLogin();
        }
    }
    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case LoginContract.REQUEST_FAIL_ID:
               goLogin();
                break;
            case LoginContract.REQUEST_SUCCESS_ID:
                goHome();
                break;
        }
    }
    private void createDialog() {
        if (null == mWaitingDialog) {
            mWaitingDialog = new WaitingDialog(this);
            mWaitingDialog.setWaitText(getString(R.string.str_login_ing));
        }
    }

    //TODO 进入主界面
    private void goHome() {
        BaseApplication.startActivity(MainActivity.class);
        finish();
    }
    private void goLogin(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }


    public void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }


}
