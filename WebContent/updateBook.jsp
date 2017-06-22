<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	var category = document.getElementById("category");
	var childs = category.childNodes;
};
</script>
<style type="text/css">
	.mycontainer{
		width:700px;
		margin:0 auto;
		margin-top:40px;
	}
</style>
</head>
<body>
<jsp:include page="common.jsp"/>
<span style="color:red;">${countError }</span>
<c:forEach items="${updateError}" var="error">
	<p style="color:red;">${error }</p>
</c:forEach>


<div class="mycontainer">
  <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/admin?method=update" method="post">
  	<input type="hidden" name="bookId" value="${book.id}">
          <div class="form-group">
          <label for="bookName" class="col-sm-3 control-label">书名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="bookName" id="bookName" value="${book.bookName}">
          </div>
        </div>
        <div class="form-group">
          <label for="author" class="col-sm-3 control-label">作者</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="author" id="author" value="${book.author}">
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
            <input type="text" class="form-control" name="count" id="count" value="${book.count}">
          </div>
        </div>
        
         <div class="form-group">
          <label for="description" class="col-sm-3 control-label" >描述</label>
          <div class="col-sm-9">
             <input type="text" class="form-control" name="description" id="description" value="${book.description}">
          </div>
        </div>
        <div>
        	<div class="form-group">
        	
        	<div class="col-sm-offset-11 col-sm-1">
        	  <input type="submit" class="btn btn-success" value="修改"/>
        	</div>
        </div>
		</div>
        </form>
</div>
</body>
</html>