package com.sz.ljs.shipments.model;

import com.sz.ljs.common.model.ExpressPackageModel;

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


    private List<ExpressPackageModel> selectList=new ArrayList<>();

    public List<ExpressPackageModel> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<ExpressPackageModel> list) {
        List<ExpressPackageModel> lists=new ArrayList<>();
        lists.addAll(list);
        selectList.clear();
        selectList.addAll(lists);
    }
}
