<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/area.js"></script>
<script type="text/javascript">
var Gid  = document.getElementById ;
var showArea = function(){
	Gid('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" + 	
	Gid('s_city').value + " - 县/区" + 
	Gid('s_county').value + "</h3>"
							}
Gid('s_county').setAttribute('onchange','showArea()');

function addAddress(){
	 $.ajax({
        type: "POST",
        url:"/myhome/addAddress.action",
        data:$('#addrFrom').serialize(),
        success: function (data) {
        	 if(data.status == 200){
        		 alert("新地址添加成功！！");
	             top.$.colorbox.close();
        	} 
        }
    }); 
	}
</script>
				<link type="text/css" rel="stylesheet"
					href="//misc.360buyimg.com/jdf/1.0.0/unit/??ui-base/1.0.0/ui-base.css,shortcut/2.0.0/shortcut.css,global-header/1.0.0/global-header.css,myjd/2.0.0/myjd.css,nav/2.0.0/nav.css,shoppingcart/2.0.0/shoppingcart.css,global-footer/1.0.0/global-footer.css,service/1.0.0/service.css,basePatch/1.0.0/basePatch.css">
				<link type="text/css" rel="stylesheet"
					href="//misc.360buyimg.com/user/myjd-2015/widget/??common/common.css"
					source="widget">
				<link type="text/css" rel="stylesheet"
					href="//misc.360buyimg.com/user/myjd-2015/css/myjd.easebuy.css"
					source="combo"> 
				<title>收货地址</title>

                <form id="addrFrom" >
				<div class="m" id="edit-cont">
					<div class="mc">
						<div class="form">
							<div class="item">
								<span class="label"><em>*</em>收货人：</span>
								<div class="fl">
									<input id="consigneeName" type="text" class="text"
										onblur="checkConsigneeName()" name="reciveName"> <span
										id="consigneeNameNote" class="error-msg hide"></span>
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label"><em>*</em>所在地区：</span>
								<div class="info">
									<div>
										<select id="s_province" name="reciveProvince"></select>&nbsp;&nbsp;
										<select id="s_city" name="reciveCity"></select>&nbsp;&nbsp; <select
											id="s_county" name="reciveArea"></select>
										<script class="resources library" src="js/area.js"
											type="text/javascript"></script>

										<script type="text/javascript">
											_init_area();
										</script>
									</div>
									<div id="show"></div>
								</div>

								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label"><em>*</em>详细地址：</span>
								<div class="fl">
									<span
										style="float: left; margin-right: 5px; line-height: 32px;"
										id="areaName" ></span> <input id="consigneeAddress" type="text"
										class="text text1" onblur="checkConsigneeAddress()" name="reciveAddress">
								</div>
								<span class="error-msg" id="consigneeAddressNote"></span>
								<div class="clr"></div>
							</div>
							<div class="item">
								<div class="fl">
									<span class="label"><em>*</em>手机号码：</span> <input
										id="consigneeMobile" type="text" maxlength="11" class="text"
										onblur="checkMobile()" name="telphone">
								</div>
								<div class="fl">
									<span class="extra-span ftx-03">或</span>
								</div>
								<div class="fl">
									<span class="label">固定电话：</span> <input id="consigneePhone"
										type="text" class="text" onblur="checkMobile()"  name="mobile"> <span
										class="error-msg hide" id="consigneeMobileNote"></span> <span
										class="clr"></span>
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label">邮箱：</span>
								<div class="fl">
									<input id="consigneeEmail" type="text" class="text text1"
										maxlength="50" onblur="checkEmail()" name="email"> <span
										class="error-msg hide" id="emailNote"></span>
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label">地址别名：</span>
								<div class="fl">
									<input id="consigneeAlias" type="text" class="text"
										onblur="checkConsigneeAlias()" name="addrName"> <span class="ftx-03">建议填写常用名称</span>
									<span class="addr-alias"> <a
										href="javascript:setEditAddressAilas('home');" id="home">家里</a>
										<a href="javascript:setEditAddressAilas('parentHome');"
										id="parentHome">父母家</a> <a
										href="javascript:setEditAddressAilas('company');" id="company">公司</a>
									</span> 
								</div>
								<div class="clr"></div>
							</div>
							<div class="btns">
								<a href="javascript:;" onclick="addAddress();"
									class="e-btn btn-9 save-btn">保存收货地址</a>
							</div>
						</div>
					</div>
				</div>
				</form>