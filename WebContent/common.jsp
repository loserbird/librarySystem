<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">  
   <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
   <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <style type="text/css">
  .library-img{
    margin: 0;
    padding: 0;
    width: 100%;
    height: 200px;
    background-image: url('${pageContext.request.contextPath}/image/library2.jpg');  
    background-repeat: no-repeat;  
    background-size: 100% 100%;  
  }
   
  </style>
</head>
<body>
<nav class="navbar navbar-inverse  navbar-static-top" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">首页</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/userInfo.jsp">个人资料</a></li>
            <li><a href="${pageContext.request.contextPath}/updatePassword.jsp">修改密码</a></li>
            <c:if test="${user.role == 'admin' }">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    图书管理 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/addBook.jsp">上架书籍</a></li>
                    <li><a href="${pageContext.request.contextPath}/addCategory.jsp">添加分类</a></li>
                    <li><a href="${pageContext.request.contextPath}/category?method=queryCategory">查看分类</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin?method=queryAllBook">查找书籍</a></li>
                </ul>
            </li>
             <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    用户管理 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/admin?method=queryAllUser">查找用户</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin?method=queryNotRegistUser">审核注册用户</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin?method=queryBorrowHistory">查看借书记录</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin?method=queryApplyList">审核借书申请</a></li>
                </ul>
            </li>
             </c:if>
             
               <c:if test="${user.role == 'student'}">
         		 <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    业务办理 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/borrow?method=queryMyBorrow&&userId=${user.uid}">我的借阅</a></li>
                    <li><a href="${pageContext.request.contextPath}/borrow?method=queryMyBorrowHistory&&userId=${user.uid}">我的借书历史</a></li>
                    <li><a href="${pageContext.request.contextPath}/showCollect.jsp" target="middle">我的收藏夹</a></li>
                    <li><a href="${pageContext.request.contextPath}/book?method=queryAllBook" target="middle">查看所有书籍</a></li>
                </ul>
            </li>
        	 </c:if>
        </ul>
        
       
         <ul class="nav navbar-nav navbar-right"> 
            <li>
            <a href="${pageContext.request.contextPath}/regist.jsp" target="_parent"">
            <span class="glyphicon glyphicon-user"></span> 注册 </a></li> 
            <li>
            <a href="${pageContext.request.contextPath}/user?method=logout" target="_parent">
            <span class="glyphicon glyphicon-log-out"></span> 注销</a></li> 
            <li><a href="${pageContext.request.contextPath}/login.jsp" target="_parent"">
            <span class="glyphicon glyphicon-log-in"></span> 退出登录</a></li> 
        </ul> 
    </div>
    </div>
</nav>
<div class="library-img">

</div>



</body>
</html>
</body>
</html>