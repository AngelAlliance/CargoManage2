package com.sz.ljs.common.model;

/**
 * Created by liujs on 2018/8/16.
 * 菜单实体
 */

public class MenuModel {
    private int id;
    private int imgResource;
    private String name;

    public MenuModel(int id,String name,int imgResource){
        this.id=id;
        this.name=name;
        this.imgResource=imgResource;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
