package com.sz.ljs.warehousing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class CountryModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : [{"country_code":"AD","country_cnname":"安道尔","country_enname":"ANDORRA","country_type":"C","country_cnspell":"ANDAOER"}]
     */

    private int Code;
    private String Msg;
    private List<DataEntity> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        /**
         * country_code : AD
         * country_cnname : 安道尔
         * country_enname : ANDORRA
         * country_type : C
         * country_cnspell : ANDAOER
         */

        private String country_code;//国家二字简码
        private String country_cnname;//国家中文名
        private String country_enname;//国家英文名
        private String country_type;
        private String country_cnspell; //汉语拼音

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getCountry_cnname() {
            return country_cnname;
        }

        public void setCountry_cnname(String country_cnname) {
            this.country_cnname = country_cnname;
        }

        public String getCountry_enname() {
            return country_enname;
        }

        public void setCountry_enname(String country_enname) {
            this.country_enname = country_enname;
        }

        public String getCountry_type() {
            return country_type;
        }

        public void setCountry_type(String country_type) {
            this.country_type = country_type;
        }

        public String getCountry_cnspell() {
            return country_cnspell;
        }

        public void setCountry_cnspell(String country_cnspell) {
            this.country_cnspell = country_cnspell;
        }
    }
}
