package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.common.SearchResult;
import com.taotao.pojo.TbItem;


public interface SearchDao {
	
	SearchResult search(SolrQuery query) throws Exception;
}
