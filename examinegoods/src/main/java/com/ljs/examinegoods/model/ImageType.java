package com.ljs.examinegoods.model;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ImageType {
    private String url;//图片地址
    private String Type;//图片类型，D为删除，A为正常

    public ImageType(String url,String Type){
        this.url=url;
        this.Type=Type;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
