package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemDescService;
import com.taotao.utils.TaotaoResult;

@Controller
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult getItemDescById(@PathVariable("id") Long id){
		TaotaoResult result = itemDescService.getItemDescById(id);
		return result;
	}
}
