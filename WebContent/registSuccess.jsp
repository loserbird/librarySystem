<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>registSuccess</title>
</head>
<body>
恭喜你注册成功，待管理员审核通过 后便可登陆<br>
<span id="time" style="color:red;">6</span>秒后将跳回登陆界面<br>
你也可直接点击此<a href="login.jsp">链接</a>回到登陆界面
</body>
<script type="text/javascript">
	function delayURL(url) {
		var delay = document.getElementById("time").innerHTML;
		if(delay > 0) {
			delay--;
			document.getElementById("time").innerHTML = delay;
		} else {
			window.location.href = url;
		}
		setTimeout("delayURL('" + url + "')", 1000);
	}

	delayURL("${pageContext.request.contextPath}/login.jsp");
</script>
</html>