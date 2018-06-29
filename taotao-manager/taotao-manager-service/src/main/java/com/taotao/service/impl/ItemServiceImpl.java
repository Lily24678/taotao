package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 商品管理service
 * @author nq nianqiang@itcast.cn
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	// @Autowired
	// private JmsTemplate jmsTemplate;
	// @Resource(name = "itemTopicDestination")
	// private Destination itemTopicDestination;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	/**
	 * 显示商品列表
	 */
	@Override
	public EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows) {

		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		EasyUIDataGridResult<TbItem> result = new EasyUIDataGridResult<>();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());

		System.out.println("分页的总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		System.err.println("当前页：" + pageInfo.getPageNum());
		System.out.println("一页显示的记录数：" + pageInfo.getPageSize());
		System.out.println("当前查询的列表的size：" + pageInfo.getList().size());
		System.out.println("上一页：" + pageInfo.getPrePage());
		System.out.println("下一页：" + pageInfo.getNextPage());
		System.out.println("最后一页：" + pageInfo.getLastPage());

		return result;
	}

	public TaotaoResult addItem(TbItem item, String desc) {
		// 商品信息（除详情）
		final Long id = IDUtils.genItemId();
		item.setId(id);
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(date);

		itemMapper.insertSelective(item);

		// 商品详情
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		itemDesc.setUpdated(date);
		itemDescMapper.insertSelective(itemDesc);

		// 发布消息
		// SendChangeItemInfoMQ(id, item.getStatus());

		// 缓存同步
		// jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":BASE", 0);
		// jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":DESC", 0);

		return TaotaoResult.ok(item);
		
	}

	/*
	 * private void SendChangeItemInfoMQ(final Long id, final Byte type) { //
	 * 添加商品成功后，发布商品添加消息 （商品的id） jmsTemplate.send(itemTopicDestination, new
	 * MessageCreator() {
	 * 
	 * @Override public Message createMessage(Session session) throws
	 * JMSException { TextMessage message = session.createTextMessage(id + "-" +
	 * type); System.out.println("发布消息成功..." + id + "-" + type); return message;
	 * } }); }
	 */

	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		// 更新商品主信息
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);

		// 更新商品的描述
		Long id = item.getId();

		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		if (null != itemDesc) {
			itemDesc.setItemId(id);
			itemDesc.setUpdated(new Date());
			itemDesc.setItemDesc(desc);
			itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		} else {
			itemDesc = new TbItemDesc();
			itemDesc.setCreated(new Date());
			itemDesc.setItemDesc(desc);
			itemDesc.setItemId(id);
			itemDesc.setUpdated(new Date());
			itemDescMapper.insertSelective(itemDesc);
		}
		// 发布消息
		// SendChangeItemInfoMQ(id, (byte) 0);

		// 缓存同步
		jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":BASE", 0);
		jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":DESC", 0);

		return TaotaoResult.ok();
	}

	/**
	 * 修改商品的状态 '商品状态，1-正常，2-下架，3-删除',
	 */
	public TaotaoResult updateItemStatus(String ids, Byte status) {
		if (StringUtils.isNotBlank(ids)) {
			String[] strings = ids.split(",");
			for (String id : strings) {
				long itemId = Long.parseLong(id);
				TbItem item = itemMapper.selectByPrimaryKey(itemId);
				item.setUpdated(new Date());
				item.setStatus(status);
				itemMapper.updateByPrimaryKeySelective(item);

				// 发布消息
				// SendChangeItemInfoMQ(itemId, status);
				// 缓存同步
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":BASE", 0);
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":DESC", 0);
			}
		} else {
			return TaotaoResult.build(0, "选择的ID为空");
		}


		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(Long id) {
		// 先查缓存，缓存中有就直接返回
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":BASE");
			if (StringUtils.isNotBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				// 保证是热点数据， 需要重新初始化过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":BASE", REDIS_ITEM_EXPIRE);
				System.out.println("缓存中的商品数据......");
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 没有就查询数据库
		TbItem item = itemMapper.selectByPrimaryKey(id);
		// 查询结束后向缓存中添加
		try {
			// 添加缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":BASE", JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":BASE", REDIS_ITEM_EXPIRE);
			System.out.println("数据库中的商品数据，存入缓存中......");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

}
