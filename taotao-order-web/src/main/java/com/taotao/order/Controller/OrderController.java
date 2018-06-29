package com.taotao.order.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbUser;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClient;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

import javassist.tools.reflect.Loader;

@Controller
public class OrderController {

	@Value("${CART_COOKIE_EXPIRED}")
	private Integer CART_COOKIE_EXPIRED;

	@Value("${CART_COOKIE_KEY}")
	private String CART_COOKIE_KEY;

	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/order-cart")
	public String getCartOrder(HttpServletRequest request) {
		// 获得购物车列表
		List<TbItem> cartList = getCartList(request);
		// 在这前 要求用户必须登录
		// 用户id 获得当前的 配送地址
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user.getUsername() + "---" + user.getId());

		// 查询支付方式及用户绑定的银行卡
		request.setAttribute("cartList", cartList);

		return "order-cart";
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

	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo,
			HttpServletRequest request ,HttpServletResponse response,Model model) {
		
		// 取用户的信息
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		TaotaoResult result = orderService.createOrder(orderInfo);
		// 数据回显
		if (result!=null && result.getStatus()==200){
			model.addAttribute("orderId", orderInfo.getOrderId());
			model.addAttribute("payment", orderInfo.getPayment());
			
			DateTime dateTime = new DateTime();
			dateTime = dateTime.plusDays(3);
			model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		}
		// 将购物车对应的商品删除？
		try {
			List<TbOrderItem> orderItems = orderInfo.getOrderItems();
			List<String> sList = new ArrayList<>();
			for (TbOrderItem orderItem : orderItems) {
				sList.add(orderItem.getItemId());
			}
			deleteCartItem(sList, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4、返回逻辑视图展示成功页面
//		return "success";
		return "pay";
	}
	
	public String deleteCartItem(List<String> itemIds, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、从url中取商品id
		// 2、从cookie中取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		// 3、遍历列表找到对应的商品
		if (cartList!=null && cartList.size()>0){
			for (int i=0; i<cartList.size();i++) {
				for (String sItemId : itemIds) {
					Long itemId = new Long(sItemId);
					if (cartList.get(i).getId() == itemId.longValue()) {
						// 4、删除商品。
						cartList.remove(i);
					}
				}
			}
		}
		
		// 5、把商品列表写入cookie。
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY, 
				JsonUtils.objectToJson(cartList), CART_COOKIE_EXPIRED, true);
		return "ok";
	}
	


}
