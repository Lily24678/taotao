<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的信息 - 淘淘</title>
<meta name="Keywords" content="java,淘淘java" />
<meta name="description" content="在淘淘中找到了29910件java的类似商品，其中包含了“图书”，“电子书”，“教育音像”，“骑行运动”等类型的java的商品。" />
<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.common.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.info.css" media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
<script type="text/javascript" src="/js/base-2011.js" charset="utf-8"></script>
<!-- header start -->
<jsp:include page="commons/header.jsp" />
<!-- header end -->

<div id="container">
<div class="w">
	
<div id="main">
    <div class="g-0">
        <div id="content" class="c-3-5">
            <div class="mod-main">
						<div class="mt">
							<ul class="extra-l">
                                <li class="fore-1"><a href="/my-info.html">基本信息</a></li>
                                <li class="fore-2"><a class="curr">头像照片</a></li>
                                <li class="fore-3"><a href="/my-info-more.html">更多个人信息</a></li>
                            </ul>
						</div>
						<div class="mc update-face-cont">
							<div class="update-lcol">
								<div class="mb10">
									<object id="SWFUpload_0" type="application/x-shockwave-flash" data="/images/swfupload.swf?preventswfcaching=1419921680918" width="202" height="34" class="swfupload"><param name="wmode" value="window"><param name="movie" value="http://i.jd.com/commons/swfupload.swf?preventswfcaching=1419921680918"><param name="quality" value="high"><param name="menu" value="false"><param name="allowScriptAccess" value="always"><param name="flashvars" value="movieName=SWFUpload_0&amp;uploadURL=http%3A%2F%2Fi.jd.com%2Fuser%2Fupload%2Fimage.action&amp;useQueryString=false&amp;requeueOnError=false&amp;httpSuccess=&amp;assumeSuccessTimeout=0&amp;params=flashuploadimg%3D7CBF7B70DBDBBBF7B6BF2E4979C16E3DDBCA1CA3FD4B7B76AFB5AC2FF83DB5FD1828AC00799C2184C2642652C4D4686AF1FE36E49D3477A3FF34E056B55731C8D1309AD7D3C918887F2D5A6A5B1CD053F254F6D7B09273591B9C43BA085F5CA0881A9EE6F629AEB44D051A9288A126A71FC1D3F53A64DC49E71359C1D776480A&amp;filePostName=file&amp;fileTypes=*.jpg%3B*.gif%3B*.png%3B*.jpeg%3B*.bmp&amp;fileTypesDescription=img&amp;fileSizeLimit=4%20MB&amp;fileUploadLimit=0&amp;fileQueueLimit=0&amp;debugEnabled=false&amp;buttonImageURL=/images/perfect_bg.jpg&amp;buttonWidth=202&amp;buttonHeight=34&amp;buttonText=&amp;buttonTextTopPadding=0&amp;buttonTextLeftPadding=0&amp;buttonTextStyle=color%3A%20%23000000%3B%20font-size%3A%2016pt%3B&amp;buttonAction=-100&amp;buttonDisabled=false&amp;buttonCursor=-1"></object>
									<img id="loading" class="float:left" src="/images/loading.gif" style="display:none">
									<div id="messages" style="border:1px solid #DB9A9A;background-color:#FFE8E8;color:#CC0000;text-align: left;display:none"></div>
									<input id="btnCancel" type="hidden">
									<div class="ftx03">仅支持JPG、GIF、PNG、JPEG、BMP格式，文件小于4M</div>
								</div>
								<div class="img-b-cont img-cont ">
									<!--<div class="tip">编辑预览区</div>-->
									<div class="img-b">
										<img id="bigImage" name="bigImage" alt="" width="150" height="150" src="/images/defaultImgs/1.jpg">
									</div>
								</div>
								<div class="smt"><h3>推荐头像</h3></div>
								<div class="smc face-list">
									<ul class="imgUl">
										<li value="1"><img src="/images/defaultImgs/1.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="2"><img src="/images/defaultImgs/2.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="3"><img src="/images/defaultImgs/3.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="4"><img src="/images/defaultImgs/4.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="5"><img src="/images/defaultImgs/5.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="6"><img src="/images/defaultImgs/6.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="7"><img src="/images/defaultImgs/7.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="8"><img src="/images/defaultImgs/8.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="9"><img src="/images/defaultImgs/9.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="10"><img src="/images/defaultImgs/10.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="11"><img src="/images/defaultImgs/11.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="12"><img src="/images/defaultImgs/12.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="13"><img src="/images/defaultImgs/13.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="14"><img src="/images/defaultImgs/14.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="15"><img src="/images/defaultImgs/15.jpg" alt="" width="50" height="50"><b></b></li>
										<li value="16"><img src="/images/defaultImgs/16.jpg" alt="" width="50" height="50"><b></b></li>
									</ul>
									<div class="btns mt20">
									   <a href="javascript:void(0)" class="btn-5 mr10" onclick="uploadDefaultImg()">保存</a>
									</div>
								</div>
							</div>
							<div class="update-rcol">
								<div class="smt"><h3>效果预览</h3></div>
								<div class="smc">
									你上传的图片会自动生成2种尺寸，请注意小尺寸的头像是否清晰
									<div class="img-m-cont img-cont">
										<div class="img-s">
									    <img id="midImage" width="100" height="100" name="midImage" src="/images/defaultImgs/1.jpg">
                                        </div>
									</div>
									100*100像素
									<div class="img-s-cont img-cont">
										<div class="img-s">
										<img id="smaImage" width="50" height="50" name="smaImage" src="/images/defaultImgs/1.jpg">
                                        </div>
									</div>
									50*50像素
								</div>
							</div>
							<div class="clr"></div>
						</div>
					</div>
        </div>
    </div>
	<jsp:include page="commons/left.jsp">
    	<jsp:param name="page_index" value="4"/>
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