package com.ljs.examinegoods.model;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ImageType {
    private String image_url;//图片地址
    private String image_type;//图片类型，D为删除，A为正常

    public ImageType(String url,String Type){
        this.image_url=url;
        this.image_type=Type;
    }
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }


}
