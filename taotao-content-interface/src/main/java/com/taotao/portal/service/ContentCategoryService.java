package com.taotao.portal.service;

import java.util.List;

import com.taotao.common.EasyUITreeNode;
import com.taotao.pojo.TbContentCategory;
import com.taotao.utils.TaotaoResult;

public interface ContentCategoryService {
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	public TaotaoResult addOrUpdateContentCategory(TbContentCategory category);
	public TaotaoResult deleteContentCategory(Long id);
}
