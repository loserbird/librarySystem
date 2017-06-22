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
    <script type="text/javascript">
    function checkNull(str){//检查是否为空
    	var reg = /^\s*$/;//表示0个或多个空白符
    	if(reg.test(str)){	
    		return false;
    	}
    	return true;
    }
  function reloadCode(){	//重载验证码
  	var time = new Date().getTime();
  	document.getElementById("imagecode").src="${pageContext.request.contextPath}/CheckCodeServlet?d="+time;
  };
  function checkSelect(field){
		if(document.getElementById(field).value == "请选择"){
			alert("该项不能为空，请选择")
			return false;
		}
		return true;
	};
	 function checkChange(field){
		  if(field.value!="请选择"){
			  //document.getElementById(field.id+"Span").style.visibility="hidden";
		  } else{
			  alert("该项不能为空，请选择");
		  }
	  };
  window.onload = function() {	//根据学院联动专业
	  
	  /* 	$.get("${pageContext.request.contextPath}/departmentServlet",function(data,status){
	  		alert(data);
	  	}); */
	  
		var department = document.getElementById("department");
		var major = document.getElementById("major");
		var xmlHttp =  new XMLHttpRequest();	//创建ajax请求
		xmlHttp.open("GET", "${pageContext.request.contextPath}/departmentServlet", true);
		xmlHttp.send(null);
		xmlHttp.onreadystatechange = function() {
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				var text = xmlHttp.responseText;
				var arr = text.split(",");
				for(var i = 0; i < arr.length; i++) {
					var op = document.createElement("option");
					op.value = arr[i];
					var textNode = document.createTextNode(arr[i]);
					op.appendChild(textNode);
					department.appendChild(op);
				}
			}
			
			
		}; 
		
		department.onchange = function() {
			checkChange(department);
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "${pageContext.request.contextPath}/majorServlet", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.send("pname=" + department.value);
			xmlHttp.onreadystatechange = function() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					var optionEleList = major.getElementsByTagName("option");
					while(optionEleList.length > 1) {
						major.removeChild(optionEleList[1]);
					}
					var doc = xmlHttp.responseXML;
					var majorEleList = doc.getElementsByTagName("major");
					for(var i = 0; i < majorEleList.length; i++) {
						var majorEle = majorEleList[i];//得到每个major元素
						var majorName;
						if(window.addEventListener) {//处理浏览器的差异
							majorName = majorEle.textContent;//支持FireFox等浏览器
						} else {
							majorName = cityEle.text;//支持IE
						}
						
						var op = document.createElement("option");
						op.value = majorName;
						var textNode = document.createTextNode(majorName);
						op.appendChild(textNode);
						major.appendChild(op);
					}
				}
			};		
		};
		
		var userId = document.getElementById("id");
		userId.onblur = function(){
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "${pageContext.request.contextPath}/user?method=isUserExist", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.send("userId=" + userId.value);
			xmlHttp.onreadystatechange = function() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					var text = xmlHttp.responseText;
					var idExistSpan = document.getElementById("idExistSpan");
					if(text=="0"){
						alert("该账号已经被注册")
					}else{
						
					}
				}
			};	
		};
	};
	function checkForm(){
		var id = $("#id").val();
		var userName = $("#userName").val();
		var password = $("#password").val();
		var rpassword = $("#rpassword").val();
		var grade = $("#grade").val();
		var department = $("#department").val();
		var major = $("#major").val();
		var email = $("#email").val();
		var checkcode = $("#checkcode").val();
		
		var flag = checkNull(id)&&checkNull(password)&&checkNull(rpassword)&&checkNull(userName)&&checkNull(grade)&&checkNull(department)&&checkNull(major)&&checkNull(email)&&checkNull(checkcode);
		if(!flag) alert("信息不完整");
		if(grade == '请选择' || major == '请选择' || deparment == '请选择' ){
			alert("信息不正确")
			return false;
		}
		return flag;
	}
  </script>
  <style type="text/css">
  	 .main{
     margin-right: 20%;
     margin-left: 40%;
     margin-top: 100px;
     width: 300px;
    }
    body{
      background-image: url('${pageContext.request.contextPath}/image/library.jpg');
      background-repeat: no-repeat;
      background-size: 100%;
    }
  </style>
</head>
<body>
<span style="color:red;font-size:16px;">${registError}</span>
<div style="color:red;font-size:16px;">
<c:forEach items="${regist_errorlist}" var="error">
	<p style="color:red;">${error }</p>
</c:forEach>
</div>
<div class="main">
<form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user?method=regist" method="post" onsubmit="return checkForm();">
  <div class="form-group">
    <label for="id" class="col-sm-3 control-label">账号</label>
    <div class="col-sm-9">
      <input type="text" class="form-control" id="id" placeholder="请输入账号" name="userId">
    </div>
  </div>
   <div class="form-group">
    <label for="userName" class="col-sm-3 control-label">名字</label>
    <div class="col-sm-9">
      <input type="text" class="form-control" id="userName" placeholder="请输入名字" name="userName">
    </div>
  </div>
   <div class="form-group">
    <label for="email" class="col-sm-3 control-label">邮箱</label>
    <div class="col-sm-9">
      <input type="text" class="form-control"  placeholder="请输入邮箱" name="email" id="email">
    </div>
  </div>
  <div class="form-group">
    <label for="password" class="col-sm-3 control-label">密码</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password">
    </div>
  </div>
   <div class="form-group">
    <label for="firstname" class="col-sm-3 control-label">确认密码</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="rpassword" placeholder="请再次输入密码" name="rpassword">
    </div>
  </div>
   <div class="form-group">
    <label for="grade" class="col-sm-3 control-label">年级</label>
    <div class="col-sm-9">
    <select class="form-control" name="grade" id="grade" onchange="checkChange(this);">
     	 <option value="请选择">请选择</option>
      	<option value="大一">大一</option>
		<option value="大二">大二</option>
		<option value="大三">大三</option>
		<option value="大四">大四</option>
      </select>
</div>
</div>

 <div class="form-group">
    <label for="department" class="col-sm-3 control-label">学院</label>
    <div class="col-sm-9">
     <select class="form-control" name="department" id="department">
      <option value="请选择">请选择</option>
      </select>
</div>
</div>

 <div class="form-group">
    <label for="major" class="col-sm-3 control-label">专业</label>
    <div class="col-sm-9">
      <select class="form-control" id="major" name="major" onchange="checkChange(this);">
      <option value="请选择">请选择</option>
    </select>
</div>
</div>
    
   <div class="form-group">
    <label for="checkcode" class="col-sm-3 control-label" >验证码</label>
    <div class="col-sm-9">
      <input type="text" class="form-control" id="checkcode" placeholder="请输入验证码" name="registCheckcode">
      <img alt="验证码" id="imagecode" src="${pageContext.request.contextPath}/CheckCodeServlet" onclick="reloadCode();"/>
    <a href="javascript:reloadCode();">看不清楚</a>
    </div>
  </div>

 
  <div class="form-group">
    <div class="col-sm-offset-3 col-sm-3">
      <input type="submit" class="btn btn-info" value="注册">
    </div>
     <div class="col-sm-offset-3  col-sm-3">
      <input type="reset" class="btn btn-success" value="重置">
    </div>
  </div>


</form>
</div>
</body>
</html>