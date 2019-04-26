package com.jw.controller;

import com.jw.entity.ConfigEntity;
import com.jw.service.ConfigService;
import com.jw.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RemoteController {
    @Autowired
    private SendService sendService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CacheManager cacheManager;



    /**
     * 发送红包
     * @param opid
     * @param total_amount
     * @return
     * @throws Exception
     */
    @GetMapping("/WxSend")
    public String testSend(String opid, String total_amount,String scene_id) throws Exception {
        return sendService.getSand(opid, total_amount,scene_id);
    }

    /**
     * 查询订单
     * @throws Exception
     */
    @GetMapping("/Query")
    public String  testQuery(String mch_billno) throws Exception {
        return  sendService.getqure(mch_billno);

    }

    /**
     * 数据测试
     * @return
     */
     @GetMapping("/index")
    public List<ConfigEntity> getIndex(){
        return configService.getAll();
     }

    /**
     * redis 数据删除
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void remoKey() {
        System.out.print("baseCahe缓存删除");
        cacheManager.getCache("baseCache").clear();

    }

}

