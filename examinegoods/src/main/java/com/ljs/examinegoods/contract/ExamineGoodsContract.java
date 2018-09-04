package com.ljs.examinegoods.contract;

import com.ljs.examinegoods.model.SaveDeteTionOrderRequestModel;
import com.sz.ljs.base.interfacecallback.IGenView;
import com.sz.ljs.common.utils.MD5Util;

/**
 * Created by Administrator on 2018/8/20.
 */

public interface ExamineGoodsContract {

    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");
    public static final String NUMBER = "number";   //运单号码
    public static final String SUMMARY = "summary";  //zhbg_ips2018_cn得MD532位小写加密
    public static final String DETECTIONNAME = "detection_name";
    public static final String REFERENCE_NUMBER = "reference_number";  //客户参考单号
    public static final String REQUEST_TYPE = "request_type";          //问题件还是不是问题件 Y问题件N不是问题件
    public static final String DETECTION_NOTE = "detection_note";      // 检测结果 如（带电，没有发票……….）
    public static final String IMAGE_LIST = "image_list";               //图片集合 LISt<string>
    public static final String ORDER_ID = "order_id";                // 订单id
    public static final String USERID = "userId";                    //用户id
    public static final String QUEST_NOTE = "quest_note";                    //问题描述
    public static final String HEXADECIMAL_STR = "hexadecimal_str";                    //十六进制字符串

    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int GETORDERBYNUMBER_SUCCESS= 1;//根据订单号查询订单信息
    public static final int GET_ITEM_TYPE_SUCCESS = 2;//查询所有得货物类型
    public static final int GET_DETECTIONBY_SUCCESS = 3;//根据货物类型差检查项
    public static final int SAVE_DETECTIONORDER_SUCCESS = 4;//添加问题件或者保存验货结果
    public static final int UPLOAD_FILE_SUCCESS = 5;//图片上传

    interface View extends IGenView {

    }

    interface Presenter {
        //TODO 根据订单号查询订单信息
        public void getOrderByNumber(String number);
        //TODO 查询所有得货物类型
        public void getItemType();
        //TODO 根据货物类型差检查项  detection_name:货物类型中文名称
        public void getDetectionBy(String detection_name);
        //TODO 添加问题件或者保存验货结果
        public void saveDetecTionOrder(SaveDeteTionOrderRequestModel requestModel);
        //TODO 图片上传
        public void uploadFile(String imageUrl);
    }
}
