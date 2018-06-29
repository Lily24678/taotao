package com.taotao.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public TaotaoResult getItemDescById(Long id) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TbItemDesc findItemDescById(Long id) {
		// 先查缓存，缓存中有就直接返回
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":DESC");
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				// 重置过期时间  保证是热点数据
				jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":DESC", REDIS_ITEM_EXPIRE);
				System.out.println("缓存中的商品详情数据......");
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 没有就查询数据库
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		// 查询结束后向缓存中添加
		try {
			// 添加缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":DESC", JsonUtils.objectToJson(itemDesc));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":DESC", REDIS_ITEM_EXPIRE);
			System.out.println("数据库中的商品详情数据，存入缓存中......");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemDesc;
	}

}
