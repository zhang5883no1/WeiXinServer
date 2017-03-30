$(function(){
	$('.emotion').qqFace({
		id : 'facebox', 
		assign:'saytext', 
		path:'arclist/'
	});
	$(".menu_content:not(:first)").hide();
	$(".frm_tab label").click(function(){
		$(this).addClass("selected").siblings("label").removeClass("selected");
		var index = $(this).index();
		$(".menu_content").eq(index).show().siblings(".menu_content").hide();
	})
	
	//添加一级菜单
	$("#_do_addMenu").click(function(){
		var menus = $(".mobile_list>li");
		if(menus.length==4){
			return ;
		}
		if(menus.length==3){
			$(this).hide();
		}
		$("._hide_clone_top_menu>li").clone(true).insertBefore("#_do_addMenu");
		$(".mobile_list>li").width(100/$(".mobile_list>li:visible").length+"%");
		$(this).prev().click();
	});
	
	//添加二级菜单
	$(".addsubMenu").click(function(){
		var subMenus=$(this).siblings();
		if(subMenus.length==5){
			return ;
		}
		if(subMenus.length==4){
			$(this).hide();
		}
		$("._hide_clone_sub_menu>li").clone(true).insertBefore(this);
		$(this).prev().click();
	});
	
	//点击一级菜单
	$(".topMenu").click(function(){
		$(".topMenu").removeClass("current");
		$(this).addClass("current");
		$(".mobile_list>li>.menu_box").hide();
		$(this).find(".menu_box").show();
	});
	
	//切换一级菜单
	$("._topMenuInfo").click(function(){
		saveCurrentInfo();
		$("._isselect").removeClass("_isselect");
		$(this).addClass("_isselect");
		setInfo(this);
		if($(this).siblings().find("ul>li").length>1){
			$("#radio_div").hide();
			$("#dinfo_div").hide();
		}
	});
	
	//切换二级菜单
	$("._submenuInfo").click(function(){
		saveCurrentInfo();
		$("._isselect").removeClass("_isselect");
		$(this).addClass("_isselect");
		setInfo(this);
	});
	
	//删除菜单
	$("#_delMenuBtn").click(function(){
		if($("._isselect").hasClass("_topMenuInfo")){
			$("._isselect").parent().remove();
			$(".topMenu:eq(0)").click();
			$("#_do_addMenu").show();
			$(".mobile_list>li").width(100/$(".mobile_list>li:visible").length+"%");
		}else if($("._isselect").hasClass("_submenuInfo")){
			var parMenu=$("._isselect").parent();
			$("._isselect").remove();
			parMenu.find(".addsubMenu").show();
			if($(parMenu).find("li").length>1){
				$(parMenu).find("li:eq(0)").click();
			}else{
				$(".current>._topMenuInfo").click();
			}
		}
	});
	
	//提交菜单
	$("#doSubMenu").click(function(){
		var json='{"button":[';
		var topMenus=$(".mobile_list>.topMenu");
		for(var ti=0;ti<topMenus.length;ti++){
			if(ti!=0){
				json+=',';
			}
			var tmName=topMenus.eq(ti).find("._topMenuInfo").attr("data-dname");
			var tmtype=topMenus.eq(ti).find("._topMenuInfo").attr("data-dtype");
			var tminfo=topMenus.eq(ti).find("._topMenuInfo").attr("data-dinfo");
			json+='{"name":"'+tmName+'"';
			var subMenus=topMenus.eq(ti).find("._submenuInfo");
			if(subMenus.length>0){
				json+=',"sub_button":[';
				for(var si=0;si<subMenus.length;si++){
					if(si>0){
						json+=",";
					}
					var smName=subMenus.eq(si).attr("data-dname");
					var smType=subMenus.eq(si).attr("data-dtype");
					var smInfo=subMenus.eq(si).attr("data-dinfo");
					json+='{"name":"'+smName+'","type":"'+smType+'"';
					if(smType=="click"){
						json+=',"key":"'+$.trim(smInfo)+'"}';
					}else if(smType=="view"){
						json+=',"url":"'+$.trim(smInfo)+'"}';
					}
				}
				json+=']';
			}else{
				json+=',"type":"'+tmtype+'"';
				if(tmtype=="click"){
					json+=',"key":"'+$.trim(tminfo)+'"';
				}else if(tmtype=="view"){
					json+=',"url":"'+$.trim(tminfo)+'"';
				}
			}
			json+='}';
		}
		json+=']}';
		alert(json);
		$.ajax({
		  	url:"setMenu",
		  	type:"post",
		  	dataType:"json",
		  	contentType:"application/x-www-form-urlencoded;charset=UTF-8",   //解决传中文乱码
		  	data:{"content":encodeURI(json)},      //encodeURI(xxx) 编码
			success:function(res){
				//alert();
				if(res==true){
					alert("设置成功");
				}else {
					alert("设置失败，请检查菜单内容");
				}
			},
			error:function(){
				alert("error");
			}
		});  
	});
	var menus = $(".mobile_list>li");
	if(menus.length==4){
		$("#_do_addMenu").hide();
	}
	$(".mobile_list>li").width(100/$(".mobile_list>li:visible").length+"%");
	$(menus).eq(0).find("._topMenuInfo").click();
});

//把对象属性赋值到显示页面
function setInfo(btn){
	var name=$(btn).attr("data-dname");
	var type=$(btn).attr("data-dtype");
	var info=$.trim($(btn).attr("data-dinfo"));
	$("#_menu_name").val(name);
	if(type=="click"){
		$("#textInputArea").show();
		$("#urlInputArea").hide();
		$("#textRadio").click();
		$("#saytext").val(info);
		$("#sayurl").val("");
	}else if(type=="view"){
		$("#urlInputArea").show();
		$("#textInputArea").hide();
		$("#urlRadio").click();
		$("#sayurl").val($.trim(info));
		$("#saytext").val("");
	}
	$("#radio_div").show();
	$("#dinfo_div").show();
}

//保存属性到对象
function saveCurrentInfo(){
	var name=$("#_menu_name").val();
	var type=$(":radio:checked").val();
	var info="";
	if(type=="click"){
		info=$("#saytext").val();
	}else if(type=="view"){
		info=$("#sayurl").val();
	}
	$("._isselect").attr("data-dname",name);
	$("._isselect").attr("data-dtype",type);
	$("._isselect").attr("data-dinfo",info);
	if($("._isselect").hasClass("_topMenuInfo")){
		$("._isselect").html(name);
	}else if($("._isselect").hasClass("_submenuInfo")){
		$("._isselect>a>span").html(name);
	}
}
