package com.sz.ljs.common.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * json类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class JsonResponseHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();


            onFailure(response.code(), e.getMessage());

            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;

        try {
            final Object result = new JSONTokener(finalResponseBodyStr).nextValue();
            if (result instanceof JSONObject) {

                onSuccess(response.code(), (JSONObject) result);

            } else if (result instanceof JSONArray) {

                onSuccess(response.code(), (JSONArray) result);

            } else {

                onFailure(response.code(), finalResponseBodyStr);

            }
        } catch (JSONException e) {
            e.printStackTrace();

            onFailure(response.code(), finalResponseBodyStr);

        }
    }

    public abstract void onSuccess(int statusCode, JSONObject response);

    public void onSuccess(int statusCode, JSONArray response) {

    }

    @Override
    public void onProgress(float progress, long total) {

    }
}