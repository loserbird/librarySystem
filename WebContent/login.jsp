<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">  
   <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
   <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <style type="text/css">
   .main{
     margin-right: 20%;
     margin-left: 40%;
     margin-top: 100px;
     width: 300px;
     height: 400px;
    }
    .findPassword{
    margin-right: 20%;
     margin-left: 45%;
     margin-bottom: 100px;
     width: 300px;
     font-size: 16px;
    }
    body{
      background-image: url('${pageContext.request.contextPath}/image/library.jpg');
      background-repeat: no-repeat;
      background-size: 100%;
    }
  </style>
 <script type="text/javascript">
    function reloadCode(){	//刷新验证码
    	var time = new Date().getTime();
    	document.getElementById("imagecode").src=		//后面加时间浏览器才能够识别，不使用缓存。重新加载
    		"${pageContext.request.contextPath}/CheckCodeServlet?d="+time;
    };
    function checkNull(str){//检查是否为空
    	var reg = /^\s*$/;//表示0个或多个空白符
    	if(reg.test(str)){	
    		return false;
    	}
    	return true;
    }
    function clearError(){	//清除错误
    	var err = document.getElementById("errorSpan");
    	if(checkNull(err.innerHTML)){
    		err.innerHTML = '';
    	}
    	
    }
  	function checkForm(){	//检查表单提交时有没有错误
    	var id = document.getElementById("id");
    	var password = document.getElementById("password");
    	var checkcode = document.getElementById("checkcode");
    	var idspan = document.getElementById("idSpan");
    	var passwordspan = document.getElementById("passwordSpan");
    	var checkcodespan = document.getElementById("checkcodeSpan");
    	var flag1 = checkNull(id.value);
    	var flag2 = checkNull(password.value);
    	var flag3 = checkNull(checkcode.value);
    	if(!flag1){
    		idspan.innerHTML='账号不能为空';
    	}
    	if(!flag2){
    		passwordspan.innerHTML='密码不能为空';
    	}
    	if(!flag3){
    		checkcodespan.innerHTML='验证码不能为空';
    	}
    	
    	id.onfocus = function(){
    		idspan.innerHTML='';
    	}
    	id.onblur = function(){
    		if(!checkNull(id.value)){
    			idspan.innerHTML='账号不能为空';
    		}
    	}
    	password.onfocus = function(){
    		passwordspan.innerHTML='';
    	}
    	password.onblur = function(){
    		if(!checkNull(password.value)){
    			passwordspan.innerHTML='密码不能为空';
    		}
    	}
    	checkcode.onfocus = function(){
    		checkcodespan.innerHTML='';
    	}
    	checkcode.onblur = function(){
    		if(!checkNull(checkcode.value)){
    			checkcodespan.innerHTML='验证码不能为空';
    		}
    	}
    	
    	return flag1&&flag2&&flag3;
    }
  	window.onload = function(){
  		var stuidvalue = "${cookie.saveuserid.value}";//得到cookie里面保存的用户账号
  		var id = document.getElementById("id");
  		var remember = document.getElementById("remember");//得到checkbox对象
  		if(checkNull(stuidvalue)){	//如果stuidvalue不为空，让remeber勾选
  			remember.checked = true;
  		}else{
  			remember.checked = false;
  		}
  		id.value = stuidvalue;	//把账号显示在文本框里
  	};
  	
//计时器
  	 var Time=60,t;
  	 var c=Time;
  	 function timedCount(){	//计时
  	     document.getElementById('btn').value="重新发送(" + c + ")秒";
  	     document.getElementById('btn').disabled="disabled";
  	     c=c-1;
  	     t=setTimeout("timedCount()",1000)
  	     if(c<0){
  	         c=Time;
  	         stopCount();
  	         document.getElementById('btn').value="重新发送验证码";
  	         document.getElementById('btn').removeAttribute("disabled");
  	     }
  	 }
  	 function stopCount(){
  	     clearTimeout(t);
  	 }
  		function getcheckcode(){
  			var userid = $("#re-id").val();//获取账号
  	  		if(!checkNull(userid)){
  	  			alert("请先填写账号");
  	  			return;
  	  		}else{
  	  		$.post("${pageContext.request.contextPath}/user?method=isUserExist",
  	  			    {
  	  				userId:userid,
  	  			    },
  	  			    function(data,status){
  	  			        if(data!=0) {
  	  			        	alert("账号不存在");
  	  			        	return;
  	  			        }
  	  			        
  	  			    $.post("${pageContext.request.contextPath}/user?method=getCheckCode",
  	  	  			    {
  	  	  				userId:userid,
  	  	  			    },
  	  	  			    function(data,status){
  	  	  			        
  	  	  			    }); 
  	  			timedCount();
  	  			    });
  	  		}
  	  	}
  		
  		function checkregist(){
  			var id = $("#re-id").val();
  			var password = $("#re-password").val();
  			var rpassword = $("#re-rpassword").val();
  			var checkcode = $("#re-checkcode").val();
  			
  			var flag = checkNull(id)&&checkNull(password)&&checkNull(rpassword)&&checkNull(checkcode);
  			if(!flag) alert("信息不完整");
  			else if(password != rpassword){
  				alert("两次密码不一致");
  				return false;
  			}
  			return flag;
  		}
 </script>
<style type="text/css">
span{
	color:red;
}
</style>
</head>
<body>

<span id="errorSpan">${requestScope.loginMsg }</span>	
<div class="main">
<form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user?method=login" method="post" onsubmit="return checkForm();">
  <div class="form-group">
    <label for="id" class="col-sm-3 control-label">账号</label>
    <div class="col-sm-9">
      <input type="text" class="form-control" id="id" placeholder="请输入账号" name="userId"><span id="idSpan"></span>
    </div>
  </div>
  <div class="form-group">
    <label for="password" class="col-sm-3 control-label">密码</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password"><span id="passwordSpan"></span>
    </div>
  </div>
   <div class="form-group">
    <label for="checkcode" class="col-sm-3 control-label">验证码</label>
    <div class="col-sm-9">
      <input type="text" class="form-control" id="checkcode" placeholder="请输入验证码" name="loginCheckcode">
      <img alt="验证码" id="imagecode" src="${pageContext.request.contextPath}/CheckCodeServlet" onclick="reloadCode();"/>
    			<a href="javascript:reloadCode();">看不清楚</a>
    			<span id="checkcodeSpan"></span>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-4">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="remember" value="on"  id="remember">记住我
        </label>
      </div>
    </div>
     <div class="col-sm-6">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="autologin" value="on">自动登录
        </label>
      </div>
    </div>
  </div>
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-4">
      <div class="radio">
        <label>
          <input type="radio" name="role" value="student" checked="checked">学生
        </label>
      </div>
    </div>
     <div class="col-sm-6">
      <div class="radio">
        <label>
          <input type="radio" name="role" value="admin">管理员
        </label>
      </div>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-4">
      <button type="submit" class="btn btn-info" onclick="clearError();">登录</button>
    </div>
     <div class=" col-sm-6">
      <a href="regist.jsp" class="btn btn-success" role="button">注册</a>
    </div>
  </div>
</form>

</div>


<div class="findPassword">
  <label>忘记密码？</label>
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  找回密码
</button>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
          找回密码
        </h4>
      </div>
      <div class="modal-body">
        


        <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user?method=findPassword" method="post" onsubmit="return checkregist();">
          <div class="form-group">
          <label for="firstname" class="col-sm-3 control-label">账号</label>
          <div class="col-sm-9">
            <input type="text" class="form-control"  placeholder="请输入账号" name="userId" id="re-id">
          </div>
        </div>
        <div class="form-group">
          <label for="lastname" class="col-sm-3 control-label">密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" id="lastname" placeholder="请输入密码" name="password" id="re-password">
          </div>
        </div>
         <div class="form-group">
          <label for="lastname" class="col-sm-3 control-label">确认密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" id="lastname" placeholder="请确认密码" name="rpassword" id="re-rpassword">
          </div>
        </div>
         <div class="form-group">
          <label for="lastname" class="col-sm-3 control-label">验证码</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" placeholder="请输入验证码" name="checkcode" id="re-checkcode">
          </div>
           <div class="col-sm-4">
              <input type="button" class="btn btn-info" onclick="getcheckcode();" id="btn" value="获取验证码"></input>
          </div>
        </div>
        <div>
        	<div class="form-group">
        	
        	<div class="col-sm-offset-6 col-sm-2">
        	  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        	</div>
        	<div class="col-sm-offset-1 col-sm-3">
        	  <input type="submit" class="btn btn-primary" value="提交更改"/>
        	</div>  
        </div>
		</div>
        </form>


      </div>
      <div class="modal-footer">
      
        
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div>
</body>
</html>