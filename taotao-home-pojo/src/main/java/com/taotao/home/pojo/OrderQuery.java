package com.taotao.home.pojo;

import java.util.Date;
import java.util.List;

public class OrderQuery {

	//用户id
	private Long userId;
	//商品名称、商品id、订单号
	private String keyword;
	//订单状态
	private List<Integer> status;
	//订单起始日期
	private Date startDate;
	//订单截止日期
	private Date endDate;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Integer> getStatus() {
		return status;
	}
	public void setStatus(List<Integer> status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
