package com.sz.ljs.common.base;

import com.orhanobut.logger.Logger;

import okhttp3.Response;

/**
 * 2017/7/7
 * wangxiaoer
 * 功能描述：普通未处理回调
 **/
public abstract class IHttpUtilsCallBack implements IResponseHandler {

    private final static String TAG ="IHttpUtilsCallBack";
    /**
     * 请求失败
     *
     * @param statusCode
     * @param error_msg
     */
    @Override
    public abstract void onFailure(int statusCode, String error_msg);

    /**
     * 请求成功(需转换)
     * JSONObject jsonObject = new JSONObject(result)
     * String result=response.body().string();
     *
     * @param response
     * @throws Exception
     */
    @Override
    public void onSuccess(Response response) throws Exception {
        String str = response.body().string();
        Logger.i(str);
        onSuccess(str);
    }

    /**
     * JSONObject jsonObject = new JSONObject(result)
     *
     * @param result
     * @throws Exception
     */
    public abstract void onSuccess(String result) throws Exception;

    /**
     * 请求进度显示
     *
     * @param progress 目前进度值
     * @param total    总进度值
     */
    @Override
    public void onProgress(float progress, long total) {

    }
}
