package com.sz.ljs.common.base;

/**
 * Created by 侯晓戬 on 2017/11/16.
 * 下载文件回调
 */

public interface IHttpDownFileCallBack {
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
     * @throws Exception
     */
    void onSuccess(String out_path) throws Exception;

    /**
     * 请求进度显示
     *
     * @param progress 目前进度值(百分比的)
     */
    void onProgress(int progress);
}
