package com.sz.ljs.patchlabel.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.posapi.PosApi;
import android.posapi.PrintQueue;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.utils.BarcodeCreater;
import com.sz.ljs.common.utils.BitmapTools;
import com.sz.ljs.common.utils.PrintManager;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.patchlabel.R;

import java.io.UnsupportedEncodingException;


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
    private AlertDialog alertDialog;

    private WaitingDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patchlabel);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        PrintManager.getInstance().init(this);
        waitingDialog = new WaitingDialog(this);
        popuwindow = new YunDanTypePopuwindow(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        iv_chose = (ImageView) findViewById(R.id.iv_chose);
        et_danhao = (EditText) findViewById(R.id.et_danhao);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        ll_dayin = (RelativeLayout) findViewById(R.id.ll_dayin);

    }

    private void initData() {


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


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_chose) { //选择单号类型
            popuwindow.showPopupWindow(tv_type);
        } else if (id == R.id.ll_dayin) {
            //TODO 打印
            PrintManager.getInstance().creatOneDimensionalCode(et_danhao.getText().toString().trim());
//            creatOneDimensionalCode();
        }
    }


    /**
     * 弹出提示框
     *
     * @param msg
     */
    private void showTip(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(PatchlabelActivity.this)
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

    private void showWaiteDialog(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

//		unregisterReceiver(receiver);
    }
}
