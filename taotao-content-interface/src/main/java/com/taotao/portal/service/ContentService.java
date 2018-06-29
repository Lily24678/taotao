package com.taotao.portal.service;

import java.util.List;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.utils.TaotaoResult;

public interface ContentService {
	public List<TbContent> getContentList(Long cid,Integer page,Integer rows);
	public EasyUIDataGridResult<TbContent> getContentListByCategoryId(Long categoryId,
			Integer page,Integer rows);
	public TaotaoResult addOrupdateContent(TbContent content);
	public TaotaoResult deleteContentbyId(String ids);
}
