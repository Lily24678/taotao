package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;

/** 
 * @Description: 商品详情的 Controller
 * @author nq  nianqiang@itcast.cn   
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping("/item/{id}.html")
	public String getItem(@PathVariable Long id,Model model){
		// 商品简单的信息
		TbItem tbItem = itemService.getItemById(id);
		Item item = new Item(tbItem);
		model.addAttribute("item", item);
		// 商品的详情
		TbItemDesc itemDesc = itemDescService.findItemDescById(id);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
