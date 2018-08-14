package com.sz.ljs.common.view;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiloo.WBarcodeScan.callback.IDecodeCallBack;
import com.qiloo.WBarcodeScan.view.WBarcodeScanView;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.R;

/**
 * Created by liujs on 2018/8/14.
 */

public class ScanView extends BaseActivity implements View.OnClickListener {
    private final String[] permission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private WBarcodeScanView mBarScanView = null;

    public interface SacnCallBack {
        void onResult(String result);
    }

    private static SacnCallBack callBack;

    public static void ScanView(SacnCallBack CallBack) {
        callBack = CallBack;
        BaseApplication.startActivity(ScanView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanview);
        initView();
        setListener();
    }

    private void initView() {
        mBarScanView = (WBarcodeScanView) findViewById(R.id.scan_view);
    }

    private void setListener() {
        mBarScanView.setDecodeCallBack(new IDecodeCallBack() {
            @Override
            public void onHandleDecode(String s) {
                Log.i("ScanView", "扫描到的结果为:" + s);
                if (null != callBack) {
                    callBack.onResult(s);
                    finish();
                }
            }

            @Override
            public void surfaceCreated() {
                if (authorizeRuntimePermission()) {
                    mBarScanView.initScanView();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected String[] getRuntimePermissions() {
        return permission;
    }
}
