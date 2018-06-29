package com.taotao.order.Controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.order.service.OrderService;
import com.taotao.pay.service.WeixinPayService;
import com.taotao.pojo.TbPayLog;
import com.taotao.pojo.TbUser;
import com.taotao.utils.TaotaoResult;

/** 
 * @Description: 支付调用处理
 * @author nq  nianqiang@itcast.cn   
 */
@RestController
@RequestMapping("/order/pay")
public class PayController {
	
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/createNative")
	public TaotaoResult createNative(HttpSession session){
//		return TaotaoResult.ok(weixinPayService.createNative(new IdWorker().nextId()+"", "1"));
		//1.获取当前登录用户
		TbUser user = (TbUser) session.getAttribute("user");
		if (user!=null){
			//2.提取支付日志（从缓存 ）
			TbPayLog payLog = orderService.searchPayLogFromRedis(user.getId()+"");
			//3.调用微信支付接口
			if(payLog!=null){
				Map map = weixinPayService.createNative(payLog.getOutTradeNo(),"1");		
//				Map map = weixinPayService.createNative(payLog.getOutTradeNo(), payLog.getTotalFee()+"");
				return TaotaoResult.ok(map);
			}else{
				return TaotaoResult.build(-2, "微信支付出现异常");
			}		
		}else{
			return TaotaoResult.build(-3, "用户登录异常");
		}
	}
	/**
	 * @Description: 查询订单的状态
	 * 问题：如何知道当前用户是否支付呢？
	 * @param    
	 * @return TaotaoResult
	 */
	@RequestMapping("/queryPayStatus")
	public TaotaoResult queryPayStatus(String out_trade_no){
		TaotaoResult result = null;
		int flag = 0;
		while(true){
			Map map = weixinPayService.queryPayStatus(out_trade_no);
			System.out.println("订单号："+out_trade_no);
			System.out.println("结果："+map);
			// 支付失败
			if (map==null){
				result = TaotaoResult.build(-1, "支付出错");
				break;
			}
			
			// 支付成功
			if (map.get("trade_state").equals("SUCCESS")){
				result = TaotaoResult.build(200, "支付成功");
				System.out.println(out_trade_no+"-支付成功....");
				// 更新相关表的状态
				orderService.updateOrderStatus(out_trade_no, map.get("transaction_id")+"");
				break;
			}
			
			try {
				Thread.sleep(3000);//间隔三秒调用一次？
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			
			flag++;
			
			if (flag >=100){ // 超过5分钟 当前订单支付失效
				result = TaotaoResult.build(-2, "二维码失效");
				break;
			}
		}
		return result;
	}
	/**
	 * @Description: 关闭订单状态
	 * 以下情况需要调用关单接口：
	 * 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
	 * 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
	 *	注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
	 * @param    
	 * @return TaotaoResult
	 */
	@RequestMapping("closedNativePayOrder")
	public TaotaoResult closedNativePayOrder(String out_trade_no) {
		Map map = weixinPayService.closeNativeOrder(out_trade_no);
		if (map!=null && "SUCCESS".equals(map.get("result_code"))){
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(-1, "订单关闭失败");
	}
	
	@RequestMapping("queryOrderPayResult")
	public TaotaoResult queryOrderPayResult() {
		
		System.out.println("微信调用....");
		
		return TaotaoResult.ok();
	}
	

}
