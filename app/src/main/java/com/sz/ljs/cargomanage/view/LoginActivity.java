package com.sz.ljs.cargomanage.view;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.base.event.EventID;
import com.sz.ljs.base.event.EventMSG;
import com.sz.ljs.cargomanage.R;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.presenter.LoginPresenter;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.WaitingDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujs on 2018/8/9.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_workNum, et_password;
    private ImageView img_view_workNum_del, img_view_pwd_del;
    private Button bt_login;
    private ToggleButton according_userPwd_tb;
    private LoginPresenter loginPresenter;
    private WaitingDialog mWaitingDialog = null;
    private final static String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        createDialog();
        initData();
        EventBus.getDefault().post(new EventMSG(EventID.CHECK_NEW_VERSION));
    }

    private void initView() {
        et_workNum = (EditText) findViewById(R.id.et_workNum);
        et_password = (EditText) findViewById(R.id.et_password);
        img_view_workNum_del = (ImageView) findViewById(R.id.img_view_workNum_del);
        img_view_pwd_del = (ImageView) findViewById(R.id.img_view_pwd_del);
        according_userPwd_tb = (ToggleButton) findViewById(R.id.according_userPwd_tb);
        bt_login = (Button) findViewById(R.id.bt_login);
    }

    private void initData() {
        loginPresenter = new LoginPresenter();
    }

    private void createDialog() {
        if (null == mWaitingDialog) {
            mWaitingDialog = new WaitingDialog(this);
            mWaitingDialog.setWaitText(getString(R.string.str_login_ing));
        }
    }

    private void setListener() {
        et_workNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    img_view_workNum_del.setVisibility(View.GONE);
                } else {
                    img_view_workNum_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    img_view_pwd_del.setVisibility(View.GONE);
                } else {
                    img_view_pwd_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt_login.setOnClickListener(this);
        img_view_workNum_del.setOnClickListener(this);
        img_view_pwd_del.setOnClickListener(this);
        according_userPwd_tb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.according_userPwd_tb) {
            clickShowPassword();
        } else if (id == R.id.bt_login) {
            doLogin();
        } else if (id == R.id.img_view_workNum_del) {
            et_workNum.setText("");
            img_view_workNum_del.setVisibility(View.GONE);
        } else if (id == R.id.img_view_pwd_del) {
            et_password.setText("");
            img_view_pwd_del.setVisibility(View.GONE);
        }
    }

    //TODO 登录
    private void doLogin() {
        if (TextUtils.isEmpty(et_workNum.getText().toString().trim())) {
            Utils.showToast(LoginActivity.this, getResources().getString(R.string.str_ghbwk));
            return;
        }
        if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
            Utils.showToast(LoginActivity.this, getResources().getString(R.string.str_mmbwk));
            return;
        }
//        goHome();
        showWaiting(true);
        loginPresenter.doLogin(et_workNum.getText().toString().trim(), et_password.getText().toString().trim())
                .compose(this.<LoginModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(LoginModel result) throws Exception {
                        showWaiting(false);
                        if (0 == result.getCode()) {
                            Utils.showToast(LoginActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            handelLoginResult(result);
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

    private void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }


    // TODO 密码登录是否明文显示
    private void clickShowPassword() {
        if (!according_userPwd_tb.isChecked()) {
            et_password.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        et_password.setSelection(et_password.length());
    }
    @Override
    protected String[] getRuntimePermissions() {
        return RUNTIME_PERMISSIONS;
    }
}
