package com.sz.ljs.common.constant;

import com.sz.ljs.common.utils.MD5Util;

public class ApiUrl {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");

    public static final String NUMBER = "number";   //运单号码
    public static final String USERID = "userId";   //用户名
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密

    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    public static final String GET_DEPLT_LIST = "user/GetDepltList";

    //TODO 把运单从某个包提出
    public static final String BAG_PUT_BUSINESS = "user/BagPutBusiness";

    //TODO 称量包的重量
    public static final String BAG_WEIFHING = "user/BagWeighing";

    //TODO 生效渠道
    public static final String GET_SERVICECHANNEL = "user/GetServiceChannel";

    //TODO 运单打包
    public static final String ADD_BUSSINESS_PACKAGE = "user/AddBussinessPackage";

    //TODO 拆包
    public static final String UNPACKING = "user/Unpacking";

    //TODO 根据单号查询运单数据(盘库模块使用)
    public static final String FIND_EXPRESS_BYID = "user/FindExpressByID";

    //TODO 批量提交盘货数据
    public static final String ADD_EXPRESSS_TRACK = "user/AddExpressTrack";

    //TODO 查看收货服务商跟机构
    public static final String GET_ORG_SERVER = "user/GetOrgServer";

    //TODO 生效渠道
    public static final String GET_SERVICE_CHANNEL = "user/GetServiceChannel";

    //TODO 出库生成主单 user/SaveTransportBatchAndBusiness
    public static final String SAVE_TRANSPORTBATCH_AND_BUSINESS = "user/SaveTransportBatchAndBusiness";

    //TODO 根据订单号查询订单信息 user/GetOrderbyNumber
    public static final String GET_ORDERBY_NUMBER = "user/GetOrderbyNumber";

    //TODO 查询所有得货物类型
    public static final String GET_ITEM_TYPE = "user/GetItemType";

    //TODO 根据货物类型差检查项
    public static final String GET_DETECTION_BY = "user/GetDetectionBy";

    //TODO 添加问题件或者保存验货结果
    public static final String SAVE_DETECTION = "user/SaveDetection";

    //TODO 图片上传
    public static final String UPLOAD_FILE = "user/UploadFile";

    //TODO 获取杂费项
    public static final String GET_EXTRASERVICE = "user/GetExtraservice";

    //TODO 查询国家
    public static final String GET_GETCOUNTRY = "user/GetCountry";

    //TODO 查询生效得销售产品
    public static final String GET_GETPRODUCT = "user/GetProduct";

    //TODO 根据客户名称查询客户资料
    public static final String GET_GETCUSTOMER = "user/GetCustomer";

    //TODO 入库时选择客户生成到货总单
    public static final String SELECT_CURRENTDAY_BATCH = "user/SelectCurrentDayBatch";

    //TODO 查询材积重、计费重
    public static final String CALCULATION_VOLUMEWEIGHT = "user/CalculationVolumeWeight";

    //TODO 入库接口
    public static final String CHENCK_IN = "user/ChenckIn";

}
