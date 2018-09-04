package com.sz.ljs.articlescan.model;

import java.util.ArrayList;
import java.util.List;

public class ArticleScanModel {

    //定义单例句柄
    private static ArticleScanModel Instance;

    public static ArticleScanModel getInstance() {
        if (null == Instance) {
            Instance = new ArticleScanModel();
        }
        return Instance;
    }

    private List<GsonSelectShipmentBagReceiveModel.DataBean> shipmentBagList = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.OrgListBean> org_list = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.ServerListBean> server_list = new ArrayList<>();

    public List<GsonSelectShipmentBagReceiveModel.DataBean> getShipmentBagList() {
        return shipmentBagList;
    }

    public void setShipmentBagList(List<GsonSelectShipmentBagReceiveModel.DataBean> list) {
        List<GsonSelectShipmentBagReceiveModel.DataBean> lists = new ArrayList<>();
        lists.addAll(list);
        shipmentBagList.clear();
        shipmentBagList.addAll(lists);
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
}
