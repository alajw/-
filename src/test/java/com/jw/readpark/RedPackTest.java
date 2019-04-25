package com.jw.readpark;

import com.jw.util.WeRedPackUtils;
import org.junit.Test;

/**
 * TODO: 增加描述
 * 
 * @author yiz
 * @date 2016年11月17日 下午12:38:51
 * @version 1.0.0 
 */
public class RedPackTest {
	/**
	 * 发送现金红包,返回值JSON字符串，当result_code为SUCCESS，return_code为SUCESS表示发送成功，其他信息可在 err_code_des 字段查看，具体字段参考 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3，成功示例和失败示例
	 * <p> scene_id 请登陆商户平台：产品中心-现金红包-产品设置-使用场景 进行开通</p>
	 * @param ip 获取IP
	 * @param mchBillno 商户订单号
	 * @param scene_id 场景id 红包大于200时必传  PRODUCT_1:商品促销PRODUCT_2:抽奖PRODUCT_3:虚拟物品兑奖PRODUCT_4:企业内部福利PRODUCT_5:渠道分润PRODUCT_6:保险回馈PRODUCT_7:彩票派奖PRODUCT_8:税务刮奖
	 * @param total_amount  付款现金(单位分)
	 * @param total_num 红包发放总人数
	 * @param wishing 红包祝福语
	 * @param act_name 活动名称
	 * @param remark 备注
	 * @param reOpenid 用户openid
	 * @param partner 商户号
	 * @param wxappid 公众账号appid
	 * @param sendName 商户名称
	 * @param paternerKey 商户签名key
	 * @param certPath 证书路径
	 * @return
	 */
	@Test
	public void testSend() throws Exception {
		String mchBillno = System.currentTimeMillis() + "";
		System.out.println("订单号：" + mchBillno);
		String jsonObject = WeRedPackUtils.send(mchBillno + "", "", "127.0.0.1", "100", "1", "恭喜发财", "关注", "测试",
				"oCe2Pw4orhMUJ0j2YL0bsXQApuJs", "1531431851", "wx2b350584332295fc", "红包测试",
				"LQ4B9KRU0T769DA5Z78X6AD7LZ7I4I6X",
				"E:/U/1531431851_20190424_cert/apiclient_cert.p12");
		System.out.println(jsonObject);
	}

	@Test
	public void testQuery() throws Exception {
		String mch_billno = "1556161167239";
		String jsonStr = WeRedPackUtils.query(mch_billno, "1531431851", "wx2b350584332295fc",
				"LQ4B9KRU0T769DA5Z78X6AD7LZ7I4I6X",
				"E:/U/1531431851_20190424_cert/apiclient_cert.p12");
		System.out.println(jsonStr);
	}
}
