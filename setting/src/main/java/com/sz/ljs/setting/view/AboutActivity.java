package com.sz.ljs.setting.view;

import android.os.Bundle;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.setting.R;

/**
 * Created by liujs on 2018/8/20.
 * 关于界面
 */

public class AboutActivity extends BaseActivity {

    private TextView tv_versionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboult);
        initView();
    }

    private void initView(){
        tv_versionCode=(TextView) findViewById(R.id.tv_versionCode);
        tv_versionCode.setText("v"+ Utils.getVersionName(AboutActivity.this));
    }
}
