package com.sz.ljs.common.constant;

import com.sz.ljs.common.utils.MD5Util;

public class ApiUrl {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");

    public static final String NUMBER = "number";   //运单号码
    public static final String USERID = "userId";   //用户名
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密

    //TODO 根据单号查询运单数据(盘库模块使用)
    public static final String FIND_EXPRESS_BYID = "user/FindExpressByID";

    //TODO 批量提交盘货数据
    public static final String ADD_EXPRESSS_TRACK="user/AddExpressTrack";
}
