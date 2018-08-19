package com.sz.ljs.warehousing.model;

/**
 * 附加服务实体类
 */
public class ServiceModel {
    private String kind;//杂费项代码
    private String zafeixiang;//杂费项
    private Double feiyong; //费用


    public String getKind() {
        return kind;
    }

    public void setKind(String id) {
        this.kind = id;
    }

    public String getZafeixiang() {
        return zafeixiang;
    }

    public void setZafeixiang(String zafeixiang) {
        this.zafeixiang = zafeixiang;
    }

    public Double getFeiyong() {
        return feiyong;
    }

    public void setFeiyong(Double feiyong) {
        this.feiyong = feiyong;
    }


}
