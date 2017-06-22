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
 
		<form action="${pageContext.request.contextPath}/borrow?method=returnBook" method="post">
		<input type="hidden" name="userId" value="${user.uid }">
		<table class="table table-condensed">
			<thead>
			<tr>
			<td>归还</td>
		<td>最迟应还期</td>
		<td>书籍/作者</td>
		<td>借书日期</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${myborrowlist}" var="borrow">
		<tr>
		<td align="center">
			<input type="checkbox" name="borrowId" value="${borrow.id},${borrow.book.id}"/>
		</td>
		<td>${borrow.shouldReturnDate }</td>
		<td>${borrow.book.bookName}/${borrow.book.author}</td>
		<td>${borrow.borrowDate}</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty myborrowlist }">
				<div class="col-sm-offset-11 col-sm-1">
				<input type="submit" value="确定" class="btn btn-success"/>
				</div>
		</c:if>
		</form>
		</div>
</div>

</body>
</html>