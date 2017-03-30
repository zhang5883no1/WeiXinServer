<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
	<link href="${appStaticContent}server/css/menu.style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main">
	<div class="menu">
		<div class="left">
			<div class="mobile_menu">
				<div class="title">通云商</div>
				<ul class="mobile_list">
					<core:forEach items="${menu.button }" var="btns">
						<li class="topMenu">
						
							<a href="javascript:;" class="_topMenuInfo" data-dname="${btns.name }" data-dtype="${btns.type }" data-dinfo="
								<core:if test="${btns.key==''||btns.key==null}">${btns.url}</core:if>
								<core:if test="${btns.url==''||btns.url==null}">${btns.key}</core:if>
							">${btns.name }</a>
							<div class="menu_box">
								<ul>
									<core:forEach  items="${btns.sub_button }" var="subs">
										<li class="_submenuInfo" data-dname="${subs.name }" data-dtype="${subs.type }" data-dinfo="
											<core:if test="${subs.key==''||subs.key==null}">${subs.url}</core:if>
											<core:if test="${subs.url==''||subs.url==null}">${subs.key}</core:if>
										"><a href="javascript:;"><span>${subs.name }</span></a></li>
									</core:forEach>
									<li class="addsubMenu"><a href="javascript:;" title="最多添加5个子菜单"><span><i class="add"></i></span></a></li>
								</ul>
							</div>
						</li>
					</core:forEach>
					<li id="_do_addMenu"><a href="javascript:;"><i class="add"></i></a></li>
				</ul>
			</div>
		</div>
		<div class="right">
			<div class="rightBox">
				<div class="editor_inner">
					<div class="menu_form">
						<h4>菜单名称</h4>
						<div class="extra">
							<a href="javascript:;" id="_delMenuBtn">删除菜单</a>
						</div>
					</div>
					<div class="menu_form_bd">
						<div class="control_group">
							<label>菜单名称</label>
							<div class="frm_controls">
								<span class="frm_input_box"><input type="text" value="菜单名称" id="_menu_name"></span>
								<p style="color:#e15f63;display:none">字数超过上限</p>
								<p style="color:#e15f63;display:none">请输入菜单名称</p>
								<p>字数不超过4个汉字或8个字母</p>
							</div>
						</div>
						<div class="control_group" id="radio_div">
							<label>菜单内容</label>
							<div class="frm_controls frm_tab">
								<label class="selected">
									<i class="icon_radio"></i>
									<span class="lbl_content">发送消息</span>
									<input type="radio" id="textRadio" value="click" name="_selectType">
								</label>
								<label>
									<i class="icon_radio"></i>
									<span class="lbl_content">跳转网页</span>
									<input type="radio" id="urlRadio" value="view" name="_selectType">
								</label>
							</div>
						</div>
						<div class="menu_content_container" id="dinfo_div">
							<div class="menu_content" id="textInputArea">
								<div class="tab_navs">
									<ul class="tab_navs_box">
										<li><a href="javascript:;"><i></i><span>文字</span></a></li>
									</ul>
								</div>
								<textarea  class="tab_content" contenteditable="true" id="saytext"></textarea >
								<div class="editor_toolbar">
									<a href="javascript:;" class="emotion"></a>
									<p>还可以输入600字</p>
								</div>
							</div>
							<div class="menu_content" id="urlInputArea">
								<form class="urlForm">
									<p>订阅者点击该子菜单会跳到以下链接</p>
									<div class="control_group url_group">
										<label>页面地址</label>
										<div class="frm_controls">
											<span class="frm_input_box"><input type="text" id="sayurl"></span>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<input id="doSubMenu" value="提交" style="float: right; height: 33px; width: 115px; border: 1px solid gray;" type="button">
			</div>
		</div>
	</div>
</div>

<div class="_hide_clone_top_menu" style="display:none;">
	<li class="topMenu">
		<a href="javascript:;" class="_topMenuInfo" data-dname="菜单名称" data-dtype="click" data-dinfo=" ">菜单名称</a>
		<div class="menu_box">
			<ul>
				<li class="addsubMenu"><a href="javascript:;" title="最多添加5个子菜单"><span><i class="add"></i></span></a></li>
			</ul>
		</div>
	</li>
</div>
<div class="_hide_clone_sub_menu" style="display:none;">
	<li class="_submenuInfo" data-dname="子菜单名称" data-dtype="click" data-dinfo=" "><a href="javascript:;"><span>子菜单名称</span></a></li>
</div>

<script type="text/javascript" src="${appStaticContent}server/js/jquery.min.js"></script> 
<script type="text/javascript" src="${appStaticContent}server/js/jquery.qqFace.js"></script>
<script type="text/javascript" src="${appStaticContent}server/js/weixin.menu.js"></script>
</body>
</html>
