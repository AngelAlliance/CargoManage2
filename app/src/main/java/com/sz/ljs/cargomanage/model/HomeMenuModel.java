package com.sz.ljs.cargomanage.model;

/**
 * Created by liujs on 2018/8/13.
 * 首页菜单模型类
 */

public class HomeMenuModel {
    private int id;
    private String name = "";
    private int imgResource;

    public HomeMenuModel(int id, String name, int ImgResource) {
        this.id = id;
        this.name = name;
        this.imgResource = ImgResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

}
