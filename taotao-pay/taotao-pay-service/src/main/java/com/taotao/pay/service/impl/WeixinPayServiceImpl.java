package com.taotao.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayUtil;
import com.taotao.pay.service.WeixinPayService;
import com.taotao.utils.HttpClient;
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

	@Value("${appid}")
	private String appid;
	
	@Value("${partner}")
	private String partner;
	
	@Value("${partnerkey}")
	private String partnerkey;
	
	@Value("${notifyurl}")
	private String notifyurl;
	
	@Value("${ORDER_PAY_URL_REQUEST}")
	private String ORDER_PAY_URL_REQUEST;
	
	@Value("${ORDER_QUERY_URL}")
	private String ORDER_QUERY_URL;
	@Value("${ORDER_CLOSED_URL}")
	private String ORDER_CLOSED_URL;
	
	@Override
	public Map createNative(String out_trade_no, String total_fee) {
		//1.参数封装
		Map param=new HashMap();
		param.put("appid", appid);//公众账号ID
		param.put("mch_id", partner);//商户
		param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		param.put("body", "淘淘商城"); // 在扫码完成后可以见到的文字
		param.put("out_trade_no", out_trade_no);//交易订单号
		param.put("total_fee", total_fee);//金额（分）
		param.put("spbill_create_ip", "127.0.0.1");
		param.put("notify_url", notifyurl);
		param.put("trade_type", "NATIVE");//交易类型 必须 NATIVE
			
		try {
			// generateSignedXml方法中 要根据 partnerkey 生成一个 签名sign
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			System.out.println("请求的参数："+xmlParam);
			
			//2.发送请求			
			HttpClient httpClient=new HttpClient(ORDER_PAY_URL_REQUEST);
			httpClient.setHttps(true);
			httpClient.setXmlParam(xmlParam);
			httpClient.post();
			
			//3.获取结果
			String xmlResult = httpClient.getContent();
			
			Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
			System.out.println("微信返回结果"+mapResult);
			Map map=new HashMap<>();
			map.put("code_url", mapResult.get("code_url"));//生成支付二维码的链接
			map.put("out_trade_no", out_trade_no);
			map.put("total_fee", total_fee);
			
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap();
		}
		
	}

	@Override
	public Map queryPayStatus(String out_trade_no) {
		//1.封装参数
		Map param=new HashMap();
		param.put("appid", appid);
		param.put("mch_id", partner);
		param.put("out_trade_no", out_trade_no);
		param.put("nonce_str", WXPayUtil.generateNonceStr());
		try {
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			//2.发送请求
			HttpClient httpClient=new HttpClient(ORDER_QUERY_URL);
			httpClient.setHttps(true);
			httpClient.setXmlParam(xmlParam);
			httpClient.post();
			
			//3.获取结果
			String xmlResult = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
			System.out.println("调用查询API返回结果："+xmlResult);
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Map closeNativeOrder(String out_trade_no) {
		Map param=new HashMap();
		param.put("appid", appid);//公众账号ID
		param.put("mch_id", partner);//商户
		param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		param.put("out_trade_no", out_trade_no);//交易订单号
		try {
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			
			HttpClient httpClient=new HttpClient(ORDER_CLOSED_URL);
			httpClient.setHttps(true);
			httpClient.setXmlParam(xmlParam);
			httpClient.post();
			
			//3.获取结果
			String xmlResult = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
			System.out.println("关闭订单结果："+map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public Map queryOrderPayResult() {
		
		return null;
	}
	
	

}