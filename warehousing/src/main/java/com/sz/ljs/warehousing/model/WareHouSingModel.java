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
    private List<CustomerModel.DataEntity> customerList=new ArrayList<>();
    private List<SubnitModel> subnitList=new ArrayList<>();
    private double volume = 0;  //总材积
    private double TotalVolumeWeight;//总材积重
    private double TotalChargeWeight;   //总计费重
    private double TotalGrossWeight;   //总实重


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

    public void setCustomerList(List<CustomerModel.DataEntity> list){
        List<CustomerModel.DataEntity> list1 = new ArrayList<>();
        list1.addAll(list);
        customerList.clear();
        customerList.addAll(list1);
    }

    public CustomerModel.DataEntity getCustomerModel(int customer_id){
        CustomerModel.DataEntity model=new CustomerModel.DataEntity();
        if(null!=customerList&&customerList.size()>0){
            for (CustomerModel.DataEntity bean:customerList){
                if(customer_id==bean.getCustomer_id()){
                    model= bean;
                    break;
                }
            }
        }
        return model;
    }

    public List<SubnitModel> getSubnitList() {
        return subnitList;
    }

    public void setSubnitList(List<SubnitModel> list) {
        List<SubnitModel> list1=new ArrayList<>();
        list1.addAll(list);
        subnitList.clear();
        subnitList.addAll(list1);
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTotalVolumeWeight() {
        return TotalVolumeWeight;
    }

    public void setTotalVolumeWeight(double totalVolumeWeight) {
        TotalVolumeWeight = totalVolumeWeight;
    }

    public double getTotalChargeWeight() {
        return TotalChargeWeight;
    }

    public void setTotalChargeWeight(double totalChargeWeight) {
        TotalChargeWeight = totalChargeWeight;
    }

    public double getTotalGrossWeight() {
        return TotalGrossWeight;
    }

    public void setTotalGrossWeight(double totalGrossWeight) {
        TotalGrossWeight = totalGrossWeight;
    }

}
