package com.sz.ljs.base.interfacecallback;

/**
 * Created by 刘劲松 on 2018/8/8.
 * 通用视图处理接口
 */

public interface IGenView {
    public final static int RESULT_SUCCESS = 0; //返回成功
    public final static int RESULT_ERROR_FAIL = -1; //返回失败
    public final static int RESULT_ERROR_NATWORK = -2;//网络错误
    //等待界面
    public void showWaiting(boolean show);
    //结果返回
    public void onResult(int Id, String result);
}
