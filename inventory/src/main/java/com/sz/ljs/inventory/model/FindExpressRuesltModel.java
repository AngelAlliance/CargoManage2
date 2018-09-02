package com.sz.ljs.inventory.model;

import com.sz.ljs.common.model.ExpressModel;

import java.util.List;

/**
 * Author: Mr. Duan
 * Date: 2018/8/29
 * Description:查询运单编号返回数据体
 */

public class FindExpressRuesltModel {

 /**
  * Code : 1
  * Msg : OK
  * Data : {"expressEntity":{"bs_id":"1948299","shipper_hawbcode":"300803414","child_number":"300803414","server_id":"","server_channelid":"","checkin_date":"2018-08-28 00:00:00","shipper_pieces":"1","outvolume_grossweight":"22.000","outvolume_Volmtweight":"4.260","outvolume_length":"","outvolume_width":"","outvolume_height":"","balance_sign":"","holding":"","IsSelect":"","order_status":"已收件"}}
  */

 private int Code;
 private String Msg;
 private DataBean Data;

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

 public DataBean getData() {
  return Data;
 }

 public void setData(DataBean Data) {
  this.Data = Data;
 }

 public static class DataBean {
  /**
   * expressEntity : {"bs_id":"1948299","shipper_hawbcode":"300803414","child_number":"300803414","server_id":"","server_channelid":"","checkin_date":"2018-08-28 00:00:00","shipper_pieces":"1","outvolume_grossweight":"22.000","outvolume_Volmtweight":"4.260","outvolume_length":"","outvolume_width":"","outvolume_height":"","balance_sign":"","holding":"","IsSelect":"","order_status":"已收件"}
   */

  private ExpressModel expressEntity;

  public ExpressModel getExpressEntity() {
   return expressEntity;
  }

  public void setExpressEntity(ExpressModel expressEntity) {
   this.expressEntity = expressEntity;
  }
 }

 @Override
 public String toString() {
  return "FindExpressRuesltModel{" +
          "Code=" + Code +
          ", Msg='" + Msg + '\'' +
          ", Data=" + Data.getExpressEntity().toString() +
          '}';
 }
}