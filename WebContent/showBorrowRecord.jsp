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
<span style="color:red">${allowBorrowError }</span>
<div class="mycontainer">

		<form action="${pageContext.request.contextPath}/admin?method=updownBook" method="post">
		<input type="hidden" name="userId" value="${user.uid }">
		<table class="table  table-striped">
			<thead>
			<tr>
				<td>借书日期</td>
				<td>还书日期</td>
				<td>书籍/作者</td>
				<td>图书类别</td>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${borrowRecords}" var="borrow">
					<tr>
					<td>${borrow.borrowDate}</td>
					<td align="center">
						<c:if test="${empty borrow.returnDate}">待还</c:if>
						<c:if test="${not empty borrow.returnDate}">${borrow.returnDate }</c:if>
					</td>
					<td><a href="${pageContext.request.contextPath}/book?id=${borrow.book.id}&&method=queryOneBook">${borrow.book.bookName}/${borrow.book.author}</a></td>
					<td>${borrow.book.category.categoryName }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
</div>
</body>
</html>