package com.sz.ljs.warehousing.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.warehousing.R;

import java.util.List;

/**
 * 入库界面
 */

public class WareHousingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_yubaokehu, iv_scan, iv_mudiguojia, iv_xiaoshouchanpin;
    private EditText et_yundanhao, et_kehudaima, et_kehucankaodanhao, et_shizhong, et_chang, et_kuan, et_gao, et_daohuozongdan;
    private TextView et_mudiguojia, et_xiaoshouchanpin;
    private LinearLayout ll_mudiguojia, ll_xiaoshouchanpin, ll_duojian;
    private Button btn_qianru, btn_fujiafuwu;
    private String countryCode;//国家简码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_warehousing);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        iv_yubaokehu = (ImageView) findViewById(R.id.iv_yubaokehu);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_mudiguojia = (ImageView) findViewById(R.id.iv_mudiguojia);
        iv_xiaoshouchanpin = (ImageView) findViewById(R.id.iv_xiaoshouchanpin);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        et_kehudaima = (EditText) findViewById(R.id.et_kehudaima);
        et_kehucankaodanhao = (EditText) findViewById(R.id.et_kehucankaodanhao);
        et_mudiguojia = (TextView) findViewById(R.id.et_mudiguojia);
        et_xiaoshouchanpin = (TextView) findViewById(R.id.et_xiaoshouchanpin);
        et_shizhong = (EditText) findViewById(R.id.et_shizhong);
        et_chang = (EditText) findViewById(R.id.et_chang);
        et_kuan = (EditText) findViewById(R.id.et_kuan);
        et_gao = (EditText) findViewById(R.id.et_gao);
        et_daohuozongdan = (EditText) findViewById(R.id.et_daohuozongdan);
        ll_mudiguojia = (LinearLayout) findViewById(R.id.ll_mudiguojia);
        ll_xiaoshouchanpin = (LinearLayout) findViewById(R.id.ll_xiaoshouchanpin);
        ll_duojian = (LinearLayout) findViewById(R.id.ll_duojian);
        btn_qianru = (Button) findViewById(R.id.btn_qianru);
        btn_fujiafuwu = (Button) findViewById(R.id.btn_fujiafuwu);
    }

    private void initData() {

    }

    private void setListener() {
        ll_mudiguojia.setOnClickListener(this);
        ll_xiaoshouchanpin.setOnClickListener(this);
        ll_duojian.setOnClickListener(this);
        btn_qianru.setOnClickListener(this);
        btn_fujiafuwu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_mudiguojia) {
            //TODO 目的国家
        } else if (id == R.id.ll_xiaoshouchanpin) {
            //TODO 销售产品
        } else if (id == R.id.ll_duojian) {
            //TODO 多件
            Intent intent = new Intent(WareHousingActivity.this, AddSubunitActivity.class);
            startActivityForResult(intent, 1000);
        } else if (id == R.id.btn_qianru) {
            //TODO 签入
        } else if (id == R.id.btn_fujiafuwu) {
            //TODO 附加服务
            Intent intent = new Intent(WareHousingActivity.this, AddServiceActivity.class);
            startActivityForResult(intent, 1001);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {

                }
                break;
                case 1001: {

                }
                break;
            }
        }
    }
}
