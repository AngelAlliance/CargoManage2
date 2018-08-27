package com.sz.ljs.common.model;

//TODO 选择dialog数据实体类
public class ListialogModel {
    private String id;
    private String name;
    private String en_name;
    private boolean isChecked = false;

    public ListialogModel(String id,String name,String en_name, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.en_name = en_name;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
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
