package com.sz.ljs.packgoods.model;


import com.sz.ljs.common.model.ExpressModel;
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


    private List<ExpressModel> baleList=new ArrayList<>();



}
