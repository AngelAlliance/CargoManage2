package com.ljs.examinegoods.model;

import com.sz.ljs.common.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/9/4.
 * 验货模块临时缓存数据单例实体
 */

public class ExamineGoodsModel {
    //定义单例句柄
    private static ExamineGoodsModel Instance;

    public static ExamineGoodsModel getInstance() {
        if (null == Instance) {
            Instance = new ExamineGoodsModel();
        }
        return Instance;
    }

    private OrderModel orderModel;
    private List<ItemTypeModel.DataBean> itemTypeList = new ArrayList<>();
    private List<DetectionByModel.DataBean> detectionList = new ArrayList<>();
    private UploadFileResultModel fileResultModel;

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public List<ItemTypeModel.DataBean> getItemTypeList() {
        return itemTypeList;
    }

    public void setItemTypeList(List<ItemTypeModel.DataBean> list) {
        List<ItemTypeModel.DataBean> list1 = new ArrayList<>();
        list1.addAll(list);
        itemTypeList.clear();
        itemTypeList.addAll(list1);
    }

    public List<DetectionByModel.DataBean> getDetectionList() {
        return detectionList;
    }

    public void setDetectionList(List<DetectionByModel.DataBean> list) {
        List<DetectionByModel.DataBean> list1 = new ArrayList<>();
        list1.addAll(list);
        detectionList.clear();
        detectionList.addAll(list1);
    }

    public UploadFileResultModel getFileResultModel() {
        return fileResultModel;
    }

    public void setFileResultModel(UploadFileResultModel fileResultModel) {
        this.fileResultModel = fileResultModel;
    }
}
