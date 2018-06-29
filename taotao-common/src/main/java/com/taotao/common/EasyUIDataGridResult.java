package com.taotao.common;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: easyUI分页栏固定实体
 * @author nq  nianqiang@itcast.cn 
 */
public class EasyUIDataGridResult<T> implements Serializable {
	@Override
	public String toString() {
		return "EasyUIDataGridResult [total=" + total + ", rows=" + rows + "]";
	}
	private static final long serialVersionUID = 1L;
	
	private Long total;
	private List<T> rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
