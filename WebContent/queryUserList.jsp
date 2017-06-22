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
 
		<form action="${pageContext.request.contextPath}/admin?method=allowRegist" method="post">
		<table class="table table-condensed">
			<thead>
			<tr>
			<td>学号</td>
			<td>姓名</td>
			<td>学院</td>
			<td>专业</td>
			<td>年级</td>
			<td>邮箱</td>
			<td>状态</td>
			<td>允许注册</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${userlist}" var="user">
		<tr>
		<td>${user.uid }</td>
		<td>${user.userName }</td>
		<td>${user.department }</td>
		<td>${user.major}</td>
		<td>${user.grade }</td>
		<td>${user.email }</td>
		<td>
			<c:if test="${user.state eq 0 }">
				待审核
			</c:if>
			<c:if test="${user.state eq 1 }">
				已通过
			</c:if>
		</td>
		<td align="center">
			<input type="checkbox" name="uid" value="${user.uid}"/>
		</td>
	</tr>
	</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty userlist}">
				<div class="col-sm-offset-11 col-sm-1">
				<input type="submit" value="确定" class="btn btn-success"/>
				</div>
		</c:if>
		</form>
		</div>
</div>

</body>
</html>