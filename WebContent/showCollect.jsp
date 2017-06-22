<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<span style="color:red;">${addCollectError}</span>
<div class="mycontainer">

	<form action="${pageContext.request.contextPath}/collect?method=remove" method="post">
		<table class="table  table-striped">
			<thead>
			<tr>
				<th>书名</th>	
				<th>作者</th>	
				<th>类别</th>	
				<th>描述</th>	
				<th>状态</th>	
				<th>取消收藏</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${collect}" var="book">
		<tr>
		<td>${book.bookName}</td>
		<td>${book.author }</td>
		<td>${book.category.categoryName }</td>
		<td>${book.description}</td>
		<td>
			<c:if test="${book.isAvailable eq 0 }">
				已下架
			</c:if>
			<c:if test="${book.isAvailable eq 1 }">
				可供借阅
			</c:if>
		</td>
		<td align="center">
			<input type="checkbox" name="bookId" value="${book.id }"/>
		</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
			<c:if test="${not empty collect }">
				 <div class="col-sm-offset-6  col-sm-3">
     				 <input type="submit" class="btn btn-success" value="确定">
   				 </div>
			</c:if>
		</form>	
      
</div>
</body>
</html>