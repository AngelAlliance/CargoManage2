package com.sz.ljs.cargomanage.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by 侯晓戬 on 2017/8/29.
 * 后台处理的服务
 */

public class MainService extends Service {
    private MainController mMainController = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMainController = new MainController(getApplication());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainController.release();
        mMainController = null;
    }
}
