<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function checkNull(field){	//检查是否为空
	var reg = /^\s*$/;//表示0个或多个空白符
	var value =  document.getElementById(field).value;
	if(reg.test(value)){
		return false;
	}
	return true;
}

function checkForm(){
	var old = checkNull("oldPassword");
	var newp = checkNull("newPassword");
	var re = checkNull("rpassword");
	var flags = old&&newp&&re;
	
	if(flags == false){
		alert("信息不完整");
	}
	
	if($("#newPassword").val() != $("#rpassword").val()){
		alert("两次密码不一致");
		return false;
	}
	return flags;
}
</script>
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
<div style="color:red;font-size:16px;">
<c:forEach items="${updatePasswordError}" var="error">
	<p style="color:red;">${error }</p>
</c:forEach>
</div>
<div class="mycontainer">
  <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user?method=updatePassword" method="post" onsubmit="return checkForm();">
          <div class="form-group">
          <label for="oldPassword" class="col-sm-3 control-label">原密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" name="oldPassword" id="oldPassword">
          </div>
        </div>
        <div class="form-group">
          <label for="newPassword" class="col-sm-3 control-label">新密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" name="newPassword" id="newPassword">
          </div>
        </div>
         <div class="form-group">
          <label for="rpassword" class="col-sm-3 control-label">确认密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" name="rpassword" id="rpassword">
          </div>
        </div>
        
        <div>
        	<div class="form-group">
        	
        	<div class="col-sm-offset-6 col-sm-2">
        	  <input type="submit" class="btn btn-success" value="更改"/>
        	</div>
        	<div class="col-sm-offset-1 col-sm-3">
        	  <input type="reset" class="btn btn-primary" value="重置"/>
        	</div>  
        </div>
		</div>
        </form>
</div>
</body>
</html>