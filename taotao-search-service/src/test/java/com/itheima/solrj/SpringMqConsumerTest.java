package com.itheima.solrj;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: spring整合mq 的 消费者测试
 * @author nq nianqiang@itcast.cn
 */
public class SpringMqConsumerTest {

	@Test
	public void testMqConsumer() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-mq.xml");
		// 等待
		System.in.read();
	}

}
