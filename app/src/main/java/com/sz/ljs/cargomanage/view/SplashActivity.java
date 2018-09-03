package com.sz.ljs.cargomanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.cargomanage.R;
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

public class SplashActivity extends BaseActivity{
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 1500;
    private WaitingDialog mWaitingDialog;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        createDialog();
        loginPresenter=new LoginPresenter();
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                doLogin();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void doLogin(){
        if(!TextUtils.isEmpty(UserModel.getInstance().getLocalSaveUserNo())&&!TextUtils.isEmpty(UserModel.getInstance().getLocalSavePassword())){
            showWaiting(true);
            loginPresenter.doLogin(UserModel.getInstance().getLocalSaveUserNo(), UserModel.getInstance().getLocalSavePassword())
                    .compose(this.<LoginModel>bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginModel>() {
                        @Override
                        public void accept(LoginModel result) throws Exception {
                            showWaiting(false);
                            if (1 == result.getCode()) {
                                handelLoginResult(result);
                            }else {
                                goLogin();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //获取失败，提示
                            showWaiting(false);
                            Utils.showToast(getBaseActivity(), R.string.str_dlsb);
                        }
                    });
        }else {
            goLogin();
        }
    }
    private void createDialog() {
        if (null == mWaitingDialog) {
            mWaitingDialog = new WaitingDialog(this);
            mWaitingDialog.setWaitText(getString(R.string.str_login_ing));
        }
    }
    //TODO 处理登录返回的数据
    private void handelLoginResult(LoginModel result) {
        if (null != result && null != result.getData() && null != result.getData().getUserModel()) {
            LoginModel.DataEntity dataEntity = result.getData();
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
            for (LoginModel.DataEntity.PermissionEntity model : dataEntity.getPermission()) {
                UserModel.PermissionEntity permissionEntity = new UserModel.PermissionEntity();
                permissionEntity.setMi_ename(model.getMi_ename());
                permissionEntity.setMi_name(model.getMi_name());
                list.add(permissionEntity);
            }
            UserModel.getInstance().setPermission(list);
//            UserModel.getInstance().setLocalSaveUserNo(et_workNum.getText().toString().trim());
//            UserModel.getInstance().setLocalSavePassword(et_password.getText().toString().trim());
            ApiTimestampToken.setToken(dataEntity.getTokenModel().getToken());
            ApiTimestampToken.setUserID(dataEntity.getUserModel().getSt_id());
            goHome();
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


    private void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }
}
