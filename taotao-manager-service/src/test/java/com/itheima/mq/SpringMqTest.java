package com.itheima.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @Description: 测试 spring和mq 整合
 * @author nq nianqiang@itcast.cn
 */
public class SpringMqTest {

	@Test
	public void testSpringMQProducer() throws Exception {
		// 初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-mq.xml");
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 发送 点对点 的消息的 目的地，在spring容器中取
		Queue queue = (Queue) applicationContext.getBean("queueDestination");
		// 直接调用 send 方法 发送
		jmsTemplate.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 创建发送的消息
				TextMessage textMessage = session.createTextMessage
						("spring activemq test111");
				return textMessage;
			}
		});
	}

}
