package com.jw.util;


import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.demo.WeixinPayController;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.jw.call.WePayModel;
import com.jw.vo.AjaxResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 */
public class WePayUtils {
	private static final Log logger = Log.getLog(WeixinPayController.class);
	private AjaxResult ajax = new AjaxResult();

	/**
	 * 
	 * @param openId 支付者的openid
	 * @param appid 商戶的appId（公众好）
	 * @param partner（商户ID）
	 * @param paternerKey（商户秘钥）
	 * @param total_fee（支付金额，请传入数字类型的字符串，1表示1分，100表示1元）
	 * @param ip（客户端IP）
	 * @param notify_url（支付成功通知地址）
	 * @param attach 支付附带参数
	 * @return
	 */
	public AjaxResult pay(String openId, String appid, String partner, String paternerKey, String total_fee,
			String notify_url, String ip, String attach) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", partner);
		params.put("body", "Javen微信公众号极速开发");
		String out_trade_no = System.currentTimeMillis() + "";
		params.put("out_trade_no", out_trade_no);
		int price = ((int) (Float.valueOf(total_fee) * 100));
		params.put("total_fee", price + "");
		params.put("attach", attach);

		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}

		params.put("spbill_create_ip", ip);
		params.put("trade_type", TradeType.JSAPI.name());
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("notify_url", notify_url);
		params.put("openid", openId);

		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);

		String xmlResult = PaymentApi.pushOrder(params);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			ajax.addError(return_msg);
			return ajax;
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			ajax.addError(return_msg);
			return ajax;
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = result.get("prepay_id");

		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", appid);
		packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		packageParams.put("nonceStr", System.currentTimeMillis() + "");
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		String packageSign = PaymentKit.createSign(packageParams, paternerKey);
		packageParams.put("paySign", packageSign);

		String jsonStr = JsonUtils.toJson(packageParams);
		ajax.success(jsonStr);
		return ajax;
	}

	public String pay_notifyXml(String xmlMsg, String paternerKey, Callable<WePayModel> call) {
		//获取所有的参数
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);

		String appid = params.get("appid");
		//商户号
		String mch_id = params.get("mch_id");
		String result_code = params.get("result_code");
		String openId = params.get("openid");
		//交易类型
		String trade_type = params.get("trade_type");
		//付款银行
		String bank_type = params.get("bank_type");
		// 总金额
		String total_fee = params.get("total_fee");
		//现金支付金额
		String cash_fee = params.get("cash_fee");
		// 微信支付订单号
		String transaction_id = params.get("transaction_id");
		// 商户订单号
		String out_trade_no = params.get("out_trade_no");
		// 支付完成时间，格式为yyyyMMddHHmmss
		String time_end = params.get("time_end");

		/////////////////////////////以下是附加参数///////////////////////////////////

		String fee_type = params.get("fee_type");
		String is_subscribe = params.get("is_subscribe");
		String err_code = params.get("err_code");
		String err_code_des = params.get("err_code_des");

		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		if (PaymentKit.verifyNotify(params, paternerKey)) {
			if (("SUCCESS").equals(result_code)) {
				try {
					call.call();
				} catch (Exception e) {
					logger.error("微信异步异常", e);
				}
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				return PaymentKit.toXml(xml);
			}
		}

		return "";
	}

	public String getCodeUrl(String product_id, String appid, String partner, String paternerKey) {
		String url = "weixin://wxpay/bizpayurl?sign=%1$s&appid=%2$s&mch_id=%3$s&product_id=%4$s&time_stamp=%5$s&nonce_str=%6$s";
		String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
		String nonceStr = Long.toString(System.currentTimeMillis());
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", partner);
		packageParams.put("product_id", product_id);
		packageParams.put("time_stamp", timeStamp);
		packageParams.put("nonce_str", nonceStr);
		String packageSign = PaymentKit.createSign(packageParams, paternerKey);

		return String.format(url, packageSign, appid, partner, product_id, timeStamp, nonceStr);
	}

	/**
	 * 生成支付二维码（模式一）并在页面上显示
	 */
	public byte[] scanCode1(String product_id, String appid, String partner, String paternerKey) {
		//获取扫码支付（模式一）url
		String qrCodeUrl = getCodeUrl(product_id, appid, partner, paternerKey);
		logger.info("微信扫码的qrCodeUrl=" + qrCodeUrl);
		byte[] bytes = null;
		try {
			bytes = QRUtils.createQr(qrCodeUrl, 200, 200, "png");
		} catch (Exception e) {
			logger.error("二维码生成异常", e);
		}
		return bytes;
	}
}
