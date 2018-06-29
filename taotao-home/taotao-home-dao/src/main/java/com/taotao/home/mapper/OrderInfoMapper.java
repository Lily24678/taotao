package com.taotao.home.mapper;

import java.util.List;

import com.taotao.home.pojo.OrderInfo;
import com.taotao.home.pojo.OrderQuery;

public interface OrderInfoMapper {

	List<OrderInfo> getOrderList(OrderQuery query); 
}
