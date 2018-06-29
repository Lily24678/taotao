package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaotaoResult checkUserInfo(String param, Integer type) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type==1){
			// 校验用户名  
			criteria.andUsernameEqualTo(param);
		}else if (type==2){
			// 手机
			criteria.andPhoneEqualTo(param);
		}else if (type==3){
			// 邮箱
			criteria.andEmailEqualTo(param);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		
		if (null==list || list.size()==0){
			return TaotaoResult.ok(true);
		}
		
		return TaotaoResult.ok(false);
	}
	
	@Override
	public TaotaoResult createUser(TbUser user) {
		// 1、使用TbUser接收提交的请求。
		if (StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "密码不能为空");
		}
		//校验数据是否可用
		TaotaoResult result = checkUserInfo(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "此用户名已经被使用");
		}
		//校验电话是否可用
		if (StringUtils.isNotBlank(user.getPhone())) {
			result = checkUserInfo(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "此手机号已经被使用");
			}
		}
		//校验email是否可用
		if (StringUtils.isNotBlank(user.getEmail())) {
			result = checkUserInfo(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "此邮件地址已经被使用");
			}
		}
		// 2、补全TbUser其他属性。
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 3、密码要进行MD5加密。
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		// 4、把用户信息插入到数据库中。
		userMapper.insert(user);
		// 5、返回TaotaoResult。
		return TaotaoResult.ok();
	}
	
	@Override
	public TaotaoResult login(String username, String password) {
		// 1、判断用户名密码是否正确。
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//查询用户信息
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//校验密码
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		// 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
		String token = UUID.randomUUID().toString();
		// 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
		// 4、使用String类型保存Session信息。可以使用“前缀:token”为key
		user.setPassword(null); 
		jedisClient.set(USER_INFO + ":" + token, JsonUtils.objectToJson(user));
		// 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
		jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
 		
		// 6、返回TaotaoResult包装token。  jsessionid 在表现层存  cookie
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		
		String jsonUser = jedisClient.get(USER_INFO+":"+token);
		// 判断用户是否过期
		if (StringUtils.isBlank(jsonUser)){
			return TaotaoResult.build(400, "用户登录信息已过期");
		}
		// 重置用户过期时间
		jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
		TaotaoResult result = TaotaoResult.ok(user);
		
		return result;
	}

	@Override
	public TaotaoResult logout(String token) {
		if (!jedisClient.exists(USER_INFO+":"+token)){
			return TaotaoResult.build(400, "token信息过期");
		}
		jedisClient.expire(USER_INFO + ":" + token, 0);
		return TaotaoResult.ok(true);
	}



}
