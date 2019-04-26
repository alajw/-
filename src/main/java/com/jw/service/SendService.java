package com.jw.service;


public interface SendService {

    //指定opID发送红包
      String getSand(String opid, String total_amount, String scene_id );
    //根据订单号查询订单
      String getqure(String ding);

}
