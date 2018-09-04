package com.sz.ljs.warehousing.model;

import com.sz.ljs.common.model.OrderModel;

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
    private List<OrderModel.DataEntity.ExtraserviceEntity> extrasList=new ArrayList<>();
    private double volume = 0;  //总材积
    private double TotalVolumeWeight;//总材积重
    private double TotalChargeWeight;   //总计费重
    private double TotalGrossWeight;   //总实重

    private List<GsonIncidentalModel.DataBean> incidentalList=new ArrayList<>();
    private List<CountryModel.DataEntity> countryList=new ArrayList<>();
    private List<ProductModel.DataEntity> productList=new ArrayList<>();
    private List<CustomerModel.DataEntity> customerResultList=new ArrayList<>();
    private SelectCurrentDayBatchModel.DataEntity selectCurrentDayBatchModel;
    private CalculationVolumeWeightModel.DataEntity calculationVolumWeightModel;
    private OrderModel orderModel;
    private ChenckInModel.DataEntity chenckInResultModel;

    public void release(){
        serviceModelList.clear();
        customerList.clear();
        subnitList.clear();
        extrasList.clear();
        volume=0;
        TotalVolumeWeight=0;
        TotalChargeWeight=0;
        TotalGrossWeight=0;
        incidentalList.clear();
        countryList.clear();
        productList.clear();
        customerResultList.clear();
        selectCurrentDayBatchModel=null;
        calculationVolumWeightModel=null;
        orderModel=null;
        chenckInResultModel=null;
    }

    public void setServiceModelList(List<ServiceModel> list) {
        List<ServiceModel> list1 = new ArrayList<>();
        list1.addAll(list);
        serviceModelList.clear();
        serviceModelList.addAll(list1);
    }
    public List<ServiceModel> getServiceModelList(){
        return serviceModelList;
    }


    public void removeServiceModel(int position) {
        if (serviceModelList.size() > position) {
            serviceModelList.remove(position);
        }
    }

    public List<OrderModel.DataEntity.ExtraserviceEntity> getExtrasList() {
        return extrasList;
    }

    public void setExtrasList(List<OrderModel.DataEntity.ExtraserviceEntity> list) {
        List<OrderModel.DataEntity.ExtraserviceEntity> list1 = new ArrayList<>();
        list1.addAll(list);
        extrasList.clear();
        extrasList.addAll(list1);
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

    public List<CustomerModel.DataEntity> getCustomerList() {
        return customerList;
    }

    public List<GsonIncidentalModel.DataBean> getIncidentalList() {
        return incidentalList;
    }

    public void setIncidentalList(List<GsonIncidentalModel.DataBean> list) {
        List<GsonIncidentalModel.DataBean> list1=new ArrayList<>();
        list1.addAll(list);
        incidentalList.clear();
        incidentalList.addAll(list1);
    }

    public List<CountryModel.DataEntity> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryModel.DataEntity> list) {
        List<CountryModel.DataEntity> lists=new ArrayList<>();
        lists.addAll(list);
        countryList.clear();
        countryList.addAll(lists);
    }

    public List<ProductModel.DataEntity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductModel.DataEntity> list) {
        List<ProductModel.DataEntity> lists=new ArrayList<>();
        lists.addAll(list);
        productList.clear();
        productList.addAll(lists);
    }

    public List<CustomerModel.DataEntity> getCustomerResultList() {
        return customerResultList;
    }

    public void setCustomerResultList(List<CustomerModel.DataEntity> list) {
        List<CustomerModel.DataEntity> lists=new ArrayList<>();
        lists.addAll(list);
        customerResultList.clear();
        customerResultList.addAll(lists);
    }

    public SelectCurrentDayBatchModel.DataEntity getSelectCurrentDayBatchModel() {
        return selectCurrentDayBatchModel;
    }

    public void setSelectCurrentDayBatchModel(SelectCurrentDayBatchModel.DataEntity selectCurrentDayBatchModel) {
        this.selectCurrentDayBatchModel = selectCurrentDayBatchModel;
    }

    public CalculationVolumeWeightModel.DataEntity getCalculationVolumWeightModel() {
        return calculationVolumWeightModel;
    }

    public void setCalculationVolumWeightModel(CalculationVolumeWeightModel.DataEntity calculationVolumWeightModel) {
        this.calculationVolumWeightModel = calculationVolumWeightModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public ChenckInModel.DataEntity getChenckInResultModel() {
        return chenckInResultModel;
    }

    public void setChenckInResultModel(ChenckInModel.DataEntity chenckInResultModel) {
        this.chenckInResultModel = chenckInResultModel;
    }
}
