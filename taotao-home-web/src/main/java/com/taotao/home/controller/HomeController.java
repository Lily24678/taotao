package com.taotao.home.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.home.pojo.TbReciveAddress;
import com.taotao.home.service.HomeService;
import com.taotao.pojo.TbUser;
import com.taotao.utils.TaotaoResult;

@Controller
@RequestMapping("/myhome")
public class HomeController {

	@RequestMapping("/my-address")
	public String showPage(HttpServletRequest request, Model model) {
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbReciveAddress> list = homeService.queryAllAddrByUserId(user.getId());
		model.addAttribute("addrs", list);
		if (list.size() > 0) {
			model.addAttribute("size", list.size());
		} else {
			model.addAttribute("size", 0);
		}
		return "my-address";
	}

	@Autowired
	private HomeService homeService;

	@RequestMapping("/addAddress")
	@ResponseBody
	public TaotaoResult addAddr(TbReciveAddress reciveAddress, HttpServletRequest request) {
		// 取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		reciveAddress.setUserId(user.getId());
		homeService.save(reciveAddress);
		return TaotaoResult.ok();
	}

	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult delete(@RequestParam Long id) {
		return homeService.delete(id);
	}

	@RequestMapping("/changeDefalut")
	@ResponseBody
	public TaotaoResult changeDefalut(@RequestParam Long id) {
		return homeService.changeIsdefault(id);
	}

	@RequestMapping("/editAliasName")
	@ResponseBody
	public TaotaoResult editAliasName(TbReciveAddress reciveAddress) {
		return homeService.updateAddress(reciveAddress);
	}

}
