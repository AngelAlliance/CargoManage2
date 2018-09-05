package com.sz.ljs.articlescan.model;

/**
 * Created by Administrator on 2018/9/5.
 */

public class TitleModel {
    private String packNumber="";
    private String fenGongSi="";
    private String fuWuQuDao="";
    private String pices="";
    private String votes="";

    public TitleModel(String packNumber, String fenGongSi, String fuWuQuDao, String pices, String votes) {
        this.packNumber = packNumber;
        this.fenGongSi = fenGongSi;
        this.fuWuQuDao = fuWuQuDao;
        this.pices = pices;
        this.votes = votes;
    }

    public String getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(String packNumber) {
        this.packNumber = packNumber;
    }

    public String getFenGongSi() {
        return fenGongSi;
    }

    public void setFenGongSi(String fenGongSi) {
        this.fenGongSi = fenGongSi;
    }

    public String getFuWuQuDao() {
        return fuWuQuDao;
    }

    public void setFuWuQuDao(String fuWuQuDao) {
        this.fuWuQuDao = fuWuQuDao;
    }

    public String getPices() {
        return pices;
    }

    public void setPices(String pices) {
        this.pices = pices;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
