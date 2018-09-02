package com.sz.ljs.common.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.orhanobut.logger.Logger;
import com.sz.ljs.common.base.IHttpDownFileCallBack;
import com.sz.ljs.common.base.IResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 2017/7/7
 * wangxiaoer
 * 功能描述：Okhttp原始封装
 **/
public class HttpUtils {
    private final static String TAG = "HttpUtils";
    private final static int DEF_POST_PARAM_TYPE = 1;// 0:表格格式，1:json格式 2 json格式数组
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * okhttpclient实例
     */
    private static OkHttpClient mOkHttpClient;
    /**
     * 获取取消请求的Call对象
     */
    protected static Class<?> tag;
    /**
     * 请求集合: key=Activity value=Call集合
     */
    private static Map<Class<?>, List<Call>> callsMap = new ConcurrentHashMap<Class<?>, List<Call>>();
    /**
     * 超时.未设置时，默认为零
     */
    private static long timeconnect = 0;
    private static long timeread = 0;
    private static long timewrite = 0;
    private static SparseArray<Call> callsList = new SparseArray<>();

    /**
     * 构造方法
     */
    private static OkHttpClient getHttpClient() {
        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient();
        }
        /**
         * 在这里直接设置连接超时.读取超时，写入超时
         * 设置了超时时间（即超时时间补为零），则按设置使用
         * 否则默认为2000L
         */
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();
        if (timeconnect == 0) {
            builder.connectTimeout(12L, TimeUnit.SECONDS);
        } else {
            builder.connectTimeout(timeconnect, TimeUnit.SECONDS);
        }
        if (timeread == 0) {
            builder.readTimeout(20L, TimeUnit.SECONDS);
        } else {
            builder.readTimeout(timeread, TimeUnit.SECONDS);
        }
        if (timewrite == 0) {
            builder.writeTimeout(20L, TimeUnit.SECONDS);
        } else {
            builder.writeTimeout(timewrite, TimeUnit.SECONDS);
        }
        mOkHttpClient = builder.build();
        return mOkHttpClient;
    }

    /**
     * 超时请求时间设置
     *
     * @param timeconnectout 连接超时
     * @param timereadout    读取超时
     * @param timewriteout   写入超时
     */
    public static void setTimeOut(long timeconnectout, long timereadout, long timewriteout) {
        timeconnect = timeconnectout;
        timeread = timereadout;
        timewrite = timewriteout;
    }

    //-------------------------get请求数据--------------------------



    //-------------------------post请求--------------------------

    /**
     * @param url      请求地址
     * @param params   请求参数集合
     * @param callBack 回调接口
     */
    public static void post(String url,String token, Map<String, String> params,
                            final IResponseHandler callBack) {
        Map<String, String> header = new HashMap<>();
        header.put("token", token);
        post(DEF_POST_PARAM_TYPE,url, header, params, callBack);
    }
    /**
     * @param url      请求地址
     * @param params   请求参数集合
     * @param callBack 回调接口
     */
    public static void get(String url, Map<String, String> params,
                            final IResponseHandler callBack) {
        Map<String, String> header = new HashMap<>();
        header.put("Accept-Language", Utils.getLanguage());
        get(url, header, params, callBack);
    }

    /**
     * @param url      请求地址
     * @param callBack 回调接口
     */
    public static int get(String url,Map<String, String> headparam, Map<String, String> params, final IResponseHandler callBack) {
        String get_content = "";
        if (params == null) {
            params = new HashMap<>();
        }
        //请求头处理
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (null != headparam) {
            for (Map.Entry<String, String> header : headparam.entrySet()) {
                headersbuilder.add(header.getKey(), header.getValue());
            }
            headers = headersbuilder.build();
        }
        for (Map.Entry<String, String> map : params.entrySet()) {
            //把key和value添加到formbody中
            get_content = get_content + map.getKey() + "=" + (map.getValue() == null ? "" : map.getValue()) + "&";
        }
//        //参数处理
//        RequestBody requestBody = null;
//        if(POST_PARAM_TYPE == 0) {
//            FormBody.Builder builder = new FormBody.Builder();
//            for (Map.Entry<String, String> map : params.entrySet()) {
//                //把key和value添加到formbody中
//                builder.add(map.getKey(), map.getValue() == null ? "" : map.getValue());
//            }
//            requestBody = builder.build();
//        }else if(POST_PARAM_TYPE == 1){
//            JSONObject jsonObject = new JSONObject(params);
//            String json = jsonObject.toString();
//            Logger.i("JSONString:%s",json);
//            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
//        }
        Logger.i(get_content);
        final Request request = new Request.Builder().headers(headers).url(url + "?" + get_content).method("GET", null).build();
        setCall(callsList.size(), getHttpClient().newCall(request)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(0, e.getMessage());
                }
                int count = callsList.indexOfValue(call);
                Logger.e("onFailure() count:" + count);
                callsList.remove(count);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.i(TAG, "onResponse " + response.request().url().url().toString());
                    callBack.onSuccess(response);
                } catch (IOException e) {
                    callBack.onFailure(0, e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int count = callsList.indexOfValue(call);
                Logger.e("onResponse() count:" + count);
                callsList.remove(count);
            }
        });
        //因为添加后数组会增加，不是添加前的值
        return callsList.size() - 1;
    }
    /**
     * @param url      请求地址
     * @param params   请求参数集合
     * @param callBack 回调接口
     */
    public static void post(int param_type, String url, Map<String, String> params,final IResponseHandler callBack) {
        Map<String, String> header = new HashMap<>();
        header.put("Accept-Language", Utils.getLanguage());
        post(param_type,url, header, params, callBack);
    }


    /**
     * @param url      请求地址
     * @param params   请求参数集合
     * @param callBack 回调接口
     */
    public static int post(int post_type, String url, Map<String, String> headparam, Map<String, String> params,
                           final IResponseHandler callBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        //请求头处理
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (null != headparam) {
            for (Map.Entry<String, String> header : headparam.entrySet()) {
                headersbuilder.add(header.getKey(), header.getValue());
            }
            headers = headersbuilder.build();
        }
        //参数处理
        RequestBody requestBody = null;
        if(post_type == 0) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> map : params.entrySet()) {
                //把key和value添加到formbody中
                builder.add(map.getKey(), map.getValue() == null ? "" : map.getValue());
            }
            requestBody = builder.build();
        }else if(post_type == 1 || post_type == 2){
            JSONObject jsonObject = new JSONObject(params);
            String json = null;
            if(post_type == 2){
                JSONArray jsonArray =new JSONArray();
                jsonArray.put(jsonObject);
                json = jsonArray.toString();
            }else {
                json = jsonObject.toString();
            }
            Logger.i("JSONString:%s",json);
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        }
        // 请求对象
        final Request request = new Request
                .Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();
        Log.i(TAG, "post() url=" + url + "call index = " + callsList.size());
        setCall(callsList.size(), getHttpClient().newCall(request)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(0, e.getMessage());
                }
                int count = callsList.indexOfValue(call);
                callsList.remove(count);
                Logger.i("onFailure() count:" + count);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    int count = callsList.indexOfValue(call);
                    Log.i(TAG, "onResponse URL=" + response.request().url().url().toString() + "call index = " + count);
                    callBack.onSuccess(response);
                    callsList.remove(count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //因为添加后数组会增加，不是添加前的值
        return callsList.size() - 1;
    }

    /**
     * post上传同时存在图片和文字
     * @param url
     * @param paramsMap
     * @param callBack
     * @return
     */
    public static int filePost(String url, HashMap<String, Object> paramsMap, final IResponseHandler callBack){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        //追加参数
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            if(object instanceof File) {
                File file = (File) object;
                RequestBody requestBody = RequestBody.create(null, file);
                String fileName = "file-" + key + "." + FileUtils.getFileSuffixName(file);
                builder.addFormDataPart(key, fileName, requestBody);
                Log.i("hou", "filePost: fileName=" + fileName);
            }else if (object instanceof String){
                builder.addFormDataPart(key, (String) object);
            }else{
                builder.addFormDataPart(key, object.toString());
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        final Request request = new Request.Builder().url(url).post(body).build();
        //单独设置参数 比如读取超时时间
//        setTimeOut(150L,150L,150L);
        setCall(callsList.size(), getHttpClient().newCall(request)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(0, e.getMessage());
                }
                int count = callsList.indexOfValue(call);
                callsList.remove(count);
                Logger.i("onFailure() count:" + count);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.i(TAG, "onResponse " + response.request().url().url().toString());
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int count = callsList.indexOfValue(call);
                Logger.d("onSuccess() count:" + count);
                callsList.remove(count);
            }
        });
//        setTimeOut(0,0,0);//恢复默认
        //因为添加后数组会增加，不是添加前的值
        return callsList.size() - 1;
    }

    //-------------------------文件上传--------------------------

    /**
     * @param URL      上传地址
     * @param FileUrl  文件地址（需要上传的）
     * @param callBack 回调接口
     */
    public static int uppost(String URL, String FileUrl, final IResponseHandler callBack) {
        //根据文件路径创建File对象
        File file = new File(FileUrl);
        final Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(MEDIA_TYPE_PLAIN, file))
                .build();
        //上传请求
        setCall(callsList.size(), getHttpClient().newCall(request)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(0, e.getMessage());
                }
                int count = callsList.indexOfValue(call);
                Logger.e("onFailure() count:" + count);
                callsList.remove(count);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int count = callsList.indexOfValue(call);
                Logger.e("onSuccess() count:" + count);
                callsList.remove(count);
            }

        });
        //因为添加后数组会增加，不是添加前的值
        return callsList.size() - 1;
    }
    //-------------------------文件下载--------------------------

    /**
     * @param url      下载地址
     * @param desDir   目标地址
     * @param callBack
     */
    public static int download(final String url, final String desDir, @NonNull final IHttpDownFileCallBack callBack) {
        final Request request = new Request.Builder().url(url).method("GET", null).build();
        setCall(callsList.size(), getHttpClient().newCall(request)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(0, e.getMessage());
                int count = callsList.indexOfValue(call);
                Logger.e("count:" + count);
                callsList.remove(count);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //在这里进行文件的下载处理
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                long total = response.body().contentLength();//文件流总长度
                long sum = 0; //文件现长度
                try {
                    File file = new File(desDir, getFileName(url));//文件名和目标地址
                    inputStream = response.body().byteStream();//把请求回来的response对象装换为字节流
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    int progress = 0;
                    byte[] bytes = new byte[40960];
                    while ((len = inputStream.read(bytes)) != -1) { //循环读取数据
                        fileOutputStream.write(bytes, 0, len);
                        sum += len;
                        progress = (int) (((sum * 1.0f) / total) * 100);
//                        Log.i("hou","download() " + total + " " + sum + " " + progress);
                        callBack.onProgress(progress);
                    }
                    fileOutputStream.flush();//关闭文件输出流
                    callBack.onSuccess(file.getAbsolutePath());//调用分发数据成功的方法
                }catch (Exception e){
                    callBack.onFailure(0, e.getMessage());
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
                int count = callsList.indexOfValue(call);
                Logger.e("count:" + count);
                callsList.remove(count);
            }
        });
        //因为添加后数组会增加，不是添加前的值
        return callsList.size() - 1;
    }

    //-------------------------设置Call--------------------------

    public static Call setCall(int index, Call call) {
        callsList.put(index, call);
        return call;
    }

    //-------------------------取消请求--------------------------
    public static void CancelHttp(int index) {
        if (callsList != null) {
            callsList.get(index).cancel();
        }
    }

    //-------------------------其他方法--------------------------

    /**
     * 根据文件url获取文件的路径名字
     *
     * @param url
     * @return
     */
    private static String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        String path = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
        return path;
    }

}
