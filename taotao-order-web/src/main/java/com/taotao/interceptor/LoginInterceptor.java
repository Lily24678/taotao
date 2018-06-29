package com.taotao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.TaotaoResult;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	@Autowired
	private UserService userService;

	// 在handle执行之前 true：方行   false：拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中取token信息
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		String url = request.getRequestURL().toString();
		// 没有token，则直接重定向到 登录页面，并且把当前访问的url取到，以保证用户体验
		if (StringUtils.isBlank(token)){
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" + url);
			return false;
		}
		// 有token，判断token是否过期
		TaotaoResult result = userService.getUserByToken(token);
		if (result.getStatus()!=200){
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" + url);
			return false;
		}
		// 在request域中存入 用户信息
		request.setAttribute("user", result.getData());
		request.getSession().setAttribute("user", result.getData());
		
		// 不过期则 放行
		return true;
	}

	// 在执行handle之后，返回ModelAndView之前执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	// 在 执行Handle之后，并且返回ModelAndView之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
