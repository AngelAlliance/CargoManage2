package com.sz.ljs.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sz.ljs.base.event.EventID;
import com.sz.ljs.base.event.EventMSG;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 刘劲松 on 2018/8/8.
 * Activity的基类，实现项目中Activity的通用功能和虚函数等的定义
 */

public class BaseActivity extends RxAppActivity {
    private final static String TAG = "BaseActivity";
    private PermissionsUtils mPermissionsUtils;
    private boolean canSystemBarTransparent = true;
    private boolean mNoExecFinishMSG = false;//不执行结束窗口的消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPermissionsUtils = new PermissionsUtils(this)
                .setResultCallBack(new PermissionsUtils.IResultCallBack() {
                    @Override
                    public void onApplyPermissionsSuccess() {
                        onPermissionsSuccess();
                    }

                    @Override
                    public void onApplyPermissionsFail() {
                        onPermissionsFail();
                    }
                });
//        setTheme(ThemeUtils.getCurrentThemmId());//用来实现动态主题更换的
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //TODO 检查权限
    private void checkPermission() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(580);
                    if (null != mPermissionsUtils) {
                        mPermissionsUtils.checkRuntimePermissions(getRuntimePermissions());
                    }
                } catch (Exception e) {
                    Log.w(TAG, "checkPermission() " + e.getMessage());
                }

            }
        });
    }

    //TODO 是否已经授权运行时权限
    public boolean authorizeRuntimePermission() {
        return null != mPermissionsUtils
                && mPermissionsUtils.authorizeRuntimePermissions(getRuntimePermissions());
    }

    //TODO 置侵入式状态栏
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && canSystemBarTransparent) {
            ImmerseHelper.setSystemBarTransparent(this);
        }
        checkPermission();
    }

    //TODO 必须要在oncreate下调用
    protected void setCanSystemBarTransparent(boolean bvalue) {
        canSystemBarTransparent = bvalue;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (null != mPermissionsUtils) {
            mPermissionsUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public BaseActivity getBaseActivity() {
        return this;
    }

    //TODO 返回当前界面需要申请的权限
    protected String[] getRuntimePermissions() {
        return null;
    }

    //TODO 权限申请成功的回调
    protected void onPermissionsSuccess() {

    }

    //TODO 申请权限失败的回调
    protected void onPermissionsFail() {

    }

    //TODO 获取Activity的上下文
    public Context getContext() {
        return this;
    }

    //TODO 显示运行时授权界面
    public void showApplyDialog() {
        mPermissionsUtils.showApplyDialog(getRuntimePermissions());
    }

    //EventBus 回调接口
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执
    public void onEventMainThread(EventMSG events) {
        onEventHandler(events);
        if (events.what == EventID.FINISH_ACTIVITY && !mNoExecFinishMSG) {
            finish();
        }
    }

    //TODO 设置不执行 FINISH_ACTIVITY 消息的标识
    public void setNoExecFinishMSG(boolean bset) {
        mNoExecFinishMSG = bset;
    }

    protected void onEventHandler(EventMSG event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.i(TAG, "onDestroy() " + this.getClass().getName());

    }


}
