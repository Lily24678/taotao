package com.taotao.order.service;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.pojo.TbPayLog;
import com.taotao.utils.TaotaoResult;

public interface OrderService {
	public TaotaoResult createOrder(OrderInfo orderInfo);
	/**
	 * 根据用户查询payLog
	 * @param userId
	 * @return
	 */
	public TbPayLog searchPayLogFromRedis(String userId);
	/**
	 * 修改订单状态
	 * @param out_trade_no 支付订单号
	 * @param transaction_id 微信返回的交易流水号
	 */
	public void updateOrderStatus(String out_trade_no,String transaction_id);


}
