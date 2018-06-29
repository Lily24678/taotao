package com.itheima.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * @Description: 测试activemq
 * @author nq nianqiang@itcast.cn
 */
public class ActiveMQTest {

	@Test
	public void testQueueProducer() throws Exception {
		// 创建ConnectionFactory对象，需要指定服务端ip及端口号
		// brokerURL : ip及端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.201:61616");
		// 使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		// 开启连接，调用Connection对象的start方法
		connection.start();
		// 使用Connection对象创建一个Session对象
		// 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		// 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象
		Queue queue = session.createQueue("test-queue");
		// 使用Session对象创建一个Producer对象
		MessageProducer producer = session.createProducer(queue);
		// 创建一个Message对象，创建一个TextMessage对象
		TextMessage textMessage = session.createTextMessage("这是一个queue队列消息测试");
		// 发送消息
		producer.send(textMessage);
		// 释放资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testConsumerQueue() throws Exception {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.201:61616");
		// 创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建Destination
		Queue queue = session.createQueue("test-queue");
		// 创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		// 取消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	// topic 消息类型的生产者测试
	@Test
	public void testTopicProducer() throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.201:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageProducer producer = session.createProducer(topic);
		
		TextMessage message = session.createTextMessage();
		message.setText("这是topic的测试信息q...");
		
		producer.send(message);
		
		producer.close();
		session.close();
		connection.close();
	}
	
	// topic 消费者测试
	@Test
	public void testTopicConsumer() throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.201:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();		
	}
}
