package com.itheima.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.portal.util.JedisClient;

public class SpringJedisTest {

	@Test
	public void testJedisPool() {
		ApplicationContext context = new ClassPathXmlApplicationContext
				("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		
		jedisClient.hdel("a", "hello");
	}

}
