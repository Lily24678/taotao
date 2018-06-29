package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.support.Parameter;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

/**
 * @Description: 购物车Controller
 * @author nq nianqiang@itcast.cn
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;

	@Value("${CART_COOKIE_KEY}")
	private String CART_COOKIE_KEY;
	@Value("${CART_COOKIE_EXPIRED}")
	private Integer CART_COOKIE_EXPIRED;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 先从cookie取当前的购物车列表
		List<TbItem> cartList = getCartList(request);
		// 如果购物车中已存在，数量累加
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().equals(itemId)) {
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
				break;
			}
		}
		// 否则根据id查询商品的信息后直接添加到购物车列表
		if (!flag) {
			TbItem item = itemService.getItemById(itemId);
			item.setNum(num);
			// 取一张图片
			String image = item.getImage();
			if (StringUtils.isNoneBlank(image)) {
				String[] images = image.split(",");
				item.setImage(images[0]);
			}
			cartList.add(item);
		}
		// 写回cookie
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY, 
				JsonUtils.objectToJson(cartList), 
				CART_COOKIE_EXPIRED,
				true);
		// 返回逻辑视图
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request) {
		// 取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		if (cartList.size()==0){
			return "cartNull";
		}
		// 传递给页面
		request.setAttribute("cartList", cartList);
		return "cart";
	}

	@RequestMapping(value = "/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().equals(itemId)) {
				tbItem.setNum(num);
				break;
			}
		}
		// 写回cookie
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY, 
				JsonUtils.objectToJson(cartList), 
				CART_COOKIE_EXPIRED,
				true);
		return TaotaoResult.ok(true);
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、从url中取商品id
		// 2、从cookie中取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		// 3、遍历列表找到对应的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 4、删除商品。
				cartList.remove(tbItem);
				break;
			}
		}
		// 5、把商品列表写入cookie。
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY, 
				JsonUtils.objectToJson(cartList), CART_COOKIE_EXPIRED, true);
		// 6、返回逻辑视图：在逻辑视图中做redirect跳转。
		return "redirect:/cart/cart.html";
	}


	/**
	 * @Description: 得到商品的购物车集合
	 * @param request
	 * @return List<TbItem>
	 */
	private List<TbItem> getCartList(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, CART_COOKIE_KEY, true);
		List<TbItem> list = new ArrayList<>();

		if (StringUtils.isNotBlank(json)) {
			list = JsonUtils.jsonToList(json, TbItem.class);
		}

		return list;
	}

}
