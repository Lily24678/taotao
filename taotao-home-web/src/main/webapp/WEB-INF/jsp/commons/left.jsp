<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="left" class="g-3-5 c-0">
	<!--js 加载异步加载的左侧菜单 -->
	<div id="menu">
		<h3>个人中心</h3>
		<dl class="fore1">
			<dt>
				<a clstag="homepage|keycount|home2013|hdd" id="_MYJD_ordercenter" 
					href="/orderList.html" <c:if test="${param.page_index == 1}">class="curr"</c:if>>我的订单</a>
			</dt>
		</dl>
		<dl class="fore2">
			<dt>
				<a clstag="homepage|keycount|home2013|hdd"
					id="_MYJD_ordercenter"
					href="/my-order-comment.html" <c:if test="${param.page_index == 2}">class="curr"</c:if>>评价晒单</a>
			</dt>
		</dl>
		<dl class="fore3">
			<dt>
				<a clstag="homepage|keycount|home2013|hyushou"
					id="_MYJD_yushou"
					href="/myhome/my-address.html" <c:if test="${param.page_index == 3}">class="curr"</c:if>>收货地址</a>
			</dt>
		</dl>
		<dl class="fore4">
			<dt>
				<a clstag="homepage|keycount|home2013|hbdsh"
					id="_MYJD_locallife"
					href="/my-info.html" <c:if test="${param.page_index == 4}">class="curr"</c:if>>个人信息</a>
			</dt>
		</dl>
		<dl class="fore5">
			<dt>
				<a clstag="homepage|keycount|home2013|hdqs"
					id="_MYJD_ding" href="#">我的定期送</a>
			</dt>
		</dl>
		<dl class="fore6">
			<dt>
				<a clstag="homepage|keycount|home2013|htg"
					id="_MYJD_tuan" href="#">我的团购</a>
			</dt>
		</dl>
		<dl class="fore7">
			<dt>
				<a clstag="homepage|keycount|home2013|hjg"
					id="_MYJD_protection" href="#">价格保护</a>
			</dt>
		</dl>
	</div>
</div>