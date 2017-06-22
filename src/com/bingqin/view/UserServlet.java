package com.bingqin.view;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingqin.po.User;
import com.bingqin.service.UserService;
import com.bingqin.service.UserServiceImpl;
import com.bingqin.util.MailUtil;
import com.bingqin.util.UserUtil;
@WebServlet("/user")
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if(method.equals("login")){
			login(req,resp);
		}else if(method.equals("isUserExist")){
			isUserExist(req,resp);
		}else if(method.equals("regist")){
			regist(req,resp);
		}else if(method.equals("logout")){
			logout(req,resp);
		}else if(method.equals("getCheckCode")){
			getCheckCode(req,resp);
		}else if(method.equals("findPassword")){
			findPassword(req,resp);
		}else if(method.equals("update")){
			update(req,resp);
		}else if(method.equals("updatePassword")){
			updatePassword(req,resp);
		}
	}

	//登陆操作
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String loginCheckcode = req.getParameter("loginCheckcode");
		String checkcode = (String) req.getSession().getAttribute("checkcode");
		String loginMsg = "";
		UserService service = new UserServiceImpl();
		if(!loginCheckcode.equalsIgnoreCase(checkcode)){
			loginMsg = "验证码不正确";
			req.setAttribute("loginMsg", loginMsg);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return;
		}
		User user = service.findUser(userId, password);
		String role = req.getParameter("role");
		if(user == null||!user.getRole().equals(role)){
			loginMsg = "此账号不存在或密码错误";
			req.setAttribute("loginMsg", loginMsg);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return;
		}
		if(user.getState()==0){
			loginMsg = "此账号尚未通过管理员的审核";
			req.setAttribute("loginMsg", loginMsg);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return;
		}
		String saveuserid = req.getParameter("remember");//判断用户是否勾选记住账号
		if("on".equals(saveuserid)){
			Cookie cookie = new Cookie("saveuserid",userId);
			cookie.setMaxAge(7 * 24 * 60 * 60); // 可以记住7天
			cookie.setPath("/");
			resp.addCookie(cookie);
		}else{
			Cookie cookie = new Cookie("saveuserid",userId);
			cookie.setMaxAge(0); // 删除cookie
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		// 自动登录
		String autologin = req.getParameter("autologin");
		if ("on".equals(autologin)) {
			// 勾选了自动登录，就将用户名与密码存储到cookie中.
			Cookie cookie = new Cookie("autologin", userId + "," + password);
			cookie.setMaxAge(7 * 24 * 60 * 60);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("autologin", userId + "," + password);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		// 登录成功后，将用户存储到session中.
		req.getSession().invalidate();
		req.getSession().setAttribute("user", user);
		System.out.println(user.getPassword());
		resp.sendRedirect("middle.jsp");
	}
	// 注销操作
	public void logout(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getSession().invalidate(); // 销毁session
		Cookie cookie = new Cookie("autologin", "");//删除cookie
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect(request.getContextPath() + "/login.jsp");

	}

	//判断用户是否存在
	public void isUserExist(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String userid = req.getParameter("userId");
		System.out.println("--------"+userid);
		UserService service = new UserServiceImpl();
		User user = service.findUser(userid);
		if(user!=null){
			resp.getWriter().print("0");
		}else{
			resp.getWriter().print("1");
		}
	}
	//注册操作
	public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String checkcode = (String) req.getSession().getAttribute("checkcode");
		String registCheckcode = req.getParameter("registCheckcode");
		if(!registCheckcode.equalsIgnoreCase(checkcode)){
			req.setAttribute("registError", "验证码不正确");
			req.getRequestDispatcher("/regist.jsp").forward(req, resp);
			return;
		}
		if(!req.getParameter("password").equals(req.getParameter("rpassword"))){
			req.setAttribute("registError", "两次密码不一致");
			req.getRequestDispatcher("/regist.jsp").forward(req, resp);
			return;
		}
		UserService service = new UserServiceImpl();
		User user = null;
		user = service.findUser(req.getParameter("userId"));
		if(user !=null){
			req.setAttribute("registError", "该账号已经被注册过了");
			req.getRequestDispatcher("/regist.jsp").forward(req, resp);
			return;
		}
		user = new User();
		user.setDepartment(req.getParameter("department"));
		user.setEmail(req.getParameter("email"));
		user.setGrade(req.getParameter("grade"));
		user.setMajor(req.getParameter("major"));
		user.setPassword(req.getParameter("password"));
		user.setUid(req.getParameter("userId"));
		user.setUserName(req.getParameter("userName"));
		
		List<String> list = service.validate(user);
		if(list.size()>0){
			req.setAttribute("regist_errorlist", list);
			req.getRequestDispatcher("/regist.jsp").forward(req, resp);
			return;
		}
		service.addUser(user);
		resp.sendRedirect("registSuccess.jsp");
	}
	//给用户邮箱发送验证码
	public void getCheckCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		UserService service = new UserServiceImpl();
		User user = service.findUser(userId);
		String email = user.getEmail();
		String checkCode = UUID.randomUUID().toString().substring(0, 6);
		String emailMsg = "您的验证码是"+checkCode;
		req.getSession().setAttribute("activecode", checkCode);
		MailUtil.sendMail(email, emailMsg);
		
	}
	//找回密码
	public void findPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String rpassword = req.getParameter("rpassword");
		String checkcode = req.getParameter("checkcode");
		String activecode = (String) req.getSession().getAttribute("activecode");
		if(!checkcode.equals(activecode)){
			req.setAttribute("findPasswordError","验证码不正确");
			req.getRequestDispatcher("/findPassword.jsp").forward(req, resp);
			return;
		}
		List<String> list = UserUtil.checkPassword(password,rpassword);
		if(list.size()>0){
			req.setAttribute("findPasswordError", list);
			req.getRequestDispatcher("/findPassword.jsp").forward(req, resp);
			return;
		}
		UserService service = new UserServiceImpl();
		service.updatePassword(userId, password);
		resp.sendRedirect("findPasswordSuccess.jsp");
	}
	//修改个人资料
	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		if(user==null){			//如果user为空返回登陆界面
			resp.sendRedirect("login.jsp");
			return;
		}
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");
		String grade = req.getParameter("grade");
		String department = req.getParameter("department");
		String major = req.getParameter("major");
		user.setEmail(email);
		user.setUserName(userName);
		user.setDepartment(department);
		user.setGrade(grade);
		user.setMajor(major);
		UserService service = new UserServiceImpl();
		List<String> list = service.validate(user);
		if(list.size()>0){
			req.setAttribute("update_errorlist", list);
			req.setAttribute("updateResult", "2");
			req.getRequestDispatcher("static/userInfo.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("updateResult", "1");
		service.updateUser(user);
		req.getSession().setAttribute("user",user);
		//req.getRequestDispatcher("/static/userInfo.jsp").forward(req, resp);
		resp.sendRedirect("static/userInfo.jsp");
	}
	//修改密码
	public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String rPassword = req.getParameter("rpassword");
		User user = (User) req.getSession().getAttribute("user");
		if(!user.getPassword().equals(oldPassword)){
			req.setAttribute("updatePasswordError", "原密码错误");
			req.getRequestDispatcher("/updatePassword.jsp").forward(req, resp);
			return;
		}
		List<String> list = UserUtil.checkPassword(newPassword, rPassword);
		if(list.size()>0){
			req.setAttribute("updatePasswordError", list);
			req.getRequestDispatcher("/updatePassword.jsp").forward(req, resp);
			return;
		}
		UserService service = new UserServiceImpl();
		service.updatePassword(user.getUid(), newPassword);
		req.setAttribute("updatePasswordError", "修改成功");
		req.getRequestDispatcher("/updatePassword.jsp").forward(req, resp);
		//resp.sendRedirect("index.jsp");
	}
}
