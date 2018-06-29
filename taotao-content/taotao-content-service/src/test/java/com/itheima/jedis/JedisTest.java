package com.itheima.jedis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/** 
 * @Description: redis 测试 单机版和集群版
 * @author nq  nianqiang@itcast.cn   
 */
public class JedisTest {

	@Test
	public void testJedis(){
		
		Jedis jedis =new Jedis("192.168.25.201", 6379);
		String val = jedis.get("key");
		System.out.println(val);
		jedis.close();
	}
	
	@Test
	public void testJedisPool(){
		JedisPool jedisPool = new JedisPool("192.168.25.201", 6379);
		Jedis jedis = jedisPool.getResource();
//		String val = jedis.get("key1");
//		System.out.println(val);
		
//		String hget = jedis.hget("hkey", "field1");
//		System.out.println(hget);
		
		
		Map<String, String> map = jedis.hgetAll("hkey");
		for (String key : map.keySet()) {
			System.out.println(key+":"+map.get(key));
		}
		
		jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster(){
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.201", 7001));
		nodes.add(new HostAndPort("192.168.25.201", 7002));
		nodes.add(new HostAndPort("192.168.25.201", 7003));
		nodes.add(new HostAndPort("192.168.25.201", 7004));
		nodes.add(new HostAndPort("192.168.25.201", 7005));
		nodes.add(new HostAndPort("192.168.25.201", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);
		
		String value = cluster.get("hello");
		cluster.append("keya", "hhhh");  // 追加
		System.out.println(value);
		
		cluster.close();
	}
}
