package com.sz.ljs.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;
import android.posapi.PosApi;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 刘劲松 on 2018/8/8.
 * App基类
 */

public class BaseApplication extends Application {
    //全局静态应用上下文对象
    private static Context AppContext;
    private static DisplayImageOptions options = null;
    //腾讯bugly Appid
    public final String BUGLY_APP_ID = "112ab15512";//8ec6cc34-f6b4-4dc2-9e12-25f82cf6e666
    public final String XUNFEI_ID="5b7a7165";
    static BaseApplication instance = null;
    //PosSDK mSDK = null;
    static PosApi mPosApi = null;
    private static String mCurDev1 = "";
    public BaseApplication(){
        super.onCreate();
        instance = this;
    }

    public static  BaseApplication getInstance(){
        if(instance==null){
            instance =new BaseApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        setBugly();
        handlerDetectFileUriExposure();
        initImageLoader();
        startMainService();
        SpeechUtility a=	SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID+"=5b7a7165");
        Log.i("初始化讯飞","a="+a);
        Logger.addLogAdapter(new AndroidLogAdapter());
        //PosApi类初始化，该类为项目核心类，请注意务必实例化，否则将会出现无法打印和扫描等现象
//        mPosApi = PosApi.getInstance(this);
//        //根据型号进行初始化mPosApi类
//        if (Build.MODEL.contains("LTE")||android.os.Build.DISPLAY.contains("3508")||
//                android.os.Build.DISPLAY.contains("403")||
//                android.os.Build.DISPLAY.contains("35S09")) {
//            mPosApi.initPosDev("ima35s09");
//            setCurDevice("ima35s09");
//        } else if(Build.MODEL.contains("5501")){
//            mPosApi.initPosDev("ima35s12");
//            setCurDevice("ima35s12");
//        }else{
//            mPosApi.initPosDev(PosApi.PRODUCT_MODEL_IMA80M01);
//            setCurDevice(PosApi.PRODUCT_MODEL_IMA80M01);
//        }
    }

    public String getCurDevice() {
        return mCurDev1;
    }
    public static  void setCurDevice(String mCurDev) {
        mCurDev1 = mCurDev;
    }
    //TODO 其他地方引用mPosApi变量
    public PosApi getPosApi(){
        return mPosApi;
    }

    //TODO 启动主服务
    private void startMainService(){
        Intent service = new Intent("COM.LJS.SMARTCARD.MAIN_SERVICES");
        service.setPackage(getPackageName());
        startService(service);
    }

    //TODO 初始化Bugly
    private void setBugly() {
        if (!debugMode()) {
            // 获取当前包名
            String packageName = getApplicationContext().getPackageName();
            // 获取当前进程名
            String processName = getProcessName(android.os.Process.myPid());
            // 设置是否为上报进程
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            // 初始化Bugly
            CrashReport.initCrashReport(getApplicationContext(), BUGLY_APP_ID, debugMode(), strategy);
        }
    }

    //TODO 初始化 ImageLoader 类
    private void initImageLoader() {
        if (null == options) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.user_info_def_icon) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.user_info_def_icon) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.user_info_def_icon) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                    .build();
            File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .tasksProcessingOrder(QueueProcessingType.FIFO) //设置图片下载和显示的工作队列排序
                    .memoryCacheExtraOptions(480, 800)
                    // CompressFormat.PNG类型，70质量（0-100）
                    .memoryCache(new WeakMemoryCache())
                    .memoryCacheSize(2 * 1024 * 1024) // 缓存到内存的最大数据
                    // 缓存在文件的图片的宽和高度
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheExtraOptions(960, 1280, null)
                    .diskCacheSize(50 * 1024 * 1024) // 缓存到文件的最大数据
                    .diskCacheFileCount(1000) // 文件数量
                    .defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置
                    build();
            ImageLoader.getInstance().init(config); // 初始化
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    //TODO 获取 ImageLoader 的配置选项 DisplayImageOptions
    public static DisplayImageOptions getDisplayImageOptions() {
        return options;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //TODO FileUriExposedException 问题
    private void handlerDetectFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    //TODO 判断apk当前是否是debug模式
    public static boolean debugMode() {
        try {
            Context context = BaseApplication.getAppContext();
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    //TODO 统一的启动ACTIVITY的方法
    public static void startActivity(Class<?> cls) {
        //FLAG_ACTIVITY_NEW_TASK注释掉打开界面会崩溃
        Log.i("hou", "BaseApplication.startActivity() " + cls);
        AppContext.startActivity(new Intent(AppContext, cls)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    //TODO 静态全局获取应用上下文对象
    public static Context getAppContext() {
        return AppContext;
    }

    public static String getAuthority(){
        return BaseApplication.getAppContext().getPackageName()+BaseApplication.getAppContext().getString(R.string.app_shard_file_provider_name);
    }

}
