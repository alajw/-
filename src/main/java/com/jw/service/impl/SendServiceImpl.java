package com.jw.service.impl;

import com.jw.service.SendService;
import com.jw.util.WeRedPackUtils;
import org.springframework.stereotype.Service;

@Service
public class SendServiceImpl implements SendService {
    @Override
    public String getqure(String mch_billno) {
        String jsonStr = WeRedPackUtils.query(mch_billno, "1531431851", "wx2b350584332295fc",
                "LQ4B9KRU0T769DA5Z78X6AD7LZ7I4I6X",
                "E:/U/1531431851_20190424_cert/apiclient_cert.p12");
        return jsonStr;
    }

    @Override
    public String getSand(String opid, String total_amount) {
        String mchBillno = System.currentTimeMillis() + "";
        String jsonObject = WeRedPackUtils.send(mchBillno + "", "PRODUCT_2", "127.0.0.1", total_amount+"", "1", "恭喜发财", "关注", "测试",
                opid+"", "1531431851", "wx2b350584332295fc", "红包测试",
                "LQ4B9KRU0T769DA5Z78X6AD7LZ7I4I6X",
                "E:/U/1531431851_20190424_cert/apiclient_cert.p12");
        return jsonObject;
    }
}
