package com.sz.ljs.common.constant;

import com.sz.ljs.common.utils.MD5Util;

public class ApiUrl {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");

    public static final String NUMBER = "number";   //运单号码
    public static final String USERID = "userId";   //用户名
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密

    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    public static final String GET_DEPLT_LIST="user/GetDepltList";

    //TODO 把运单从某个包提出
    public static final String BAG_PUT_BUSINESS="user/BagPutBusiness";

    //TODO 称量包的重量
    public static final String BAG_WEIFHING="user/BagWeighing";

    //TODO 生效渠道
    public static final String GET_SERVICECHANNEL="user/GetServiceChannel";

    //TODO 运单打包
    public static final String ADD_BUSSINESS_PACKAGE="user/AddBussinessPackage";

    //TODO 拆包
    public static final String UNPACKING="user/Unpacking";


    //TODO 根据单号查询运单数据(盘库模块使用)
    public static final String FIND_EXPRESS_BYID = "user/FindExpressByID";

    //TODO 批量提交盘货数据
    public static final String ADD_EXPRESSS_TRACK="user/AddExpressTrack";


    //TODO 查看收货服务商跟机构
    public static final String GET_ORG_SERVER="user/GetOrgServer";

    //TODO 生效渠道
    public static final String GET_SERVICE_CHANNEL="user/GetServiceChannel";

    //TODO 出库生成主单 user/SaveTransportBatchAndBusiness
    public static final String SAVE_TRANSPORTBATCH_AND_BUSINESS="user/SaveTransportBatchAndBusiness";
}
