package com.bingqin.view;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingqin.po.Category;
import com.bingqin.service.CategoryService;
import com.bingqin.service.CategoryServiceImpl;
@WebServlet("/category")
public class CategoryServlet extends HttpServlet{
	CategoryService service = new CategoryServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if("getAllCategory".equals(method)){
			getAllCategory(req,resp);
		}else if("addCategory".equals(method)){
			addCategory(req,resp);
		}else if("queryCategory".equals(method)){
			queryCategory(req,resp);
		}
	}
	private void queryCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Category> list = service.query();
		req.setAttribute("categoryList", list);
		req.getRequestDispatcher("queryCategory.jsp").forward(req, resp);
	}

	private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryName = req.getParameter("name");
		String daysStr = req.getParameter("days");
		System.out.println(categoryName);
		if(categoryName == null||categoryName.equals("")){
			req.setAttribute("categoryError", "类别名字不能为空");
			req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
			return;
		}
		if(daysStr==null||daysStr.equals("")){
			req.setAttribute("categoryError", "天数不能为空");
			req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
			return;
		}
		Matcher name = Pattern.compile("[\u4e00-\u9fa5\\w]+").matcher(categoryName);
		if(!name.matches()){
			req.setAttribute("categoryError", "请正确填写类别名字");
			req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
			return;
		}
		int days = 0;
		try{
			days = Integer.parseInt(daysStr);
		}catch(Exception e){
			req.setAttribute("categoryError", "请正确填写天数");
			req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
			return;
		}
		
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setDays(days);
		service.add(category);
		queryCategory(req,resp);
	}

	//得到所有的类别名字
	protected void getAllCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		List<String> list = service.findCategory();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i));
			if(i<list.size()-1) sb.append(",");
		}
		resp.getWriter().print(sb.toString());
	}
	
}
