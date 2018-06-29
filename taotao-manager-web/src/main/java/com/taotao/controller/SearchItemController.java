package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.service.SearchItemService;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 商品索引库Controller
 * @author nq nianqiang@itcast.cn
 */
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping("/index/importall")
	@ResponseBody
	public TaotaoResult importAllItems() {
		try {
			TaotaoResult result = searchItemService.addAllItemList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "导入数据失败");
		}
	}

}
