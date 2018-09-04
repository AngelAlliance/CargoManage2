package com.sz.ljs.setting.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.setting.R;
import com.sz.ljs.setting.contract.SettingContract;
import com.sz.ljs.setting.presenter.SettingPresenter;

/**
 * Created by liujs on 2018/8/20.
 */

public class ChangePasswordActivity extends BaseActivity implements SettingContract.View, View.OnClickListener {

    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private SettingPresenter mPresenter;

    private TextView tv_gohao, tv_name;
    private EditText et_oldPassword, et_newPassword, et_querenPassword;
    private ImageView img_view_oldpwd_del, img_view_newpwd_del, img_view_querenpwd_del;
    private ToggleButton according_oldPwd_tb, according_newPwd_tb, according_querenPwd_tb;
    private Button bt_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        setListener();
    }

    private void initView() {
        waitingDialog = new WaitingDialog(this);
        mPresenter = new SettingPresenter(this);
        tv_gohao = (TextView) findViewById(R.id.tv_gohao);
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_oldPassword = (EditText) findViewById(R.id.et_oldPassword);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_querenPassword = (EditText) findViewById(R.id.et_querenPassword);
        img_view_oldpwd_del = (ImageView) findViewById(R.id.img_view_oldpwd_del);
        img_view_newpwd_del = (ImageView) findViewById(R.id.img_view_newpwd_del);
        img_view_querenpwd_del = (ImageView) findViewById(R.id.img_view_querenpwd_del);
        according_oldPwd_tb = (ToggleButton) findViewById(R.id.according_oldPwd_tb);
        according_newPwd_tb = (ToggleButton) findViewById(R.id.according_newPwd_tb);
        according_querenPwd_tb = (ToggleButton) findViewById(R.id.according_querenPwd_tb);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        if (!TextUtils.isEmpty(UserModel.getInstance().getSt_name())) {
            tv_name.setText(UserModel.getInstance().getSt_name());
        }
        if (!TextUtils.isEmpty(UserModel.getInstance().getSt_code())) {
            tv_gohao.setText(UserModel.getInstance().getSt_code());
        }
    }

    private void setListener() {
        img_view_oldpwd_del.setOnClickListener(this);
        img_view_newpwd_del.setOnClickListener(this);
        img_view_querenpwd_del.setOnClickListener(this);
        according_oldPwd_tb.setOnClickListener(this);
        according_newPwd_tb.setOnClickListener(this);
        according_querenPwd_tb.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        et_oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    img_view_oldpwd_del.setVisibility(View.GONE);
                }else {
                    img_view_oldpwd_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    img_view_newpwd_del.setVisibility(View.GONE);
                }else {
                    img_view_newpwd_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_querenPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    img_view_querenpwd_del.setVisibility(View.GONE);
                }else {
                    img_view_querenpwd_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_view_oldpwd_del) {
            et_oldPassword.setText("");
        } else if (id == R.id.img_view_newpwd_del) {
            et_newPassword.setText("");
        } else if (id == R.id.img_view_querenpwd_del) {
            et_querenPassword.setText("");
        } else if (id == R.id.bt_ok) {
            updatePassWord();
        }else if (id == R.id.according_oldPwd_tb) {
            clickShowPassword();
        }else if (id == R.id.according_newPwd_tb) {
            clickShowPassword();
        }else if (id == R.id.according_querenPwd_tb) {
            clickShowPassword();
        }
    }

    private void updatePassWord() {
        if (TextUtils.isEmpty(et_oldPassword.getText().toString().trim())) {
            showTipeDialog("请输入原始密码");
            return;
        }
        if (TextUtils.isEmpty(et_newPassword.getText().toString().trim())) {
            showTipeDialog("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(et_querenPassword.getText().toString().trim())) {
            showTipeDialog("请输入确认密码");
            return;
        }

        if (et_newPassword.getText().toString().trim().equals(et_oldPassword.getText().toString().trim())) {
            showTipeDialog("新密码与原始密码相同");
            return;
        }

        if (!et_querenPassword.getText().toString().trim().equals(et_newPassword.getText().toString().trim())) {
            showTipeDialog("新密码与确认密码输入不相同");
            return;
        }

        mPresenter.updatePassword(et_oldPassword.getText().toString().trim(), et_newPassword.getText().toString().trim()
                , et_querenPassword.getText().toString().trim());
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case SettingContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case SettingContract.REQUEST_SUCCESS_ID:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ChangePasswordActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg("重置密码成功")
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                });
                break;
        }
    }

    @Override
    public void showWaiting(boolean show) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(show);
        }
    }


    private void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(ChangePasswordActivity.this)
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
        if (!according_oldPwd_tb.isChecked()) {
            et_oldPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            et_oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        et_oldPassword.setSelection(et_oldPassword.length());

        if (!according_newPwd_tb.isChecked()) {
            et_newPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            et_newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        et_newPassword.setSelection(et_newPassword.length());

        if (!according_querenPwd_tb.isChecked()) {
            et_querenPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            et_querenPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        et_querenPassword.setSelection(et_querenPassword.length());
    }

}
