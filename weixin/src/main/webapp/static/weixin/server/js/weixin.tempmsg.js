$(function(){
	$(".tempMenuLi").eq(0).find("a").click();
	
	$("#subbtn").click(function(){
		sendTempData();
		alert("正在发送请稍后");
		location.reload(true);
	});
	
});

function sendTempData(){
	$.ajax({
	  	url:"sendTempMsg",
	  	type:"post",
	  	contentType:"application/x-www-form-urlencoded;charset=UTF-8",   //解决传中文乱码
	  	data:{"content":encodeURI(getData())},      //encodeURI(xxx) 编码
		success:function(res){
			//alert();
			alert("发送成功");
		},
		error:function(){
			alert("error");
		}
	});  
}

function getData(){
	var tabdiv=$(".tab-content>.active");
	var template_id=$(tabdiv).attr("id");
	var redUrl=$(tabdiv).find(".redUrl").val();
	var data="";
	var textInputs=$(tabdiv).find(".iscontentP>input:text");
	for(var i=0;i<textInputs.length;i++){
		var textName=textInputs.eq(i).attr("data-dname");
		var textValue=textInputs.eq(i).val();
		if(i!=0){
			data+=",";
		}
		if(i==0||i==1||i==4){
			data+='"'+textName+'":{"value":"'+textValue+'","color":"#c7070d"}';
		}else{
			data+='"'+textName+'":{"value":"'+textValue+'","color":"#173177"}';
		}
	}
	
	var result="{";
	result+='"template_id":"'+template_id+'",';
	result+='"url":"'+redUrl+'",';
	result+='"data":{'+data+'}';
	result+="}";
//	alert(result);
	return result;
}