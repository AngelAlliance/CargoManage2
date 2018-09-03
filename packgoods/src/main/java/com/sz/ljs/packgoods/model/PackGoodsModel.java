package com.sz.ljs.packgoods.model;


import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.GsonDepltListModel;

import java.util.ArrayList;
import java.util.List;

//TODO 打包界面数据单利模型缓存类
public class PackGoodsModel {

    //定义单例句柄
    private static PackGoodsModel Instance;

    public static PackGoodsModel getInstance() {
        if (null == Instance) {
            Instance = new PackGoodsModel();
        }
        return Instance;
    }



    private List<ExpressPackageModel> ShppingCnList = new ArrayList<>();
    private List<ExpressModel> BaleList = new ArrayList<>();
    private List<GsonServiceChannelModel.DataBean> serviceChannelList = new ArrayList<>();
    private GsonAddBussinessPackageModel.DataBean bussinessPackageModel;

    public List<ExpressPackageModel> getShppingCnList() {
        return ShppingCnList;
    }

    public void setShppingCnList(List<ExpressPackageModel> list) {
        List<ExpressPackageModel> lists = new ArrayList<>();
        lists.addAll(list);
        ShppingCnList.clear();
        ShppingCnList.addAll(lists);
    }

    public List<ExpressModel> getBaleList() {
        return BaleList;
    }

    public void setBaleList(List<ExpressModel> list) {
        List<ExpressModel> lists = new ArrayList<>();
        lists.addAll(list);
        BaleList.clear();
        BaleList.addAll(lists);
    }

    public List<GsonServiceChannelModel.DataBean> getServiceChannelList() {
        return serviceChannelList;
    }

    public void setServiceChannelList(List<GsonServiceChannelModel.DataBean> list) {
        List<GsonServiceChannelModel.DataBean> list1=new ArrayList<>();
        list1.addAll(list);
        serviceChannelList.clear();
        serviceChannelList.addAll(list1);
    }

    public GsonAddBussinessPackageModel.DataBean getBussinessPackageModel() {
        return bussinessPackageModel;
    }

    public void setBussinessPackageModel(GsonAddBussinessPackageModel.DataBean bussinessPackageModel) {
        this.bussinessPackageModel = bussinessPackageModel;
    }
}
