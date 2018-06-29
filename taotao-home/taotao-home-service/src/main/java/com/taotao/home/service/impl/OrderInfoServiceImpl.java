package com.taotao.home.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Op.Create;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;
import com.taotao.home.mapper.OrderInfoMapper;
import com.taotao.home.pojo.OrderCounter;
import com.taotao.home.pojo.OrderInfo;
import com.taotao.home.pojo.OrderQuery;
import com.taotao.home.service.OrderInfoService;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.pojo.TbOrderExample;
import com.taotao.pojo.TbOrderExample.Criteria;

/**
 * 用户中心订单相关处理
 * <p>Title: OrderInfoServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Override
	public OrderCounter getOrderInfoCount(long userId) {
		//根据用户id查询改用户名下订单统计信息
		TbOrderExample example = new TbOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
		//查询待付款订单的数量
		criteria.andStatusEqualTo(1);
		int noPayCount = orderMapper.countByExample(example);
		//查询待确认收获订单数量
		example.clear();
		criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		//订单状态为2、3、4的订单
		List<Integer> statusList = new ArrayList<>();
		statusList.add(2);
		statusList.add(3);
		statusList.add(4);
		criteria.andStatusIn(statusList);
		int noConfirm = orderMapper.countByExample(example);
		//待评价
		example.clear();
		criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(5);
		int noRateCount = orderMapper.countByExample(example);
		//设置返回结果
		OrderCounter orderCounter = new OrderCounter();
		orderCounter.setNoPayCount(noPayCount);
		orderCounter.setNoConfirm(noConfirm);
		orderCounter.setNoRateCount(noRateCount);
		
		return orderCounter;
		
	}

	@Override
	public List<OrderInfo> getOrderList(Long userId, String keyword, Integer status, Integer dateRange) {
		//创建查询条件
		OrderQuery query = new OrderQuery();
		//用户id，其中用户id为必填参数，如果没有此参数，直接返回null
		if (userId != null) {
			query.setUserId(userId);
		} else {
			return null;
		}
		//过滤关键字：商品名称、商品id、订单id
		if (keyword != null) {
			query.setKeyword(keyword);
		}
		
		//订单状态（页面选择状态，默认为“全部状态”）：全部状态:4096  等待付款：1 等待收货：32 等待评价：128 已完成：1024
		if (status==null) status = 4096;
		//全部状态
		if (status == 4096) {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(1);
			statusList.add(2);
			statusList.add(3);
			statusList.add(4);
			statusList.add(5);
			statusList.add(6);
			query.setStatus(statusList);
		}
		//等待付款
		else if (status == 1) {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(1);
			query.setStatus(statusList);
		}
		//等待收货
		else if (status == 32) {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(2);
			statusList.add(3);
			statusList.add(4);
			query.setStatus(statusList);
		}
		//等待评价
		else if (status == 128) {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(5);
			query.setStatus(statusList);
		}
		//已完成
		else if (status == 1024) {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(6);
			query.setStatus(statusList);
		}
		//日期范围，默认为最近三个月。最近三个月：1 今年内：2	2015年：2015	2014年：2014	2014年以前：3
		
		if (dateRange == null) dateRange = 1;
		//最近三个月
		if (dateRange == 1) {
			DateTime dateTime = new DateTime();
			//三个月之前的日期
			DateTime startDate = dateTime.plusMonths(-3);
			query.setStartDate(startDate.toDate());
			query.setEndDate(dateTime.toDate());
		}
		//今年内
		else if (dateRange == 2) {
			DateTime dateTime = new DateTime();
			//参数：年、月、日、时、分、秒
			DateTime startDate = new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0);
			query.setStartDate(startDate.toDate());
			query.setEndDate(dateTime.toDate());
		}
		//2015、2014...
		else if (dateRange > 1000) {
			DateTime startDate = new DateTime(dateRange, 1, 1, 0, 0, 0);
			DateTime endDate = new DateTime(dateRange, 12, 31, 23, 59, 59);
			query.setStartDate(startDate.toDate());
			query.setEndDate(endDate.toDate());
		}
		//三年以前的订单
		else if (dateRange == 3) {
			//公司创办于2000年，2000年之前的订单是不存在的。
			DateTime startDate = new DateTime(2000, 1, 1, 0, 0, 0);
			DateTime endDate = new DateTime(new DateTime().plusYears(-3).getYear(), 12, 31, 23, 59, 59);
			query.setStartDate(startDate.toDate());
			query.setEndDate(endDate.toDate());
		}
		//执行查询
		List<OrderInfo> orderList = orderInfoMapper.getOrderList(query);
		return orderList;
	}
	public static void main(String[] args) {
		DateTime dateTime = new DateTime();
		System.out.println(dateTime.getYear());
		System.out.println(dateTime.getMonthOfYear());
		System.out.println(dateTime.getDayOfMonth());
		System.out.println(dateTime.plusMonths(-3).getMonthOfYear());
		System.out.println(new DateTime(new DateTime().plusYears(-3).getYear(), 12, 31, 23, 59, 59).toString("yyyy-MM-dd HH:mm:ss"));
	}
}
