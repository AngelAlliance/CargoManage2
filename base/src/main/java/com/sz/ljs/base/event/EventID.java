package com.sz.ljs.base.event;

/**
 * Created by 刘劲松 on 2018/8/8.
 * EventBus事件定义类
 */

public enum EventID {
    CHECK_NEW_VERSION,  //检查新版本
    NOTIFY_DEVICE_DATA_CHANGED,    //更新设备数据
    NOTIFY_USER_DATA_CHANGED,      //更新用户数据
    FINISH_ACTIVITY,        //结束界面
    USER_LOG_OFF,           //用户注销处理
    USER_LOG_ENTRY,         //用户登录进入
    START_MAIN_ACTIVITY,    //启动主界面
    ;
}
