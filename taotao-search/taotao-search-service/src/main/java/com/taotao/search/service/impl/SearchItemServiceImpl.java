package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.SearchItem;
import com.taotao.common.SearchResult;
import com.taotao.pojo.TbItem;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 商品Service
 * @author nq nianqiang@itcast.cn
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SearchDao searchDao;

	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult addAllItemList() throws Exception {

		PageHelper.startPage(1, 1000);
		List<SearchItem> list = searchItemMapper.getItemList();
		PageInfo<SearchItem> pageInfo = new PageInfo<>(list);
		int totalPage = pageInfo.getPages();

		for (int i = 1; i <= totalPage; i++) {

			PageHelper.startPage(i, 1000);
			List<SearchItem> searchItems = searchItemMapper.getItemList();
			System.out.println(i + "====" + searchItems);
			for (SearchItem searchItem : searchItems) {
				SolrInputDocument document = new SolrInputDocument();
				// 为文档添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				// 向索引库中添加文档。
				solrServer.add(document);
				// 提交修改
				solrServer.commit();
			}

		}

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult addItemDocument(Long id) throws Exception {
		
		SearchItem searchItem = searchItemMapper.getItemById(id);

		SolrInputDocument document = new SolrInputDocument();
		if (null!=searchItem){
			// 为文档添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			// 向索引库中添加文档。
			// 提交修改
			solrServer.add(document);
			solrServer.commit();
			System.out.println("成功添加索引库");
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemDocumentById(Long id) {
		try {
			solrServer.deleteById(id+"");
			solrServer.commit();
			System.out.println("删除成功，索引库中的商品的id为："+id);
			return TaotaoResult.ok();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 1、创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 2、设置查询条件
		query.setQuery(queryString);
		// 3、设置分页条件
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 4、需要指定默认搜索域。
		query.set("df", "item_title");
		// 5、设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 6、执行查询，调用SearchDao。得到SearchResult
		SearchResult result = searchDao.search(query);
		// 7、需要计算总页数。
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		// 8、返回SearchResult
		return result;
	}

	

}
