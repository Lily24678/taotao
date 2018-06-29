package com.taotao.common;

import java.io.Serializable;

/**
 * @Description: 查詢的实体
 * @author nq nianqiang@itcast.cn
 */
public class QueryVo implements Serializable{
	
	private Integer cpage=1; // 当前页
	private Integer pageSize=60; // 一页显示的记录数
	
	private Integer start; 
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getCpage() {
		return cpage;
	}
	public void setCpage(Integer cpage) {
		this.cpage = cpage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
