package com.taotao.portal.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.portal.service.ContentService;
import com.taotao.portal.util.JedisClient;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_AD_KEY}")
	private String CONTENT_AD_KEY;
	
	
	/**
	 * 根据分类查询内容列表
	 */
	public EasyUIDataGridResult<TbContent> getContentListByCategoryId(Long categoryId,Integer page,Integer rows) {
		
		PageHelper.startPage(page,rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		PageInfo<TbContent> info = new PageInfo<>(list);
		EasyUIDataGridResult<TbContent> result = new EasyUIDataGridResult<>();
		
		result.setTotal(info.getTotal());
		result.setRows(list);
		
		return result;
	}

	/**
	 * 添加或者是修改内容 ：根据当前是否存在 主键 id
	 */
	public TaotaoResult addOrupdateContent(TbContent content) {
		if (content!=null && content.getId()!=null){
			// 修改
			content.setUpdated(new Date());
			contentMapper.updateByPrimaryKeySelective(content);
		}else{
			// 添加
			content.setCreated(new Date());
			content.setUpdated(new Date());
			contentMapper.insertSelective(content);
		}
		// 缓存同步
		try {
			jedisClient.hdel(CONTENT_AD_KEY, content.getCategoryId()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}

	public TaotaoResult deleteContentbyId(String ids) {
		String[] strings = ids.split(",");
		TbContent tbContent = null;
		for (String sid : strings) {
			Long id = Long.parseLong(sid);
			tbContent = contentMapper.selectByPrimaryKey(id);
			contentMapper.deleteByPrimaryKey(id);
		}
		// 缓存同步
		try {
			jedisClient.hdel(CONTENT_AD_KEY, tbContent.getCategoryId()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentList(Long cid, Integer page, Integer rows) {
//		Integer prkey = page+rows;
		// 查询数据库前先查询缓存中是否存在
//		System.out.println(CONTENT_AD_KEY);
		try {
			String json = jedisClient.hget(CONTENT_AD_KEY, cid+"");
			if (StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//  日志  邮件
		}
		// 查询数据库
		PageHelper.startPage(page,rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 将查询结果添加到缓存中
		// 由于携带分页信息，可能会导致在同一个分类下请求下一页的情况，所以将当前的prkey当做key的一部分
		try {
			jedisClient.hset(CONTENT_AD_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
