package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.impl.SearchItemServiceImpl;

public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchItemServiceImpl searchItemServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null; 
			//取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				String[] strings = textMessage.getText().split("-");
				itemId = Long.parseLong(strings[0]);
				int type = Integer.parseInt(strings[1]);
				// 根据状态不同来处理索引库 
				System.out.println(itemId+"-----"+type);
				//向索引库添加文档
				if (type>1){
					// 2-下架，3-删除, 删除索引库对应的商品
					searchItemServiceImpl.deleteItemDocumentById(itemId);
				}else{
					// 1-添加、0-更新, 添加商品到索引库，id相等会覆盖
					searchItemServiceImpl.addItemDocument(itemId);
				}
				// 日志记录
				System.out.println("调用成功添加索引库");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
