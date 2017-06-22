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
import com.bingqin.service.BookService;
import com.bingqin.service.BookServiceImpl;
import com.bingqin.util.BookUtil;
@WebServlet("/book")
public class BookServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if(method==null){
			forward(req,resp);
		}else if("queryAllBook".equals(method)){
			queryAllBook(req,resp);
		}else if("queryOneBook".equals(method)){
			queryOneBook(req,resp);
		}else if("query".equals(method)){
			query(req,resp);
		}
	}
	

	private void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("middle.jsp").forward(req, resp);
		
	}
	//用户查询书籍
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bookName = req.getParameter("bookName");
		String author = req.getParameter("author");
		String categoryName = req.getParameter("category");
		BookService service = new BookServiceImpl();
		List<Book> list = service.query(bookName, author, categoryName);
		if(list==null||list.size()==0){
			req.setAttribute("noResult", "找不到符合搜索条件的结果");
		}
		req.setAttribute("booklist", list);
		req.getRequestDispatcher("queryBook.jsp").forward(req, resp);
		
	}
	//显示某一本书籍信息
	protected void queryOneBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService service = new BookServiceImpl();
		String id = req.getParameter("id");
		Book book = service.queryOneBook(Integer.parseInt(id));
		req.setAttribute("book", book);
		req.getRequestDispatcher("queryOneBook.jsp").forward(req, resp);
	}
	//查询所有可借阅的书籍
	protected void queryAllBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNum = 1;	//默认为第一页
		int pageSize = 10;	//一页显示10条记录
		String pageNumStr = req.getParameter("pageNum");//得到要显示的 页数
		if(pageNumStr!=null){
			pageNum = Integer.parseInt(pageNumStr);
		}
		BookService service = new BookServiceImpl();
		Pager<Book> pager = service.queryAvailableBook(pageNum, pageSize);//得到pager对象
		req.setAttribute("pager", pager);
		req.getRequestDispatcher("queryMyBook.jsp").forward(req, resp);
	}
	
}
