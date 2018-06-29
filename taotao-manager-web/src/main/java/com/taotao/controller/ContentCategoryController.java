package com.taotao.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.EasyUITreeNode;
import com.taotao.pojo.TbContentCategory;
import com.taotao.portal.service.ContentCategoryService;
import com.taotao.utils.TaotaoResult;

/** 
 * @Description: 内容分类Controller
 * @author nq  nianqiang@itcast.cn   
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(value="id", defaultValue="0") Long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public TaotaoResult updateContentCategory(TbContentCategory contentCategory){
		TaotaoResult result = contentCategoryService.addOrUpdateContentCategory(contentCategory);
		return result;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id){
		TaotaoResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}

}
