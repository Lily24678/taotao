package com.taotao.service;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.utils.TaotaoResult;

public interface ItemService {
	EasyUIDataGridResult<TbItem> getItemList(Integer page,Integer rows);
	TaotaoResult addItem(TbItem item,String desc);
	TaotaoResult updateItem(TbItem item,String desc);
	/**
	 * @Description: 修改商品的状态
	 * @param    '商品状态，1-正常，2-下架，3-删除',
	 * @return TaotaoResult
	 */
	TaotaoResult updateItemStatus(String ids,Byte status);
	
	TbItem getItemById(Long id);
}
