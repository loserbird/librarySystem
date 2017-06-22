package com.bingqin.view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingqin.po.User;
import com.bingqin.service.UserService;
import com.bingqin.service.UserServiceImpl;
import com.bingqin.util.CookieUtils;

public class AutoLoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		//判断当前用户是否登录
		User user = (User) req.getSession().getAttribute("user");
		//得到访问的资源路径
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());
		if(uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("jpg")){
			chain.doFilter(request, response);
			return;
		}
		//System.out.println(path);
		if (user == null) { // user为null说明用户没有登录，可以进行自动登录操作
			
			if (!("/regist.jsp".equalsIgnoreCase(path) || "/login.jsp".equalsIgnoreCase(path))){
				// 符合条件的是可以进行自动登录操作的.

				// 2.3 得到cookie，从cookie中获取username,password
				Cookie cookie = CookieUtils.findCookieByName(req.getCookies(), "autologin");

				if (cookie != null) {
					// 说明有用户名与密码，可以进行自动登录
					String username = cookie.getValue().split(",")[0];
					String password = cookie.getValue().split(",")[1];
					// 2.4调用StudentService方法进行登录操作.
					UserService service = new UserServiceImpl();
					User existUser = service.findUser(username, password);
						if (existUser != null) {
							// 可以进行登录操作
							req.getSession().setAttribute("user", existUser);
						}
				}

			}

		}
		//进行权限校验
		User user2 = (User) req.getSession().getAttribute("user");
		if(user2==null&&!"/login.jsp".equals(path)&&!"/regist.jsp".equals(path)&&!"/registSuccess.jsp".equals(path)
				&&!"/CheckCodeServlet".equals(path)&&!"/user".equals(path)
				&&!"/findPassword.jsp".equals(path)&&!"/findPasswordSuccess.jsp".equals(path)&&!"/departmentServlet".equals(path)&&!"/majorServlet".equals(path)
				){
			resp.sendRedirect("login.jsp");
		}else{
			chain.doFilter(req, resp);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
