package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping(value="/user/check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult checkUserInfo(@PathVariable String param,@PathVariable Integer type){
		TaotaoResult result = userService.checkUserInfo(param, type);
		return result;
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		TaotaoResult result = userService.createUser(user);
		return result;
	}
	@RequestMapping(value="/user/logout/{token}")
	@ResponseBody
	public String logout(@PathVariable String token,String callback,HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = userService.logout(token);
		
		if (StringUtils.isNotBlank(callback)){
			// 说明要支持跨域请求
			String jsonResult = callback +"("+JsonUtils.objectToJson(result)+");";
			return jsonResult;
		}
		
		CookieUtils.deleteCookie(request, response, COOKIE_TOKEN_KEY);
		return JsonUtils.objectToJson(result);
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		
		// 1、接收两个参数。
		// 2、调用Service进行登录。
		TaotaoResult result = userService.login(username, password);
		
		// 判断是否验证通过
		if (result.getStatus()==200){
			// 3、从返回结果中取token，写入cookie。Cookie要跨域。
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		}
		// 4、响应数据。Json数据。TaotaoResult，其中包含Token。
		return result;
	}
	
	/*@RequestMapping(value = "/user/token/{token}"
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		
		if (StringUtils.isNotBlank(callback)){
			System.out.println("回调方法：callback="+callback);
			// 说明要支持跨域请求
			String jsonResult = callback +"("+JsonUtils.objectToJson(result)+");";
			return jsonResult;
		}
		
		return JsonUtils.objectToJson(result);
	}*/
	// 方法二：在spring4.1以后的版本使用
	@RequestMapping(value = "/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		System.out.println(token);
		if (StringUtils.isNotBlank(callback)){
			System.out.println("回调方法：callback="+callback);
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/user/tokens",method=RequestMethod.POST)
	@ResponseBody
	public Object getUserByTokens(@PathVariable String token,String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		System.out.println("tokens======"+token);
		if (StringUtils.isNotBlank(callback)){
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		
		return result;
	}

}
