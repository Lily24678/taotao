package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 内容管理Controller
 * @author nq nianqiang@itcast.cn
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult<TbContent> getContentList(Long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult<TbContent> result = contentService.getContentListByCategoryId(categoryId, page, rows);
		return result;
	}
	@RequestMapping("/content/saveOrUpdate")
	@ResponseBody
	public TaotaoResult addOrupdateContent(TbContent content){
		TaotaoResult result = contentService.addOrupdateContent(content);
		return result;
	}
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContentById(String ids){
		TaotaoResult result = contentService.deleteContentbyId(ids);
		return result;
	}

}
