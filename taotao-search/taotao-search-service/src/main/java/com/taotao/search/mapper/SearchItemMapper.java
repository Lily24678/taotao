package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.SearchItem;
import com.taotao.pojo.TbItem;

public interface SearchItemMapper {
	
	List<SearchItem> getItemList();
	
	SearchItem getItemById(Long id);
}
