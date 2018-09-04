package com.sz.ljs.cargomanage.view;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.sz.ljs.cargomanage.contract.LoginContract;
import com.sz.ljs.cargomanage.model.LoginModel;
import com.sz.ljs.cargomanage.presenter.LoginPresenter;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    private EditText et_workNum, et_password;
    private ImageView img_view_workNum_del, img_view_pwd_del;
    private Button bt_login;
    private ToggleButton according_userPwd_tb;
    private LoginPresenter loginPresenter;
    private WaitingDialog mWaitingDialog = null;
    private AlertDialog alertDialog;
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
        loginPresenter = new LoginPresenter(this);
    }

    private void createDialog() {
        if (null == mWaitingDialog) {
            mWaitingDialog = new WaitingDialog(this);
            mWaitingDialog.setWaitText(getString(R.string.str_login_ing));
        }
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case LoginContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case LoginContract.REQUEST_SUCCESS_ID:
                goHome();
                break;
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
            showTipeDialog(getResources().getString(R.string.str_ghbwk));
            return;
        }
        if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
            showTipeDialog(getResources().getString(R.string.str_mmbwk));
            return;
        }
        loginPresenter.doLogin(et_workNum.getText().toString().trim(), et_password.getText().toString().trim());
    }


    //TODO 进入主界面
    private void goHome() {
        BaseApplication.startActivity(MainActivity.class);
        finish();
    }

    public void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }

    public void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(LoginActivity.this)
                        .builder()
                        .setTitle(getResources().getString(R.string.str_alter))
                        .setMsg(msg)
                        .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dissmiss();
                            }
                        });
                alertDialog.show();
            }
        });
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
