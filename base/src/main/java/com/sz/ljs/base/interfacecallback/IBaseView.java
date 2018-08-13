package com.sz.ljs.base.interfacecallback;

/**
 * Created by liujs on 2018/8/8.
 */

public interface IBaseView {
    public <T> void OnSuccess(T result);//登录成功执行
    public <T> void OnError(T Error);//登录失败执行
    public void showWaiting(boolean show); //显示等待界面s
}
