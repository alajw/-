package com.jw.service;


public interface SendService {

    //指定opID发送红包
    public String getSand(String opid,String total_amount);
    //根据订单号查询订单
    public String getqure(String ding);

}
