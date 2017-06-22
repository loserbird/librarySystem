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
<div class="mycontainer">
 
		<form action="${pageContext.request.contextPath}/admin?method=allowBorrow" method="post">
		<table class="table table-condensed">
			<thead>
			<tr>
			<td>学号</td>
		<td>姓名</td>
		<td>书籍/作者</td>
		<td>申请日期</td>
		<td>允许借阅</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${applylist}" var="apply">
		<tr>
		<td>${apply.user.uid }</td>
		<td>${apply.user.userName }</td>
		<td>${apply.book.bookName}/${apply.book.author}</td>
		<td>${apply.applyDate}</td>
		<td align="center">
			<input type="checkbox" name="applyId" value="${apply.id},${apply.book.category.days}"/>
		</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty applylist }">
				<div class="col-sm-offset-11 col-sm-1">
				<input type="submit" value="确定" class="btn btn-success"/>
				</div>
		</c:if>
		</form>
		</div>
</div>

</body>
</html>