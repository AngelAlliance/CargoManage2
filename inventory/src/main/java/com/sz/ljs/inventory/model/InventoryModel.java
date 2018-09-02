package com.sz.ljs.inventory.model;

import com.sz.ljs.common.model.ExpressModel;

import java.util.ArrayList;
import java.util.List;

public class InventoryModel {

    //定义单例句柄
    private static InventoryModel Instance;

    public static InventoryModel getInstance() {
        if (null == Instance) {
            Instance = new InventoryModel();
        }
        return Instance;
    }

    private List<ExpressModel> expressList = new ArrayList<>();

    public List<ExpressModel> getExpressList() {
        return expressList;
    }

    public void setExpress(ExpressModel model) {
        if (null != expressList && expressList.size() > 0) {
            for (ExpressModel etity : expressList) {
                if (etity.getBs_id().equals(model.getBs_id())) {
                    //TODO 证明里面已经有这个运单了，则不做处理
                    return;
                }
            }
        }
        expressList.add(model);
    }

    public void setExpressList(List<ExpressModel> list) {
        List<ExpressModel> list1 = new ArrayList<>();
        list1.addAll(list);
        expressList.clear();
        expressList.addAll(list1);
    }

    public void removeExpressList(List<ExpressModel> list) {
        if (null != expressList) {
            expressList.removeAll(list);
        }
    }
}
