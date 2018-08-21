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
}
