package com.taotao.search.service;

import com.taotao.common.SearchResult;
import com.taotao.utils.TaotaoResult;

public interface SearchItemService {
	TaotaoResult addAllItemList() throws Exception;
	public TaotaoResult addItemDocument(Long id) throws Exception;
	public TaotaoResult deleteItemDocumentById(Long id);
	public SearchResult search(String queryString, int page, int rows) throws Exception ;
}
