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
<span style="color:red">${applyExistError}</span>
<div class="mycontainer">

		 <form class="form-horizontal" role="form" >
		 <div class="form-group">
		  <label for="firstname" class="col-sm-3 control-label">图片</label>
		 	 <div class="col-sm-offset-3 col-sm-9">
		 	 <img alt="图片" width="55px" height="55px" src="${pageContext.request.contextPath}${book.imgurl }"/>
		 	 </div>
		 </div>
		
          <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">书名</label>
          <div class="col-sm-9">
            <label class="control-label">${book.bookName}</label>
          </div>
        </div>
         <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">作者</label>
          <div class="col-sm-9">
            <label class="control-label">${book.author }</label>
          </div>
        </div>
         <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">类别</label>
          <div class="col-sm-9">
            <label class="control-label">${book.category.categoryName }</label>
          </div>
        </div>
         <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">描述</label>
          <div class="col-sm-9">
            <label class="control-label">${book.description}</label>
          </div>
        </div>
         <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">数量</label>
          <div class="col-sm-9">
            <label class="control-label">${book.count }</label>
          </div>
        </div>
         <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">状态</label>
          <div class="col-sm-9">
            <label class="control-label">
            <c:if test="${book.isAvailable eq 0 }">
				已下架
			</c:if>
			<c:if test="${book.isAvailable eq 1 }">
				<c:if test="${book.count eq 0 }">
					需还
				</c:if>
				<c:if test="${book.count gt 0 }">
					可供借阅
				</c:if>
			</c:if>
            
            </label>
          </div>
        </div>
        
         <div class="form-group">
          <div class="col-sm-offset-2 col-sm-2">
		       <a href="collect?method=add&&bookId=${book.id }" class="btn btn-info" role="button">收藏</a>
		       </div>
		       <div class="col-sm-2">
		    <c:if test="${book.count gt 0 }">
		       <a href="borrow?method=applyBorrow&&userId=${user.uid }&&bookId=${book.id }" class="btn btn-success" role="button">申请借阅</a>
			</c:if>
			</div>
		    <c:if test="${user.role=='admin'}">
		       <a href="admin?method=preupdate&&bookId=${book.id }" class="btn btn-primary" role="button">修改</a>
			</c:if>
			</div>
		  </div>
        
        
      
        </form>
</div>
</body>
</html>