package com.bingqin.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingqin.po.Book;
import com.bingqin.po.Borrows;
import com.bingqin.po.Category;
import com.bingqin.po.Pager;
import com.bingqin.po.User;
import com.bingqin.service.BookService;
import com.bingqin.service.BookServiceImpl;
import com.bingqin.service.BorrowService;
import com.bingqin.service.BorrowServiceImpl;
import com.bingqin.service.UserService;
import com.bingqin.service.UserServiceImpl;
import com.bingqin.util.BookUtil;
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//判断当前用户是否是管理员，如果不是返回首页
		User user1 = (User) req.getSession().getAttribute("user");
		if(user1!=null&&user1.getRole().equals("student")){	
			resp.sendRedirect("index.jsp");
			return;
		}
		String method = req.getParameter("method");
		if("queryAllUser".equals(method)){
			queryAllUser(req,resp);
		}else if("queryBorrowRecord".equals(method)){
			queryBorrowRecord(req,resp);
		}else if("queryUser".equals(method)){
			queryUser(req,resp);
		}else if("queryAllBook".equals(method)){
			queryAllBook(req,resp);
		}else if("updownBook".equals(method)){
			updownBook(req,resp);
		}else if("queryBook".equals(method)){
			queryBook(req,resp);
		}else if("queryBorrowHistory".equals(method)){
			queryBorrowHistory(req,resp);
		}else if("queryNotRegistUser".equals(method)){
			queryNotRegistUser(req,resp);
		}else if("allowRegist".equals(method)){
			allowRegist(req,resp);
		}else if("queryApplyList".equals(method)){
			queryApplyList(req,resp);
		}else if("allowBorrow".equals(method)){
			allowBorrow(req,resp);
		}else if("preupdate".equals(method)){
			preupdate(req,resp);
		}else if("update".equals(method)){
			update(req,resp);
		}
	}
	//查询所有用户借书记录
	private void queryBorrowHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNum = 1;	//默认为第一页
		int pageSize = 10;	//一页显示10条记录
		String pageNumStr = req.getParameter("pageNum");//得到要显示的 页数
		if(pageNumStr!=null){
			pageNum = Integer.parseInt(pageNumStr);
		}
		BorrowService service = new BorrowServiceImpl();
		Pager<Borrows> pager = service.allBorrowHistory(pageNum,pageSize);//得到pager对象
		req.setAttribute("pager", pager);	//把pager对象设进request里面
		req.getRequestDispatcher("allBorrowHistory.jsp").forward(req, resp);
	}
	//多条件查询所有书籍
	private void queryBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bookName = req.getParameter("bookName");
		String author = req.getParameter("author");
		String categoryName = req.getParameter("category");
		BookService service = new BookServiceImpl();
		List<Book> list = service.queryAllBook(bookName, author, categoryName);
		if(list==null||list.size()==0){
			req.setAttribute("noResult", "找不到符合搜索条件的结果");
		}
		req.setAttribute("allBooklist", list);
		req.getRequestDispatcher("queryAllBook.jsp").forward(req, resp);
		
	}
	//上架下架书籍
	private void updownBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] bookIds = req.getParameterValues("bookId");
		BookService service = new BookServiceImpl();
		service.updownBook(bookIds);
		queryAllBook(req,resp);
	}
	//查询所有书籍
	private void queryAllBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService service = new BookServiceImpl();
		List<Book> list = service.allBook();
		req.setAttribute("allBooklist", list);
		req.getRequestDispatcher("queryAllBook.jsp").forward(req, resp);
	}
	//多条件查询所有用户
	private void queryUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String userName  = req.getParameter("userName");
		String grade = req.getParameter("grade");
		UserService service = new UserServiceImpl();
		List<User> list = service.queryUser(userId,userName,grade);
		req.setAttribute("allUserList", list);
		req.getRequestDispatcher("queryAllUser.jsp").forward(req, resp);
	}
	//查询用户借书记录
	private void queryBorrowRecord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		BorrowService service = new BorrowServiceImpl();
		List<Borrows> list = service.queryBorrowRecord(userId); 
		req.setAttribute("borrowRecords", list);
		req.getRequestDispatcher("showBorrowRecord.jsp").forward(req, resp);
	}
	//查询所有用户
	private void queryAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserService service = new UserServiceImpl();
		List<User> list = service.queryList(1);
		req.setAttribute("allUserList", list);
		req.getRequestDispatcher("queryAllUser.jsp").forward(req, resp);
	}
	
	//查看未审核的用户
	public void queryNotRegistUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserService service = new UserServiceImpl();
		List<User> list = service.queryList(0);
		req.setAttribute("userlist", list);
		req.getRequestDispatcher("queryUserList.jsp").forward(req, resp);
	}
	//允许注册
	public void allowRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uids[] = req.getParameterValues("uid");
		if (uids == null || uids.length == 0) {
			req.setAttribute("allowRegistError", "请勾选要允许注册的用户");
			req.getRequestDispatcher("queryUserList.jsp").forward(req, resp);
			return;
		}
		UserService service = new UserServiceImpl();
		service.allowRegist(uids);
		queryNotRegistUser(req, resp);
	}
	//查询借书申请
	private void queryApplyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BorrowService service = new BorrowServiceImpl();
		List<Borrows> list =  service.queryBorrowApply();
		req.setAttribute("applylist", list);
		req.getRequestDispatcher("applyList.jsp").forward(req, resp);
		
	}
	//允许借书
	private void allowBorrow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] applylist = req.getParameterValues("applyId");
		if(applylist == null||applylist.length==0){
			req.setAttribute("allowBorrowError", "请勾选要允许借阅的申请");
			req.getRequestDispatcher("applyList.jsp").forward(req,resp);
			return;
		}
		BorrowService service = new BorrowServiceImpl();
		service.update(applylist);
		List<Borrows> list =  service.queryBorrowApply();
		req.setAttribute("applylist", list);
		req.getRequestDispatcher("applyList.jsp").forward(req, resp);
	}
	//更该书籍信息
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int count = Integer.parseInt(req.getParameter("count"));
		} catch (Exception e) {
			req.setAttribute("countError", "请正确填写数量");
			preupdate(req, resp);
			return;
		}
		Book book = new Book();
		book.setId(Integer.parseInt(req.getParameter("bookId")));
		book.setAuthor(req.getParameter("author"));
		book.setBookName(req.getParameter("bookName"));
		book.setDescription(req.getParameter("description"));
		book.setCount(Integer.parseInt(req.getParameter("count")));
		Category c = new Category();
		c.setCategoryName(req.getParameter("category"));
		book.setCategory(c);
		List<String> list = BookUtil.validate(book);
		if (list.size() > 0) {
			req.setAttribute("updateError", list);
			preupdate(req, resp);
			return;
		}
		BookService service = new BookServiceImpl();
		service.update(book);
		req.getRequestDispatcher("updateBookSuccess.jsp").forward(req, resp);
	}
	//更改之前显示书籍信息
	private void preupdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService service = new BookServiceImpl();
		String id = req.getParameter("bookId");
		Book book = service.queryOneBook(Integer.parseInt(id));
		req.setAttribute("book", book);
		req.getRequestDispatcher("updateBook.jsp").forward(req, resp);
	}


}
