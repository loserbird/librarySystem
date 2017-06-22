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
		width:400px;
		margin:0 auto;
		margin-top:40px;
	}
</style>
</head>
<body>
<jsp:include page="common.jsp"/>
<div class="mycontainer">
<table class="table table-hover">
<caption>所有书籍类别</caption>
	<thead>
    <tr>
      <th>类别</th>
      <th>可借天数</th>
    </tr>
  </thead>
  <tbody>
	<c:forEach items="${categoryList}" var="category">
	<tr>
		<td>${category.categoryName }</td>
		<td>${category.days }</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</body>
</html>