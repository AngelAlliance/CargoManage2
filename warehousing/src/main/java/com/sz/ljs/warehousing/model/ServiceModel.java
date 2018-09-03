package com.sz.ljs.warehousing.model;

/**
 * 附加服务实体类
 */
public class ServiceModel {
    private int position;
    private String extra_servicecurrency;///费用种类（默认人名币）
    private String extra_createrid;///创建人id
    private String extra_createdate;//创建时间
    private String extra_servicenote;//备注信息
    private String extra_paytype;//默认未空不需要管
    private Double extra_servicevalue; //费用金额
    private String extra_servicecode;//费用代码
    private String extra_servicecname;//费用中文名字
    private String bs_id;//业务id

    public ServiceModel(int position,String extra_servicecurrency, String extra_createrid, String extra_createdate, String extra_servicenote, String extra_paytype, Double extra_servicevalue, String extra_servicecode,String extra_servicecname, String bs_id) {
        this.position = position;
        this.extra_servicecurrency = extra_servicecurrency;
        this.extra_createrid = extra_createrid;
        this.extra_createdate = extra_createdate;
        this.extra_servicenote = extra_servicenote;
        this.extra_paytype = extra_paytype;
        this.extra_servicevalue = extra_servicevalue;
        this.extra_servicecode = extra_servicecode;
        this.extra_servicecname = extra_servicecname;
        this.bs_id = bs_id;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getExtra_servicecurrency() {
        return extra_servicecurrency;
    }

    public void setExtra_servicecurrency(String extra_servicecurrency) {
        this.extra_servicecurrency = extra_servicecurrency;
    }

    public String getExtra_createrid() {
        return extra_createrid;
    }

    public void setExtra_createrid(String extra_createrid) {
        this.extra_createrid = extra_createrid;
    }

    public String getExtra_createdate() {
        return extra_createdate;
    }

    public void setExtra_createdate(String extra_createdate) {
        this.extra_createdate = extra_createdate;
    }

    public String getExtra_servicenote() {
        return extra_servicenote;
    }

    public void setExtra_servicenote(String extra_servicenote) {
        this.extra_servicenote = extra_servicenote;
    }

    public String getExtra_paytype() {
        return extra_paytype;
    }

    public void setExtra_paytype(String extra_paytype) {
        this.extra_paytype = extra_paytype;
    }

    public Double getExtra_servicevalue() {
        return extra_servicevalue;
    }

    public void setExtra_servicevalue(Double extra_servicevalue) {
        this.extra_servicevalue = extra_servicevalue;
    }

    public String getExtra_servicecode() {
        return extra_servicecode;
    }

    public void setExtra_servicecode(String extra_servicecode) {
        this.extra_servicecode = extra_servicecode;
    }

    public String getBs_id() {
        return bs_id;
    }

    public void setBs_id(String bs_id) {
        this.bs_id = bs_id;
    }

    public String getExtra_servicecname() {
        return extra_servicecname;
    }

    public void setExtra_servicecname(String extra_servicecname) {
        this.extra_servicecname = extra_servicecname;
    }
}
