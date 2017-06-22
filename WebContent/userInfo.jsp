<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function checkNull(field){	
	var reg = /^\s*$/;
	var value =  document.getElementById(field).value;
	if(reg.test(value)){
		alert("信息不完整");
		return false;
	}
	return true;
}
function checkChange(field){
	  if(field.value!="请选择"){
		  return true;
	  } else{
		  alert("必须选择");
		  return false;
	  }
};
function checkForm(){
	var department = document.getElementById("department");
	var grade = document.getElementById("grade");
	var major = document.getElementById("major");
	var flag1 = checkNull("userName");
	var flag2 = checkNull("email");
	var flag3 = checkChange(grade);
	var flag4 = checkChange(department);
	var flag5 = checkChange(major);
	return flag1&&flag2&&flag3&&flag4&&flag5;
}
	window.onload = function(){
		var updateResult = "${updateResult}";
		if(updateResult == 2){
			alert("个人信息修改失败");
		}else if(updateResult == 1){
			alert("个人信息修改成功");
		}
		
		var department = document.getElementById("department");
		var grade = document.getElementById("grade");
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
				for(var i=0;i<department.length;i++){
					if(department[i].value == "${user.department}"){
						department[i].selected = true;
					}
				}
			}
		};
		for(var i=0;i<grade.length;i++){
			if(grade[i].value == "${user.grade}"){
				grade[i].selected = true;
			}
		}
		
		var option = document.createElement("option");
		option.value = "${user.major}";
		var textNode = document.createTextNode("${user.major}");
		option.appendChild(textNode);
		option.selected = true;
		major.appendChild(option);
		
		
		department.onchange = function() {
			checkChange(department);
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "${pageContext.request.contextPath}/majorServlet", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.send("pname=" + department.value);
			xmlHttp.onreadystatechange = function() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					var major = document.getElementById("major");
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
							majorName = majorEle.text;//支持IE
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
	}
</script>
<style type="text/css">
	.mycontainer{
		width:400px;
		margin:0 auto;
	}
</style>
</head>
<body>
<jsp:include page="common.jsp"/>
<div class="mycontainer">
  <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user?method=update" method="post" onsubmit="return checkForm();">
          <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">账号</label>
          <div class="col-sm-9">
            <label class="control-label">${user.uid}</label>
          </div>
        </div>
        <div class="form-group">
          <label for="userName" class="col-sm-3 control-label">姓名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="userName" id="userName" value="${user.userName}">
          </div>
        </div>
         <div class="form-group">
          <label for="email" class="col-sm-3 control-label">姓名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="email" id="email" value="${user.email}">
          </div>
        </div>
         <div class="form-group">
          <label for="lastname" class="col-sm-3 control-label">年级</label>
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