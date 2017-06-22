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
 
		<input type="hidden" name="userId" value="${user.uid }">
		<table class="table table-condensed">
			<thead>
			<tr>
				<td>借书日期</td>
		<td>还书日期</td>
		<td>书籍/作者</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${myborrowHistory}" var="borrow">
	<tr>
		<td>${borrow.borrowDate }</td>
		<td>${borrow.returnDate}</td>
		<td>${borrow.book.bookName}/${borrow.book.author}</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
	
</div>

</body>
</html>