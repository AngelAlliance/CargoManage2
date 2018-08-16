package com.sz.ljs.patchlabel.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.patchlabel.R;

/**
 * Created by liujs on 2018/8/16.
 * 补打标签界面
 */

public class PatchlabelActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_type;
    private ImageView iv_chose;
    private EditText et_danhao, et_jianshu;
    private RelativeLayout ll_dayin;
    private YunDanTypePopuwindow popuwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patchlabel);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        popuwindow=new YunDanTypePopuwindow(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        iv_chose = (ImageView) findViewById(R.id.iv_chose);
        et_danhao = (EditText) findViewById(R.id.et_danhao);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        ll_dayin = (RelativeLayout) findViewById(R.id.ll_dayin);

    }

    private void setListener() {
        iv_chose.setOnClickListener(this);
        ll_dayin.setOnClickListener(this);
        popuwindow.setYunDanClickListener(new YunDanTypePopuwindow.OnclickYunDanClickListener() {
            @Override
            public void onclick() {
                //TODO 运单号
                tv_type.setText(getResources().getString(R.string.str_ydh));
                popuwindow.dismiss();
            }
        });
        popuwindow.setZiDanClickListener(new YunDanTypePopuwindow.OnclickZiDanClickListener() {
            @Override
            public void onclick() {
                //TODO 子单号
                tv_type.setText(getResources().getString(R.string.zdh));
                popuwindow.dismiss();
            }
        });
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_chose) { //选择单号类型
            popuwindow.showPopupWindow(tv_type);
        } else if (id == R.id.ll_dayin) {
            //TODO 打印

        }
    }
}
