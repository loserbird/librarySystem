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
<div class="mycontainer">
  <form class="form-inline" role="form" action="${pageContext.request.contextPath}/admin?method=queryBook" method="post">
   		
          <div class="form-group">
          <label for="bookName" class="control-label">书名</label>
            <input type="text" class="form-control" name="bookName" id="bookName" placeholder="请输入书名">
        </div>
        <div class="form-group">
          <label for="author" class="control-label">作者</label>
            <input type="text" class="form-control" name="author" id="author" placeholder="请输入作者">
           
        </div>
       
          <div class="form-group">
          <label for="bookName" class="control-label">类别</label>
             <select name="category" id="category" class="form-control">
				<option>全部</option>
			</select>
        </div>
        
        	<div class="form-group">
        	  <input type="submit" class="btn btn-success" value="搜索"/>
        	</div>
        </form>
        
        <p>${noResult }</p>
        
		<div>
		<form action="${pageContext.request.contextPath}/admin?method=updownBook" method="post">
		<table class="table table-condensed">
			<thead>
			<tr>
				<th>书名</th>	
				<th>作者</th>	
				<th>类别</th>	
				<th>描述</th>	
				<th>数量</th>	
				<th>状态</th>	
				<th>上架|下架</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${allBooklist}" var="book">
				<tr>
				<td>${book.bookName}</td>
				<td>${book.author }</td>
				<td>${book.category.categoryName }</td>
				<td>${book.description}</td>
				<td>${book.count }</td>
				<c:if test="${book.isAvailable eq 0 }">
					<td>已下架</td>
					<td align="center">上架<input type="checkbox" name="bookId" value="${book.id}"/></td>
				</c:if>
				<c:if test="${book.isAvailable eq 1 }">
					<td>可供借阅</td>
					<td align="center">下架<input type="checkbox" name="bookId" value="${book.id}"/></td>	
				</c:if>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty allBooklist}">
				<div class="col-sm-offset-11 col-sm-1">
				<input type="submit" value="确定" class="btn btn-success"/>
				</div>
		</c:if>
		</form>
		</div>
</div>

</body>
</html>