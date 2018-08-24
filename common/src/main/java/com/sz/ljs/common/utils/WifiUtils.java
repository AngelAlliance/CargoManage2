package com.sz.ljs.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Administrator on 2018/8/24.
 */

public class WifiUtils {

    /**
     * 获取当前网络连接的ip地址
     * 原生
     *
     * @param context
     * @return
     */
    public static String getConnectedIp(Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            DhcpInfo di = wifiManager.getDhcpInfo();
            long getewayIpL = di.gateway;
            return long2ip(getewayIpL);
        }
        return "";
    }

    //长整型转化为IP地址
    public static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }
}
