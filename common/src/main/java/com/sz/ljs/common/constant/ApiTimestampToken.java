package com.sz.ljs.common.constant;


import com.sz.ljs.common.utils.MD5Util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 侯晓戬 on 2017/8/24.
 * 参数，时间戳校验处理
 */

public final class ApiTimestampToken {
    //通用数据键值
    public static final String GENERAL_KEY_USER_ID = "UserId";
    public static final String GENERAL_KEY_TOKEN = "Token";
    public static final String GENERAL_KEY_TIMESTAMP = "Timestamp";

    private static int UserID = 0;
    private static String Token= "";
    //设置登录的用户ID
    public static void setUserID(int userid){
        UserID = userid;
    }

    //TODO 设置登录返回的token
    public static void setToken(String token){
        Token=token;
    }
    //配置时间戳校验参数
    public static void configTimestampToken(Map<String, String> param){
        param.put(GENERAL_KEY_USER_ID, "" + UserID);
        param.put(GENERAL_KEY_TIMESTAMP, getTimestampString());
        param.put(GENERAL_KEY_TOKEN, "" +Token);
    }


    //配置时间戳校验参数
    public static void configTimestampToken2(Map<String, Object> param){
        param.put(GENERAL_KEY_USER_ID, "" + UserID);
        param.put(GENERAL_KEY_TIMESTAMP, getTimestampString());
        param.put(GENERAL_KEY_TOKEN, Token);
    }

    //获取时间戳值
    public static String getTimestampString(){
        return ""+(System.currentTimeMillis() / 1000);
    }

    //计算校验数据
    public static String calculationToken(Map<String, String> param){
        String strRet = "";
        Map<String, String> sort = sortMapByKey(param);
        strRet = MapToString(sort);
        strRet = MD5Util.md5Encode(strRet);
        return strRet;
    }

    //计算校验数据
    public static String calculationToken2(Map<String, Object> param){
        String strRet = "";
        Map<String, Object> sort = sortMapByKey2(param);
        strRet = MapToString2(sort);
        strRet = MD5Util.md5Encode(strRet);
        return strRet;
    }

    //按KEY排序
    public static Map<String, String> sortMapByKey(Map<String, String> map){
        if (map == null || map.isEmpty()){
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        sortMap.put("Uid", "36768082"); //附加的Uid=36768082
        return sortMap;
    }
    //按KEY排序
    public static Map<String, Object> sortMapByKey2(Map<String, Object> map){
        if (map == null || map.isEmpty()){
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        sortMap.putAll(map);
        sortMap.put("Uid", "36768082"); //附加的Uid=36768082
        return sortMap;
    }

    //MAP转字符串
    public static String MapToString(Map<String, String> map){
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> val : map.entrySet()) {
            buffer.append(val.getKey());
            buffer.append("=");
            buffer.append(val.getValue()==null?"":val.getValue());
        }
        return buffer.toString();
    }
    //MAP转字符串
    public static String MapToString2(Map<String, Object> map){
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> val : map.entrySet()) {
            buffer.append(val.getKey());
            buffer.append("=");
            buffer.append(val.getValue()==null?"":val.getValue());
        }
        return buffer.toString();
    }

    static  class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
