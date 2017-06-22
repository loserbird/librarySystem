<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="com.bingqin.po.Pager" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload = function(){
var page = document.getElementById("page");
page.onchange = function(){
	var pagenum = page.value;
	location.href="${pageContext.request.contextPath}/admin?method=queryBorrowHistory&pageNum="+pagenum;
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
 
		<table class="table table-condensed">
			<thead>
			<tr>
			<td>学号</td>
		<td>姓名</td>
		<td>借书日期</td>
		<td>还书日期</td>
		<td>书籍/作者</td>
		<td>图书类别</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pager.dataList}" var="borrow">
	<tr>
		<td>${borrow.user.uid}</td>
		<td>${borrow.user.userName }</td>
		<td>${borrow.borrowDate}</td>
		<td align="center">
			<c:if test="${empty borrow.returnDate}">待还</c:if>
			<c:if test="${not empty borrow.returnDate}">${borrow.returnDate }</c:if>
		</td>
		<td>${borrow.book.bookName}/${borrow.book.author}</td>
		<td>${borrow.book.category.categoryName }</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
		
	<ul class="pagination">
    <li><a href="admin?method=queryBorrowHistory&pageNum=${pager.currentPage-1}">上一页</a></li>
    <li><a href="admin?method=queryBorrowHistory&pageNum=1">首页</a></li>
    <li><a href="admin?method=queryBorrowHistory&pageNum=${pager.totalPage }">尾页</a></li>
    <li><a href="admin?method=queryBorrowHistory&pageNum=${pager.currentPage+1}">下一页</a></li>
</ul>
<div>
第${pager.currentPage}页/共${pager.totalPage }页&nbsp;&nbsp;跳到第<select id="page">
		<%Pager pager = (Pager)request.getAttribute("pager");
			int totalPage = pager.getTotalPage();
			for(int i=1;i<=totalPage;i++){
		%>
		<option value="<%=i%>"><%=i%></option>
		<%} %>
			</select>
		页
</div>
</div>

</body>
</html>