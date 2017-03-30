<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="base/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title></title>
</head>
<body>

<div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.html"><i class="fa fa-comments"></i> <strong>微信 </strong></a>
            </div>
        </nav>
        <!--/. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li>
                        <a href="/controller/wx/message/${ownerId }/getMsg" target="maintain_info"><i class="fa fa-dashboard"></i> 订阅回复</a>
                    </li>
                    <li>
                        <a href="/controller/wx/menu/${ownerId }/getMenu" target="maintain_info"><i class="fa fa-edit"></i> 自定义菜单</a>
                    </li>
                     <li>
                        <a href="/controller/wx/message/${ownerId }/getTempMsg" target="maintain_info"><i class="fa fa-edit"></i> 发送模板信息</a>
                    </li>
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <iframe id="maintain_info" name="maintain_info" style="width:100%;height:1200px;" frameborder="0"  scrolling="no" ></iframe>
        </div>
     <!-- /. WRAPPER  -->
</body>
</html>