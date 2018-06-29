package com.taotao.pay.service;

import java.util.Map;

public interface WeixinPayService {

	/**
	 * 生成二维码(主要是生成二维码的url地址)
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 */
	public Map createNative(String out_trade_no,String total_fee);
	
	/**
	 * 查询支付订单状态
	 * @param out_trade_no
	 * @return
	 */
	public Map queryPayStatus(String out_trade_no);
	/**
	 * @Description: 关闭订单
	 * @param    
	 * @return Map
	 */
	public Map closeNativeOrder(String out_trade_no);
	
	/**
	 * 异步查询订单的支付状态接口
	 */
	public Map queryOrderPayResult();
	
}
