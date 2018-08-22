package com.sz.ljs.common.model;

//TODO 选择dialog数据实体类
public class ListialogModel {
    private String name;
    private boolean isChecked = false;

    public ListialogModel(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
