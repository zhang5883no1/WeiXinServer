$(function(){
	$("#subbtn").click(function(){
		$.ajax({
		  	url:"setMsg",
		  	type:"post",
		  	contentType:"application/x-www-form-urlencoded;charset=UTF-8",   //解决传中文乱码
		  	data:{"content":encodeURI($("#dtoContent").val())},      //encodeURI(xxx) 编码
			success:function(res){
				//alert();
				alert("设置成功");
			},
			error:function(){
				alert("error");
			}
		});  
	});
});