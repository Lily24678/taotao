package com.taotao.order.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.mapper.TbPayLogMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbPayLog;
import com.taotao.redis.JedisClient;
import com.taotao.utils.IdWorker;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 订单 service
 * @author nq nianqiang@itcast.cn
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbPayLogMapper payLogMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	@Value("${ORDER_PAYLOG_OUT_TRADE_NO}")
	private String ORDER_PAYLOG_OUT_TRADE_NO;

	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		// 1、接收表单的数据
		// 2、生成订单id
		if (!jedisClient.exists(ORDER_GEN_KEY)) {
			// 设置初始值
			jedisClient.set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
		}
		String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		// 1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 3、向订单表插入数据。
		orderMapper.insert(orderInfo);
		// 4、向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		
		long totalFee = 0l;
		
		for (TbOrderItem tbOrderItem : orderItems) {
			// 生成明细id
			Long orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY);
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId);
			// 累计金额
			totalFee += tbOrderItem.getTotalFee();
			// 插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		// 5、向订单物流表插入数据。
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);

		// 判断当前的支付方式：如果是在线支付需要将当前的数据添加到支付日志记录表
		if (orderInfo.getPaymentType().intValue() == 1) { // 在线支付
			TbPayLog payLog = new TbPayLog();
			// 创建日期
			payLog.setCreateTime(new Date());
			// 多个用“,”分割
			payLog.setOrderList(orderId);
			// 支付订单号，为微信字符提供
			payLog.setOutTradeNo(new IdWorker().nextId() + "");
			// 支付类型 1：微信
			payLog.setPayType("1");
			// 单位 分
			payLog.setTotalFee(totalFee);
			// 0：未支付 1：已支付 2：支付关闭
			payLog.setTradeState("0");
			// UserId
			payLog.setUserId(orderInfo.getUserId() + "");
			// 插入数据库
			payLogMapper.insert(payLog);

			// 添加到redis缓存中 数据的格式： OUT_TRADE_NO + ORDER + userID
			jedisClient.set(ORDER_PAYLOG_OUT_TRADE_NO + ":" + orderInfo.getUserId(), JsonUtils.objectToJson(payLog));

		}

		// 6、返回TaotaoResult。
		return TaotaoResult.ok(orderId);
	}

	/**
	 * @Description: 根据用户ID获得当前支付日志
	 * @param
	 * @return TbPayLog
	 */
	@Override
	public TbPayLog searchPayLogFromRedis(String userId) {

		String json = jedisClient.get(ORDER_PAYLOG_OUT_TRADE_NO + ":" + userId);
		if (StringUtils.isNotBlank(json)) {
			return JsonUtils.jsonToPojo(json, TbPayLog.class);
		}

		return null;
	}

	/**
	 * 支付成功之后对 各个表中的支付相关的状态进行修改
	 */
	@Override
	public void updateOrderStatus(String out_trade_no, String transaction_id) {
		// 1.修改支付日志状态
		TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
		payLog.setPayTime(new Date());
		payLog.setTradeState("1");// 已支付
		payLog.setTransactionId(transaction_id);// 交易号
		payLogMapper.updateByPrimaryKey(payLog);
		// 2.修改订单状态
		String orderId = payLog.getOrderList();// 获取订单号列表
		TbOrder order = orderMapper.selectByPrimaryKey(orderId);
		if (order != null) {
			order.setStatus(2);// 已付款
			order.setPaymentTime(new Date()); // 付款时间
			orderMapper.updateByPrimaryKey(order);
		}
		// 清除redis缓存数据
		jedisClient.expire(ORDER_PAYLOG_OUT_TRADE_NO + ":" + payLog.getUserId(), 0);
		
	}

}
