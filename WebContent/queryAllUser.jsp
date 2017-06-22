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
  <form class="form-inline" role="form" action="${pageContext.request.contextPath}/admin?method=queryUser" method="post">
   		
          <div class="form-group">
          <label for="bookName" class="control-label">学号</label>
            <input type="text" class="form-control" name="bookName" id="bookName" placeholder="请输入学号">
        </div>
        <div class="form-group">
          <label for="author" class="control-label">姓名</label>
            <input type="text" class="form-control" name="author" id="author" placeholder="请输入姓名">
           
        </div>
       
          <div class="form-group">
          <label for="bookName" class="control-label">年级</label>
		       <select name="grade" id="grade" class="form-control">
				<option value="请选择">请选择</option>
				<option value="大一">大一</option>
				<option value="大二">大二</option>
				<option value="大三">大三</option>
				<option value="大四">大四</option>
			</select>
        </div>
        
        	<div class="form-group">
        	  <input type="submit" class="btn btn-success" value="搜索"/>
        	</div>
        </form>
        
        
		<div>
		<form action="${pageContext.request.contextPath}/admin?method=updownBook" method="post">
		<table class="table table-condensed">
			<thead>
			<tr>
				<td>学号</td>
				<td>姓名</td>
				<td>学院</td>
				<td>专业</td>
				<td>年级</td>
				<td>邮箱</td>
				<td>借书记录</td>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${allUserList}" var="user">
					<tr>
					<td>${user.uid }</td>
					<td>${user.userName }</td>
					<td>${user.department }</td>
					<td>${user.major}</td>
					<td>${user.grade }</td>
					<td>${user.email }</td>
					<td align="center">
						<a href="admin?method=queryBorrowRecord&&userId=${user.uid }">查看</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		</div>
</div>
</body>
</html>