package com.taotao.home.service;

import java.util.List;

import com.taotao.home.pojo.OrderCounter;
import com.taotao.home.pojo.OrderInfo;

public interface OrderInfoService {

	OrderCounter getOrderInfoCount(long userId);
	List<OrderInfo> getOrderList(Long userId, String keyword, Integer status, Integer dateRange);
}
