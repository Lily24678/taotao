package com.taotao.home.pojo;

import java.io.Serializable;
import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbUser;

public class OrderInfo extends TbOrder implements Serializable{

	private List<TbOrderItem> items;
	private TbUser user;
	public List<TbOrderItem> getItems() {
		return items;
	}
	public void setItems(List<TbOrderItem> items) {
		this.items = items;
	}
	public TbUser getUser() {
		return user;
	}
	public void setUser(TbUser user) {
		this.user = user;
	}
	
	
	
}
