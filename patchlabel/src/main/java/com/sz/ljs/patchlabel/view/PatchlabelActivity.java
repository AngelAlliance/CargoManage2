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
    private PosApi mPosSDK = null;
    private PrintQueue mPrintQueue = null;
    boolean isCanPrint = true;
    public int level_battry = 50;
    private Bitmap mBitmap = null;
    private int mLeft = 20; //打印纸左边距
    private int concentration = 60;//打印浓度
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
        waitingDialog=new WaitingDialog(this);
        popuwindow = new YunDanTypePopuwindow(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        iv_chose = (ImageView) findViewById(R.id.iv_chose);
        et_danhao = (EditText) findViewById(R.id.et_danhao);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        ll_dayin = (RelativeLayout) findViewById(R.id.ll_dayin);

    }

    private void initData() {
        // 获取PosApi实例
        mPosSDK = BaseApplication.getInstance().getPosApi();
        //监听初始化回调结果
        mPosSDK.setOnComEventListener(mCommEventListener);
        //获取状态时回调
        mPosSDK.setOnDeviceStateListener(onDeviceStateListener);
        // 打印队列初始化
        mPrintQueue = new PrintQueue(this, mPosSDK);
        // 打印队列初始化
        mPrintQueue.init();

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
        //打印队列设置监听
        mPrintQueue.setOnPrintListener(new PrintQueue.OnPrintListener() {
            //打印完成
            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                showWaiteDialog(false);
                //打印完成
                showTip(getString(R.string.print_complete));
                //当前可打印
                isCanPrint = true;
            }

            //打印失败
            @Override
            public void onFailed(int state) {
                // TODO Auto-generated method stub
                isCanPrint = true;
                showWaiteDialog(false);
                switch (state) {
                    case PosApi.ERR_POS_PRINT_NO_PAPER:
                        // 打印缺纸
                        showTip(getString(R.string.print_no_paper));
                        break;
                    case PosApi.ERR_POS_PRINT_FAILED:
                        // 打印失败
                        showTip(getString(R.string.print_failed));
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_LOW:
                        // 电压过低
                        showTip(getString(R.string.print_voltate_low));
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_HIGH:
                        // 电压过高
                        showTip(getString(R.string.print_voltate_high));
                        break;
                }
            }

            @Override
            public void onGetState(int arg0) {
                // TODO Auto-generated method stub

            }

            //打印设置
            @Override
            public void onPrinterSetting(int state) {
                // TODO Auto-generated method stub
                isCanPrint = true;
                showWaiteDialog(false);
                switch (state) {
                    case 0:
                        showTip("持续有纸");
                        break;
                    case 1:
                        //缺纸
                        showTip(getString(R.string.no_paper));
                        break;
                    case 2:
                        //检测到黑标
                        showTip(getString(R.string.label));
                        break;
                }
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
            creatOneDimensionalCode();
        }
    }


    //TODO 生成一维码图片并且打印出来
    private void creatOneDimensionalCode() {
        showWaiteDialog(true);
        if (TextUtils.isEmpty(et_danhao.getText().toString())) {
            showWaiteDialog(false);
            showTip(getResources().getString(R.string.str_qsrydydydh));
            return;
        }
        //如果判断为字节长度大于其字符长度，则判定为无法生成条码的字符
        if (et_danhao.getText().toString().getBytes().length > et_danhao.getText().toString().length()) {
            showWaiteDialog(false);
            showTip(getResources().getString(R.string.cannot_create_bar));
            return;
        }

        //生成一维码
        mBitmap = BarcodeCreater.creatBarcode(getApplicationContext(),
                et_danhao.getText().toString(), 384, 100, true,
                1);
        //图片为空则返回
        if (mBitmap == null){
            showWaiteDialog(false);
            showTip("一维码生成失败");
            return;
        }
        //打印中，不执行本次操作
        if (!isCanPrint){
            showWaiteDialog(false);
            showTip("正在打印中");
            return;
        }
        //电量低于12%不执行打印
        if(level_battry<=12){
            showWaiteDialog(false);
            showTip("低电量,不能打印,请先充电");
            return;
        }
        byte[] printData = BitmapTools.bitmap2PrinterBytes(mBitmap);
        if(concentration<=25){
            concentration=25;
        }
        mPrintQueue.addBmp(concentration, mLeft, mBitmap.getWidth(),
                mBitmap.getHeight(), printData);
        try {
            mPrintQueue.addText(concentration, "\n\n\n\n\n".toString()
                    .getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            Log.i("打印的时候出错","error="+e.toString());
            e.printStackTrace();
        }
        //设为不可打印
        isCanPrint=false;
        //打印队列开始执行
        mPrintQueue.printStart();
    }

    /**
     * 初始化
     */
    PosApi.OnCommEventListener mCommEventListener = new PosApi.OnCommEventListener() {
        @Override
        public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
            // TODO Auto-generated method stub
            switch (cmdFlag) {
                case PosApi.POS_INIT:
                    if (state == PosApi.COMM_STATUS_SUCCESS) {
//                        showTip("设备初始化成功");
                    } else {
                        showTip("设备初始化失败");
                    }
                    break;
            }
        }
    };

    private PosApi.OnDeviceStateListener onDeviceStateListener = new PosApi.OnDeviceStateListener() {
        /**
         * @param state 0-获取状态成功  1-获取状态失败
         * @param version 设备固件版本
         * @param serialNo 设备序列号
         * @param psam1 psam1 状态   0-正常   1-无卡   2-卡错误
         * @param psam2 psam2 状态   0-正常   1-无卡   2-卡错误
         * @param ic IC卡 状态   0-正常   1-无卡   2-卡错误
         * @param swipcard 磁卡状态 o-正常  1-故障
         * @param printer 打印机状态 0-正常  1-缺纸
         */
        public void OnGetState(int state, String version, String serialNo, int psam1, int psam2, int ic, int swipcard, int printer) {
            if (state == PosApi.COMM_STATUS_SUCCESS) {

                StringBuilder sb = new StringBuilder();
                String mPsam1 = null;
                switch (psam1) {
                    case 0:
                        mPsam1 = getString(R.string.state_normal);
                        break;
                    case 1:
                        mPsam1 = getString(R.string.state_no_card);
                        break;
                    case 2:
                        mPsam1 = getString(R.string.state_card_error);
                        break;
                }

                String mPsam2 = null;
                switch (psam2) {
                    case 0:
                        mPsam2 = getString(R.string.state_normal);
                        break;
                    case 1:
                        mPsam2 = getString(R.string.state_no_card);
                        break;
                    case 2:
                        mPsam2 = getString(R.string.state_card_error);
                        break;
                }

                String mIc = null;
                switch (ic) {
                    case 0:
                        mIc = getString(R.string.state_normal);
                        break;
                    case 1:
                        mIc = getString(R.string.state_no_card);
                        break;
                    case 2:
                        mIc = getString(R.string.state_card_error);
                        break;
                }

                String magnetic_card = null;
                switch (swipcard) {
                    case 0:
                        magnetic_card = getString(R.string.state_normal);
                        break;
                    case 1:
                        magnetic_card = getString(R.string.state_fault);
                        break;

                }

                String mPrinter = null;
                switch (printer) {
                    case 0:
                        mPrinter = getString(R.string.state_normal);
                        break;
                    case 1:
                        mPrinter = getString(R.string.state_no_paper);
                        break;

                }


            } else {
                // 获取状态失败
            }
        }
    };

    /**
     * 接受电量改变广播
     */
    class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

                level_battry = intent.getIntExtra("level", 0);

            }
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
                        .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dissmiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    private void showWaiteDialog(boolean isShow){
        if(null!=waitingDialog){
            waitingDialog.showDialog(isShow);
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mBitmap != null) {
            mBitmap.recycle();
        }

        if (mPrintQueue != null) {
            mPrintQueue.close();
        }
//		unregisterReceiver(receiver);
    }
}
