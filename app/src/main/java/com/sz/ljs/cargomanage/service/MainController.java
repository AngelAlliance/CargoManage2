package com.sz.ljs.cargomanage.service;

import android.app.Application;
import android.content.Context;

import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.base.event.EventID;
import com.sz.ljs.base.event.EventMSG;
import com.sz.ljs.cargomanage.view.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by 侯晓戬 on 2017/8/29.
 * 系统主控制处理类
 * 主要用来处理全局控制EventBus
 */

public class MainController {
    private final Context mContext;

    public MainController(Application context) {
        this.mContext = context;
        EventBus.getDefault().register(this);
    }

    public void release() {
        EventBus.getDefault().unregister(this);
    }

    private Context getContext() {
        return mContext;
    }

    //在主ui线程执
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(EventMSG message) {
        switch (message.what) {
            case START_MAIN_ACTIVITY: {
                BaseApplication.startActivity(MainActivity.class);
                EventBus.getDefault().post(new EventMSG(EventID.CHECK_NEW_VERSION));
            }
            break;
        }
    }

    //后台线程执行
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onEventBackGround(EventMSG message) {
        switch (message.what) {
            case USER_LOG_OFF: {
                //注销用户
                logOffCleanUp(true);
            }
            break;
        }
    }

    //注销用户和清理数据
    private void logOffCleanUp(boolean logOff) {
        if (logOff) {
            //释放与用户相关业务数据
        }
    }

}
