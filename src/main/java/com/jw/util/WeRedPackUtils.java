package com.jw.util;

import com.jfinal.kit.JsonKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jw.api.RedPackApi;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送红包接口
 * @author eaco
 * 2016年5月28日
 */
public class WeRedPackUtils {

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
	public static String send(String mchBillno, String scene_id, String ip, String total_amount, String total_num,
			String wishing, String act_name, String remark, String reOpenid, String partner, String wxappid,
			String sendName, String paternerKey, String certPath) {

		Map<String, String> params = new HashMap<String, String>();
		// 随机字符串
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		// 商户订单号
		params.put("mch_billno", mchBillno);
		// 商户号
		params.put("mch_id", partner);
		// 公众账号ID
		params.put("wxappid", wxappid);
		// 商户名称
		params.put("send_name", sendName);
		// 用户OPENID
		params.put("re_openid", reOpenid);
		// 付款现金(单位分)
		params.put("total_amount", total_amount);
		// 红包发放总人数
		params.put("total_num", total_num);
		// 红包祝福语
		params.put("wishing", wishing);
		// 终端IP
		params.put("client_ip", ip);
		// 活动名称
		params.put("act_name", act_name);
		// 场景id 红包大于200时必传
		if (null != scene_id && !"".equals(scene_id)) {
			params.put("scene_id", scene_id);
		}
		// 备注
		params.put("remark", remark);
		//创建签名
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);

		String xmlResult = RedPackApi.sendRedPack(params, certPath, partner);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

		return JsonKit.toJson(result);
	}

	/**
	 * 根据商户订单号查询红包
	 * @param mch_billno 商户订单号
	 * @param partner 商户号
	 * @param wxappid 公众账号ID
	 * @param paternerKey 商户签名Key
	 * @param certPath 证书路径
	 * @return
	 */
	public static String query(String mch_billno, String partner, String wxappid, String paternerKey, String certPath) {
		Map<String, String> params = new HashMap<String, String>();
		// 随机字符串
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		// 商户订单号
		params.put("mch_billno", mch_billno);
		// 商户号
		params.put("mch_id", partner);
		// 公众账号ID
		params.put("appid", wxappid);
		params.put("bill_type", "MCHT");
		//创建签名
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);

		String xmlResult = RedPackApi.getHbInfo(params, certPath, partner);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		return JsonKit.toJson(result);
	}
}
