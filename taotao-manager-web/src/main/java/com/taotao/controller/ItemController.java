package com.taotao.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 商品管理Controller
 * @author nq  nianqiang@itcast.cn 
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name = "itemTopicDestination")
	private Destination itemTopicDestination;
	
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String showMenu(@PathVariable String page){
		return page;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult<TbItem> showItemList(Integer page,Integer rows){
		EasyUIDataGridResult<TbItem> list = itemService.getItemList(page, rows);
		return list;
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult addItem(TbItem item,String desc){
		TaotaoResult result = itemService.addItem(item, desc);
		if (result.getStatus()==200){
			TbItem items = (TbItem) result.getData();
			// 发布消息
			if (null!=items)
			SendChangeItemInfoMQ(items.getId(), items.getStatus());
		}
		return result;
	}
	
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item,String desc) {
		TaotaoResult result = itemService.updateItem(item,desc);
		// 发布消息
		if (result.getStatus()==200){
			SendChangeItemInfoMQ(item.getId(), (byte) 0);
		}
		return result;
	}
	
	@RequestMapping("/rest/item/updateStatus")
	@ResponseBody
	public TaotaoResult updateItemStatus(String ids,Byte status){
		TaotaoResult result = itemService.updateItemStatus(ids, status);
		
		if (StringUtils.isNotBlank(ids)) {
			String[] strings = ids.split(",");
			for (String id : strings) {
				long itemId = Long.parseLong(id);
				// 发布消息
				SendChangeItemInfoMQ(itemId, status);
			}
		} else {
			return TaotaoResult.build(0, "选择的ID为空");
		}
		
		return result;
	}
	
	private void SendChangeItemInfoMQ(final Long id, final Byte type) {
		// 添加商品成功后，发布商品添加消息 （商品的id）
		jmsTemplate.send(itemTopicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException{
				TextMessage message = session.createTextMessage(id + "-" + type);
				System.out.println("发布消息成功..." + id + "-" + type);
				return message;
			}
		});
	}
}
