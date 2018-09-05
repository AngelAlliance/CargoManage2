package com.sz.ljs.setting.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.base.event.EventID;
import com.sz.ljs.base.event.EventMSG;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.setting.R;
import com.sz.ljs.setting.contract.SettingContract;
import com.sz.ljs.setting.presenter.SettingPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liujs on 2018/8/20.
 * 设置界面
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_xiugaimima, ll_guanyu, ll_banbengengxin;
    private ImageView iv_tishiyin, iv_zhendong;
    private TextView tv_banben;
    private Button bt_loginOut;
    private boolean isOpenTiShi=false;
    private boolean isOpenZhenDong=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        ll_xiugaimima = (LinearLayout) findViewById(R.id.ll_xiugaimima);
        ll_guanyu = (LinearLayout) findViewById(R.id.ll_guanyu);
        ll_banbengengxin = (LinearLayout) findViewById(R.id.ll_banbengengxin);
        iv_tishiyin = (ImageView) findViewById(R.id.iv_tishiyin);
        iv_zhendong = (ImageView) findViewById(R.id.iv_zhendong);
        tv_banben = (TextView) findViewById(R.id.tv_banben);
        bt_loginOut = (Button) findViewById(R.id.bt_loginOut);
    }

    private void setListener() {
        ll_xiugaimima.setOnClickListener(this);
        ll_guanyu.setOnClickListener(this);
        ll_banbengengxin.setOnClickListener(this);
        iv_tishiyin.setOnClickListener(this);
        iv_zhendong.setOnClickListener(this);
        bt_loginOut.setOnClickListener(this);
    }

    private void initData() {
        tv_banben.setText("" + getShowVersion());
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_xiugaimima) {
            //TODO 修改密码
            BaseApplication.startActivity(ChangePasswordActivity.class);
        } else if (id == R.id.ll_guanyu) {
            //TODO 关于
            BaseApplication.startActivity(AboutActivity.class);
        } else if (id == R.id.ll_banbengengxin) {
            //TODO 版本更新

        } else if (id == R.id.iv_tishiyin) {
            //TODO 提示音
            if(false==isOpenTiShi){
                isOpenTiShi=true;
                iv_tishiyin.setImageResource(R.mipmap.setting_riv1);
            }else {
                isOpenTiShi=false;
                iv_tishiyin.setImageResource(R.mipmap.setting_riv0);
            }
        } else if (id == R.id.iv_zhendong) {
            //TODO 震动
            if(false==isOpenZhenDong){
                isOpenZhenDong=true;
                iv_zhendong.setImageResource(R.mipmap.setting_riv1);
            }else {
                isOpenZhenDong=false;
                iv_zhendong.setImageResource(R.mipmap.setting_riv0);
            }
        } else if (id == R.id.bt_loginOut) {
            //TODO 退出登录
            handlerLogOff();
        }
    }

    private String getShowVersion() {
        return getString(R.string.str_dqbb) + ":v" + Utils.getVersionName(SettingActivity.this);
    }

    //TODO 注销用户
    private void handlerLogOff() {
        new AlertDialog(SettingActivity.this).builder()
                .setTitle(getString(R.string.str_login_out))
                .setMsg(getString(R.string.str_Logout_tips))
                .setNegativeButtonVisibility()
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new EventMSG(EventID.USER_LOG_OFF));
                    }
                }).show();
    }


}
