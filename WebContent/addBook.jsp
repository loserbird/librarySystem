<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.bingqin.po.User" %>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%
	User user = (User)session.getAttribute("user"); //权限校验
	if(user != null&&user.getRole().equals("student")){
		response.sendRedirect("middle.jsp");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload = function() {
	var xmlHttp =  new XMLHttpRequest();	
	xmlHttp.open("GET", "${pageContext.request.contextPath}/category?method=getAllCategory", true);
	xmlHttp.send(null);
	xmlHttp.onreadystatechange = function() {
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var text = xmlHttp.responseText;
			var arr = text.split(",");
			var category = document.getElementById("category");
			for(var i = 0; i < arr.length; i++) {
				var op = document.createElement("option");
				op.value = arr[i];
				var textNode = document.createTextNode(arr[i]);
				op.appendChild(textNode);
				category.appendChild(op);
			}
		}
	};
};
</script>
<style type="text/css">
	.mycontainer{
		width:400px;
		margin:0 auto;
		margin-top:40px;
	}
</style>
</head>
<body>
<jsp:include page="common.jsp"/>
<span style="color:red;font-size:16px;">${categoryError}</span>
<div class="mycontainer">
  <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/addBook" method="post" enctype="multipart/form-data">
          <div class="form-group">
          <label for="bookName" class="col-sm-3 control-label">书名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="bookName" id="bookName">
          </div>
        </div>
        <div class="form-group">
          <label for="author" class="col-sm-3 control-label">作者</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="author" id="author">
          </div>
        </div>
         <div class="form-group">
          <label for="category" class="col-sm-3 control-label">类别</label>
          <div class="col-sm-9">
          <select  class="form-control" name="category" id="category">
				<option>请选择类别</option>
			</select>
          </div>
        </div>
         <div class="form-group">
          <label for="author" class="col-sm-3 control-label">数量</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="count" id="count">
          </div>
        </div>
         <div class="form-group">
          <label for="author" class="col-sm-3 control-label">图片</label>
          <div class="col-sm-9">
            <input type="file" name="file">
          </div>
        </div>
         <div class="form-group">
          <label for="author" class="col-sm-3 control-label">描述</label>
          <div class="col-sm-9">
            <textarea name="description" rows="10" cols="40" class="form-control"></textarea>
          </div>
        </div>
        <div>
        	<div class="form-group">
        	
        	<div class="col-sm-offset-6 col-sm-2">
        	  <input type="submit" class="btn btn-success" value="添加书籍"/>
        	</div>
        	<div class="col-sm-offset-1 col-sm-3">
        	  <input type="reset" class="btn btn-primary" value="取消"/>
        	</div>  
        </div>
		</div>
        </form>
</div>
</body>
</html>