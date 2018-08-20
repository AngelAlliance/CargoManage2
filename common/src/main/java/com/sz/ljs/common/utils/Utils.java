package com.sz.ljs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.base.interfacecallback.IGeneralCallBack;
import com.sz.ljs.common.R;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by 侯晓戬 on 2017/7/10.
 * 通用性接口
 */

public class Utils {
    private final static String TAG = "Utils";
    private final static String SH_APP_NAME_UTILS = "COM_QILOO_SMARTCARD_SH_UTILS";
    private final static String SH_KEY_SAVE_VER_NUMBER = "SH_KEY_VER_NUM";//本地存储的版本号
    //////////////////////////////////////
    // 异步加载网络图片
    /////////////////////////////////////
    public static void loadDrawable(final Context context, final String url, final IGeneralCallBack cb){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Resources rs = context.getResources();
                try {
                    Drawable bmp = Drawable.createFromResourceStream(rs, null,
                            new URL(url).openStream(),"src");
                    if(null != cb){
                        cb.OnResult(bmp, null);
                    }
                } catch (IOException e) {
                    Logger.w("loadDrawable() url=" + url + " Error:" + e.getMessage());
                    if(null != cb){
                        cb.OnResult(null, null);
                    }
                }
            }
        }).start();
    }

    ///////////////////////////////////
    // 获取当前手机语言
    ////////////////////////////////////
    public static String getLanguage(){
        return Locale.getDefault().getLanguage();
    }

    ///////////////////////////////////////////////////
    // 本地存储的版本号
    //////////////////////////////////////////////////
    public final static int DEF_APP_VER_NUMBER = 99;
    public static void setShKeySaveVerNumber(Context context , int version){
        SharedPreferences sp = context.getSharedPreferences(SH_APP_NAME_UTILS,
                Context.MODE_PRIVATE);
        sp.edit().putInt(SH_KEY_SAVE_VER_NUMBER, version).commit();
    }

    public static int getShKeySaveVerNumber(Context context){
        int  ver = 0;
        SharedPreferences sp = context.getSharedPreferences(SH_APP_NAME_UTILS,
                Context.MODE_PRIVATE);
        ver = sp.getInt(SH_KEY_SAVE_VER_NUMBER, DEF_APP_VER_NUMBER);
        return ver;
    }

    /////////////////////////////////////////////////
    // 获取当前程序版本号
    /////////////////////////////////////////////////
    public static int getAppVersion(Context context){
        int ver = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            ver = info.versionCode;
        }catch (Exception e){
            Logger.e(e.getMessage());
        }
        return ver;
    }

    //TODO 获取版本名称
    public static String getVersionName(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo pi = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0.0.0";
    }
    public interface IToastCallBack{
        public final static int STATE_CLICK = 1;
        public final static int STATE_DISMISSED = 2;

        public void onStateChang(int state);
    }

    public static void showToast(BaseActivity activity, String string, int duration,
                                 final IToastCallBack callBack){
        View v = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(v, string, duration);
        snackbar.setAction(R.string.str_i_know, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != callBack) {
                    callBack.onStateChang(IToastCallBack.STATE_CLICK);
                }
                snackbar.dismiss();
            }
        });
        snackbar.addCallback( new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if(null != callBack) {
                    callBack.onStateChang(IToastCallBack.STATE_DISMISSED);
                }
                super.onDismissed(transientBottomBar, event);
            }
        });
        snackbar.show();
    }

    public static void showToast(BaseActivity activity, String string,
                                 final IToastCallBack callBack){
        showToast(activity, string, Snackbar.LENGTH_SHORT,callBack);
    }

    public static void showToast(BaseActivity activity, String string){
        showToast(activity, string, null);
    }

    public static void showToast(BaseActivity activity, int strid){
        showToast(activity, activity.getString(strid));
    }

    //网络地址Http头检查和处理
    public static String UriHttpHeadHandler(String uri){
        if (!uri.startsWith("http://")){
            uri = "http://" + uri;
        }
        Logger.d(uri);
        return uri;
    }

    //TODO 截取当前界面效果
    public static Bitmap snapShotWithoutStatusBar(BaseActivity activity){
        Bitmap bitmap = null;
        View view = activity.getWindow().getDecorView();
        try {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            bitmap = view.getDrawingCache();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int width = ScreenUtil.getScreenWidth(activity);
            int height = ScreenUtil.getScreenHeight(activity);
            bitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width,height - statusBarHeight);
            view.destroyDrawingCache();
        }catch (Exception e){
            Logger.e(e.getMessage());
            bitmap = getScreenshot(view);
        }
        return bitmap;
    }

    private static Bitmap getScreenshot(View v){
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
            Canvas c = new Canvas(bitmap);
            v.draw(c);
        }catch (Exception e){
            Logger.e(e.getMessage());
        }
        return bitmap;
    }

    //TODO 判断apk当前是否是debug模式
    public static boolean isDebugMode() {
        try {
            Context context = BaseApplication.getAppContext();
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    //TODO 时长MS转字符串显示
    public static String getTimeString(int duration){
        int hour = 0;
        int min =0;
        int sec = 0;
        int value = duration /1000;
        hour = value /(60*60);
         if(hour > 0) {
             min = (value - (hour * 60 * 60)) / 60;
             sec = value - (hour *60*60) -(min*60);
             return String.format(Locale.getDefault(),"%02d:%02d:%02d",hour, min, sec);
         }else{
             min = value / 60;
             sec = value -(min*60);
             return String.format(Locale.getDefault(),"%02d:%02d", min, sec);
         }
    }

    //TODO 获取音频时间长度
    public static int getDuration(String url){
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    //TODO 字符转整数的处理
    public static int StringParseInt(String string){
        int number = 0;
        try {
            if(!TextUtils.isEmpty(string)) {
                number = Integer.parseInt(string);
            }
        }catch (NumberFormatException e){
            Logger.i("stringParseInt = " + e.getMessage());
        }
        return number;
    }

}
