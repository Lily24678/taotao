package com.taotao.home.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.home.pojo.OrderCounter;
import com.taotao.home.pojo.OrderInfo;
import com.taotao.home.service.OrderInfoService;
import com.taotao.pojo.TbUser;

/**
 * 订单信息处理Controller
 * <p>Title: OrderInfoController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;
	
	@RequestMapping("/orderList")
	public String getOrderList(String keyword, @RequestParam(defaultValue="4096")Integer status, 
			@RequestParam(defaultValue="1")Integer dateRange, HttpServletRequest request) {
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//便利提醒
		OrderCounter orderCounter = orderInfoService.getOrderInfoCount(user.getId());
		List<OrderInfo> orderList = orderInfoService.getOrderList(user.getId(), keyword, status, dateRange);
		//向页面传递数据
		request.setAttribute("orderCounter", orderCounter);
		request.setAttribute("orderList", orderList); 
		//参数回显
		request.setAttribute("keyword", keyword);
		request.setAttribute("status", status);
		request.setAttribute("dateRange", dateRange);
		
		return "my-orders";
	}
}
