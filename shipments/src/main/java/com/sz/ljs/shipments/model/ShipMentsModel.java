package com.sz.ljs.shipments.model;

import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.GsonDepltListModel;

import java.util.ArrayList;
import java.util.List;

public class ShipMentsModel {

    //定义单例句柄
    private static ShipMentsModel Instance;

    public static ShipMentsModel getInstance() {
        if (null == Instance) {
            Instance = new ShipMentsModel();
        }
        return Instance;
    }

    public void clean() {
        selectList.clear();
        ShppingCnList.clear();
        BaleList.clear();
        serviceChannelList.clear();
        org_list.clear();
        server_list.clear();
        businessDataBean = null;
    }

    private List<ExpressPackageModel> selectList = new ArrayList<>();

    private List<ExpressPackageModel> ShppingCnList = new ArrayList<>();
    private List<ExpressModel> BaleList = new ArrayList<>();
    private List<GsonServiceChannelModel.DataBean> serviceChannelList = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.OrgListBean> org_list = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.ServerListBean> server_list = new ArrayList<>();
    private GsonSaveTransportBatchAndBusinessModel.DataBean businessDataBean;


    public List<ExpressPackageModel> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<ExpressPackageModel> list) {
        List<ExpressPackageModel> lists = new ArrayList<>();
        lists.addAll(list);
        selectList.clear();
        selectList.addAll(lists);
    }

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
        List<GsonServiceChannelModel.DataBean> list1 = new ArrayList<>();
        list1.addAll(list);
        serviceChannelList.clear();
        serviceChannelList.addAll(list1);
    }

    public List<GsonOrgServerModel.DataBean.OrgListBean> getOrg_list() {
        return org_list;
    }

    public void setOrg_list(List<GsonOrgServerModel.DataBean.OrgListBean> list) {
        List<GsonOrgServerModel.DataBean.OrgListBean> list1 = new ArrayList<>();
        list1.addAll(list);
        org_list.clear();
        org_list.addAll(list1);
    }

    public List<GsonOrgServerModel.DataBean.ServerListBean> getServer_list() {
        return server_list;
    }

    public void setServer_list(List<GsonOrgServerModel.DataBean.ServerListBean> list) {
        List<GsonOrgServerModel.DataBean.ServerListBean> lists = new ArrayList<>();
        lists.addAll(list);
        server_list.clear();
        server_list.addAll(lists);
    }

    public GsonSaveTransportBatchAndBusinessModel.DataBean getBusinessDataBean() {
        return businessDataBean;
    }

    public void setBusinessDataBean(GsonSaveTransportBatchAndBusinessModel.DataBean businessDataBean) {
        this.businessDataBean = businessDataBean;
    }
}
