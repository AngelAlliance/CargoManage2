package com.sz.ljs.common.base;

import okhttp3.Response;

/**
 * 2017
 * 07
 * 2017/7/21
 * wangxiaoer
 * 功能描述：
 **/
public interface IResponseHandler {

    /**
     * 请求失败
     *
     * @param statusCode
     * @param error_msg
     */
    void onFailure(int statusCode, String error_msg);

    /**
     * 请求成功(需转换)
     * JSONObject jsonObject = new JSONObject(result)
     *String result=response.body().string();
     * @param response
     * @throws Exception
     */
    void onSuccess(Response response) throws Exception;

    /**
     * 请求进度显示
     *
     * @param progress 目前进度值
     * @param total    总进度值
     */
    void onProgress(float progress, long total);
}
