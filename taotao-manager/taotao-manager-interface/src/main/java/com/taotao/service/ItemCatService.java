package com.taotao.service;

import java.util.List;

import com.taotao.common.EasyUITreeNode;
import com.taotao.pojo.TbItem;
import com.taotao.utils.TaotaoResult;

public interface ItemCatService {
	/**
	 * @Description: 获取分类列表
	 * @param    
	 * @return List<EasyUITreeNode>
	 */
	List<EasyUITreeNode> getEasyUITreeNodes(Long parentId);
}
