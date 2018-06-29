package com.taotao.search.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.Log;

/** 
 * @Description: 全局异常处理器
 * @author nq  nianqiang@itcast.cn   
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static Logger log = Logger.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// 写入到日志文件
		log.error("系统异常信息为：", ex);
		// 通知相关开发人员（短信、邮件等）
		// 给客户提供友好的界面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "您的网络开小差了，请稍后重试！");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
