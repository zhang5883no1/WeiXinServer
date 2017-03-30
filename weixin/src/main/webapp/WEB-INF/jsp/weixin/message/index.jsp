<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订阅回复</title>

  <!-- TABLE STYLES-->
<link href="${appStaticContent}assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
   
   <style type="text/css">  
        div{  
            width:100%;  
        }  
    </style>  
    
</head>
<body>
	<div id="page-inner">
		 <div class="row">
                   <div class="col-md-12">
                       <h1 class="page-header">
                          		订阅回复
                       </h1>
                   </div>
            </div> 
                <!-- /. ROW  -->
             <div class="row">
               <div class="col-lg-12">
                   <div class="panel panel-default">
                       <div class="panel-heading">
                          
                       </div>
                       <div class="panel-body">
                           <div class="row">
                               <div class="col-lg-6">
                                     <div class="form-group">
                                         <label>消息内容</label>
                                         <div>
								   			<textarea rows="3" class="form-control" id="dtoContent">${msg}</textarea>
										</div>
                                     </div>
                                    <input class="btn btn-default" id="subbtn" value="提交" type="button"/>
                               </div>
                           </div>
                           <!-- /.row (nested) -->
                       </div>
                       <!-- /.panel-body -->
                   </div>
                   <!-- /.panel -->
               </div>
               <!-- /.col-lg-12 -->
           </div>
	</div>
	
	<script src="${appStaticContent}server/js/weixin.message.js"></script>
</body>
</html>