package com.sz.ljs.warehousing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 子单实体类
 */
public class SubnitModel {

    private int position;
    private List<CalculationVolumeWeightModel.DataEntity.LstCargoVolumeEntity> list=new ArrayList<>();

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<CalculationVolumeWeightModel.DataEntity.LstCargoVolumeEntity> getList() {
        return list;
    }

    public void setList(List<CalculationVolumeWeightModel.DataEntity.LstCargoVolumeEntity> list) {
        this.list = list;
    }


}
