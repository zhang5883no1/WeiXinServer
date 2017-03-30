<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模板信息</title>
     <!-- Google Fonts-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
   <div id="page-inner">
            <div class="row">
                <div class="col-md-6 col-sm-6" style="width:100%">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	模板信息
                        </div>
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                            	<core:forEach items="${msg}" var="mslist">
                            		<li class="tempMenuLi"><a href="#${mslist.template_id}" data-toggle="tab">${mslist.title}</a></li>
                            	</core:forEach>
                            </ul>
                            <div class="tab-content">
                            	<core:forEach items="${msg}" var="mslist">
                            		
                            		<div class="tab-pane fade" id="${mslist.template_id}">
                            			<div style="float:left;">
		                                    <h4>${mslist.title}</h4>
		                                    <p class="iscontentP">${mslist.content }</p>
		                                    <p>跳转链接：<input type="text" value="" class="redUrl"/></p>
	                                    </div>
	                                    <div style="float:left;margin-left:100px;">
		                                    <h4>例如:</h4>
		                                    <p>${mslist.example }</p>
	                                    </div>
	                                </div>
                            	</core:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <input value="提交" type="button" id="subbtn"/>
	</div>
	<script src="${appStaticContent}server/js/weixin.tempmsg.js"></script>
</body>
</html>

  {
           "touser":"OPENID",
           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
           "url":"http://weixin.qq.com/download",            
           "data":{
                   "first": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
                   "keynote1":{
                       "value":"巧克力",
                       "color":"#173177"
                   },
                   "keynote2": {
                       "value":"39.8元",
                       "color":"#173177"
                   },
                   "keynote3": {
                       "value":"2014年9月22日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"欢迎再次购买！",
                       "color":"#173177"
                   }
           }
       }