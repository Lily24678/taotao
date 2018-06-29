<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的订单 - 淘淘</title>
<meta name="Keywords" content="java,淘淘java" />
<meta name="description"
	content="在淘淘中找到了29910件java的类似商品，其中包含了“图书”，“电子书”，“教育音像”，“骑行运动”等类型的java的商品。" />
<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.common.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.order.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.easebuy.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/colorbox.css"
	media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript">
$(function(){
	if($("#edit_iframe").colorbox.close()){
		window.location.reload();
	}
});
	function alertAddAddress() {
		$("#edit_iframe").colorbox({
			transition:	"fade",
			iframe : true,
			width : "40%",
			height : "80%",
			href : "/address-edit.html"
		});
		
	}
	
	function alertDelAddressDiag(id){
		var msg=confirm("你确定删除此该收货地址吗？");

		if(msg==true){
			var params = {"id":id};
        	$.post("/myhome/delete.action",params, function(data){
    			if(data.status == 200){
    				alert("删除成功！");
    				window.location.reload();
    			}
    		});
		}else{
			return ;
		}
	}
	function makeAddressAllDefault(id){
		var params = {"id":id};
    	$.post("/myhome/changeDefalut.action",params, function(data){
			if(data.status == 200){
				alert("设置默认地址成功！");
				window.location.reload();
			}
		});
	}
	function editAliasName(){
		$("#alias-form-137805074").show();
		
	}
	function saveAddessAlias(id){
		var name=$("#ipt-text-"+id).val();
		 var params = {"id":id,"addrName":name};
    	$.post("/myhome/editAliasName.action",params, function(data){
			if(data.status == 200){
				alert("修改成功！");
				window.location.reload();
			}
		}); 
	}
	
</script>

</head>
<body>
	<!-- header start -->
	<jsp:include page="commons/header.jsp" />
	<!-- header end -->



	<div id="container">

		<div class="w">
			<div id="main">
				<div class="g-0">
					<div id="content" class="c-3-5">
						<div id="edit_iframe" class="mod-main mod-comm">
							<div class="mt">
								<a id="edit_addr" onclick="alertAddAddress()"
									class="e-btn add-btn btn-5">新增收货地址</a> <span class="ftx-03">
									您已创建 <span id="addressNum_top" class="ftx-02">${size}</span>
									个收货地址，最多可创建 <span class="ftx-02">20</span> 个
								</span>
							</div>
							<c:forEach items="${addrs }" var="addr">
								<div class="mc">
									<div class="sm easebuy-m " id="addresssDiv-137805074">
										<div class="smt">
											<h3>
												${addr.addrName }<a
													onclick="modifyAliasTips(137805074,event);"
													id="alias-edit-137805074" class="alias-edit"
													href="javascript:editAliasName();"></a>
											</h3>
											<div class="extra">
												<a onclick="alertDelAddressDiag(${addr.id});" class="del-btn"
													href="#none">删除</a>
											</div>
											<div id="alias-form-137805074" class="alias-form hide">
												<div class="alias-new">
													<input type="text" class="ipt-text" id="ipt-text-${addr.id }"
														value="${ addr.addrName}"
														onblur="checkConsigneeAlias('ipt-text-${addr.id }')"
														maxlength="20">
														<button type="button" class="btn-save"
															onclick="saveAddessAlias(${addr.id})">保存</button>
												</div>
												<div class="alias-common">
													<div class="ac-tip">建议填写常用名称：</div>
													<div class="ac-con">
														<a
															href="javascript:setAddressAilas(137805074,'home-137805074');"
															id="home-137805074" class="item">家里</a> <a
															href="javascript:setAddressAilas(137805074,'parentHome-137805074');"
															id="parentHome-137805074" class="item">父母家</a> <a
															href="javascript:setAddressAilas(137805074,'company-137805074');"
															id="company-137805074" class="item">公司</a>
													</div>
													<span class="error-msg" id="error_ipt-text-137805074"></span>
												</div>
											</div>
										</div>

										<div class="smc">
											<div class="items new-items">
												<div class="item-lcol">
													<div class="item">
														<span class="label">收货人：</span>
														<div class="fl">${addr.reciveName }</div>
														<div class="clr"></div>
													</div>
													<div class="item">
														<span class="label">所在地区：</span>
														<div class="fl">${addr.reciveCity }</div>
														<div class="clr"></div>
													</div>
													<div class="item">
														<span class="label">地址：</span>
														<div class="fl">${addr.reciveAddress }</div>
														<div class="clr"></div>
													</div>
													<div class="item">
														<span class="label">手机：</span>
														<div class="fl">${addr.telphone }</div>
														<div class="clr"></div>
													</div>
													<div class="item">
														<span class="label">固定电话：</span>
														<div class="fl">${addr.mobile }</div>
														<div class="clr"></div>
													</div>
													<div class="item">
														<span class="label">电子邮箱：</span>
														<div class="fl">${addr.email }</div>
														<div class="clr"></div>
													</div>
												</div>

												<div class="item-rcol">
													<div class="extra">
														<c:if test="${addr.isdefault==1}">默认地址</c:if>
														<c:if test="${addr.isdefault==0}"><a class="ml10 ftx-05"
															href="javascript:makeAddressAllDefault('${addr.id }');">设为默认</a></c:if>
														<a class="ml10 ftx-05"
															href="javascript:getPayment(137805074,0,0);">使用一键购</a> <a
															class="ml10 ftx-05" href="javascript:;"
															onclick="alertUpdateAddressDiag(${addr.id });">编辑</a>
													</div>
												</div>
												<div class="clr"></div>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>


							<div class="mt" id="addAddressDiv_bottom">
								<a onclick="alertAddAddress()" class="e-btn add-btn btn-5"
									href="javascript:;">新增收货地址</a> <span class="ftx-03">
									您已创建 <span id="addressNum_botton" class="ftx-02">${size }</span>
									个收货地址，最多可创建 <span class="ftx-02">20</span> 个
								</span>
							</div>
						</div>


					</div>
				</div>
				<jsp:include page="commons/left.jsp">
					<jsp:param name="page_index" value="3" />
				</jsp:include>
				<span class="clr"></span>
			</div>
		</div>
	</div>

	<!-- footer start -->
	<jsp:include page="commons/footer.jsp" />
	<!-- footer end -->
</body>
</html>