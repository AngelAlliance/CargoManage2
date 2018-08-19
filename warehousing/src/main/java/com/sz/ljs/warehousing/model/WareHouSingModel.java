package com.sz.ljs.warehousing.model;

import java.util.ArrayList;
import java.util.List;

//TODO 入库数据模型(为了整体操作方便)
public class WareHouSingModel {

    //定义单例句柄
    private static WareHouSingModel Instance;

    public static WareHouSingModel getInstance() {
        if (null == Instance) {
            Instance = new WareHouSingModel();
        }
        return Instance;
    }

    private List<ServiceModel> serviceModelList = new ArrayList<>();

    public void setServiceModelList(List<ServiceModel> list) {
        List<ServiceModel> list1 = new ArrayList<>();
        list1.addAll(list);
        serviceModelList.clear();
        serviceModelList.addAll(list1);
    }

    public void removeServiceModel(int position) {
        if (serviceModelList.size() > position) {
            serviceModelList.remove(position);
        }
    }
}
