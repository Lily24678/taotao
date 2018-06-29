<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>微信支付页</title>
        <link rel="icon" href="/images/favicon.ico">
		
	
    <link rel="stylesheet" type="text/css" href="/css/wx/webbase.css" />
    <link rel="stylesheet" type="text/css" href="/css/wx/pages-weixinpay.css" />
	<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="/js/qrious.min.js"></script>
	<script type="text/javascript" src="/js/base.js"></script>	
</head>

	<body>
		<!--head-->
		<div class="top">
			<div class="py-container">
				<div class="shortcut">
					<ul class="fl">
						<li class="f-item">淘淘商城欢迎您！</li>
						<li class="f-item">请登录　<span><a href="#">免费注册</a></span></li>
					</ul>
					<ul class="fr">
						<li class="f-item">我的订单</li>
						<li class="f-item space"></li>
						<li class="f-item">我的淘淘商城</li>
						<li class="f-item space"></li>
						<li class="f-item">淘淘商城会员</li>
						<li class="f-item space"></li>
						<li class="f-item">企业采购</li>
						<li class="f-item space"></li>
						<li class="f-item">关注淘淘商城</li>
						<li class="f-item space"></li>
						<li class="f-item">客户服务</li>
						<li class="f-item space"></li>
						<li class="f-item">网站导航</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="cart py-container">
			<!--logoArea-->
			<div class="logoArea">
				<div class="fl logo"><span class="title">收银台</span></div>

			</div>
			<!--主内容-->
			<div class="checkout py-container  pay">
				<div class="checkout-tit">
					<h4 class="fl tit-txt"><span class="success-icon"></span><span id="order_id" class="success-info"></span></h4>
                    <span class="fr"><em class="sui-lead">应付金额：</em><em  class="orange money">￥<money id="money_id"></money></em>元</span>
					<div class="clearfix"></div>
				</div>				
				<div class="checkout-steps">
					<div class="fl weixin">微信支付</div>
                    <div class="fl sao"> 
                        <p class="red"></p>                      
                        <div class="fl code">
                        
                            <img id="qrious">
                            
                            <div class="saosao">
                                <p>请使用微信扫一扫</p>
                                <p>扫描二维码支付</p>
                            </div>
                        </div>
                        <div class="fl phone">
                            
                        </div>
                        
                    </div>
                    <div class="clearfix"></div>
				    <p><a href="#" target="_blank">> 其他支付方式</a></p>
				</div>
			</div>

		</div>
		<!-- 底部栏位 -->
		<!--页面底部-->
<div class="clearfix footer">
	<div class="py-container">
		<div class="footlink">
			<div class="Mod-service">
				<ul class="Mod-Service-list">
					<li class="grid-service-item intro  intro1">

						<i class="serivce-item fl"></i>
						<div class="service-text">
							<h4>正品保障</h4>
							<p>正品保障，提供发票</p>
						</div>

					</li>
					<li class="grid-service-item  intro intro2">

						<i class="serivce-item fl"></i>
						<div class="service-text">
							<h4>正品保障</h4>
							<p>正品保障，提供发票</p>
						</div>

					</li>
					<li class="grid-service-item intro  intro3">

						<i class="serivce-item fl"></i>
						<div class="service-text">
							<h4>正品保障</h4>
							<p>正品保障，提供发票</p>
						</div>

					</li>
					<li class="grid-service-item  intro intro4">

						<i class="serivce-item fl"></i>
						<div class="service-text">
							<h4>正品保障</h4>
							<p>正品保障，提供发票</p>
						</div>

					</li>
					<li class="grid-service-item intro intro5">

						<i class="serivce-item fl"></i>
						<div class="service-text">
							<h4>正品保障</h4>
							<p>正品保障，提供发票</p>
						</div>

					</li>
				</ul>
			</div>
			<div class="clearfix Mod-list">
				<div class="yui3-g">
					<div class="yui3-u-1-6">
						<h4>购物指南</h4>
						<ul class="unstyled">
							<li>购物流程</li>
							<li>会员介绍</li>
							<li>生活旅行/团购</li>
							<li>常见问题</li>
							<li>购物指南</li>
						</ul>

					</div>
					<div class="yui3-u-1-6">
						<h4>配送方式</h4>
						<ul class="unstyled">
							<li>上门自提</li>
							<li>211限时达</li>
							<li>配送服务查询</li>
							<li>配送费收取标准</li>
							<li>海外配送</li>
						</ul>
					</div>
					<div class="yui3-u-1-6">
						<h4>支付方式</h4>
						<ul class="unstyled">
							<li>货到付款</li>
							<li>在线支付</li>
							<li>分期付款</li>
							<li>邮局汇款</li>
							<li>公司转账</li>
						</ul>
					</div>
					<div class="yui3-u-1-6">
						<h4>售后服务</h4>
						<ul class="unstyled">
							<li>售后政策</li>
							<li>价格保护</li>
							<li>退款说明</li>
							<li>返修/退换货</li>
							<li>取消订单</li>
						</ul>
					</div>
					<div class="yui3-u-1-6">
						<h4>特色服务</h4>
						<ul class="unstyled">
							<li>夺宝岛</li>
							<li>DIY装机</li>
							<li>延保服务</li>
							<li>淘淘商城E卡</li>
							<li>淘淘商城通信</li>
						</ul>
					</div>
					<div class="yui3-u-1-6">
						<h4>帮助中心</h4>
						<img src="/images/wx_cz.jpg">
					</div>
				</div>
			</div>
			<div class="Mod-copyright">
				<ul class="helpLink">
					<li>关于我们<span class="space"></span></li>
					<li>联系我们<span class="space"></span></li>
					<li>关于我们<span class="space"></span></li>
					<li>商家入驻<span class="space"></span></li>
					<li>营销中心<span class="space"></span></li>
					<li>友情链接<span class="space"></span></li>
					<li>关于我们<span class="space"></span></li>
					<li>营销中心<span class="space"></span></li>
					<li>友情链接<span class="space"></span></li>
					<li>关于我们</li>
				</ul>
				<p>地址：北京市昌平区建材城西路金燕龙办公楼一层 邮编：100096 电话：400-618-4000 传真：010-82935100</p>
				<p>京ICP备08001421号京公网安备110108007702</p>
			</div>
		</div>
	</div>
</div>
<!--页面底部END-->
		
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery.easing/jquery.easing.min.js"></script>
<!-- <script type="text/javascript" src="/js/sui/sui.min.js"></script> -->
<!-- <script type="text/javascript" src="/js/widget/nav.js"></script> -->
<script type="text/javascript" src="/js/qrious.min.js"></script>
<script type="text/javascript">
	function queryPayStatus(order_no) {
		
		$.post("/order/pay/queryPayStatus.action",{out_trade_no:order_no},function(data){
			if (data.status == 200) {
				// encodeURIComponent(String)
				var money = $("#money_id").html();
				// paysuccess.jsp?total_fee=0.01
				location.href="/paysuccess.jsp?total_fee="+encodeURIComponent(money);
			} else {
				if (data.status == -2){
	// 				alert(data.msg);
					// 失效后重新生成二维码
	// 				closedNativePayOrder(order_no);
					createNative();
				}else{
					location.href="/payfail.jsp";
				}			
			}
				
		});
	}
	
	function closedNativePayOrder(order_no){
		$.post("/order/pay/closedNativePayOrder.action",{out_trade_no:order_no},function(data){
			if (data.status == -1) {
// 				alert("创建失败，原因是：" + data.msg);
				location.href="/payfail.jsp";
			}
		});
	}

	function createNative(){
		// 生成二维码显示页面数据 
		$.post("/order/pay/createNative.action","",function(data){
			if (data.status == 200) {
				// 显示金额和订单号
				$("#order_id").html("订单提交成功，请您及时付款！订单号："+data.data.out_trade_no);
				var money = (data.data.total_fee/100). toFixed(2);
				$("#money_id").html(money);
				var code_url = data.data.code_url; 
// 				alert(code_url);
				//生成二维码
				 var qr=new QRious({
					    element:document.getElementById('qrious'),
						size:250,
						value:code_url,
						level:'H'
			     });
				
				// 生成二维码后调用查询接口
				 queryPayStatus(data.data.out_trade_no);
			} else {
				alert("创建失败，原因是：" + data.msg);
			}
		});
	}

	$(function(){
		createNative();
	});
// createNative();
</script>
</body>

</html>