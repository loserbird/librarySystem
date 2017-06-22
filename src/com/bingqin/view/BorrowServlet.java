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
import com.bingqin.service.BookService;
import com.bingqin.service.BookServiceImpl;
import com.bingqin.service.BorrowService;
import com.bingqin.service.BorrowServiceImpl;
@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {
	BorrowService service = new BorrowServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if("applyBorrow".equals(method)){
			applyBorrow(req,resp);
		}else if("queryMyBorrow".equals(method)){
			queryMyBorrow(req,resp);
		}else if("returnBook".equals(method)){
			returnBook(req,resp);
		}else if("queryMyBorrowHistory".equals(method)){
			queryMyBorrowHistory(req,resp);
		}
	}
	//查看我的借书历史
	private void queryMyBorrowHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		List<Borrows> list =  service.queryReturnBorrow(userId);
		req.setAttribute("myborrowHistory", list);
		req.getRequestDispatcher("myBorrowHistory.jsp").forward(req, resp);
	}

	//还书
	private void returnBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] borrowIds = req.getParameterValues("borrowId");
		BorrowService service = new BorrowServiceImpl();
		if(borrowIds==null||borrowIds.length==0){
			req.setAttribute("returnBookError", "请勾选要还书的书籍");
			req.getRequestDispatcher("myBorrowList.jsp").forward(req, resp);
			return;
		}
		service.returnBook(borrowIds);
		queryMyBorrow(req,resp);
	}
	//查询我的借阅
	private void queryMyBorrow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		List<Borrows> list =  service.queryNotReturnBorrow(userId);
		req.setAttribute("myborrowlist", list);
		req.getRequestDispatcher("myBorrowList.jsp").forward(req, resp);
		
	}


	//申请借书
	private void applyBorrow(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		String userId = req.getParameter("userId");
		BorrowService service = new BorrowServiceImpl();
		boolean flag = service.addApply(bookId, userId);
		if(!flag){
			req.setAttribute("applyExistError", "该书籍您已申请借阅，请勿重复申请");
			BookService bookservice = new BookServiceImpl();
			Book book = bookservice.queryOneBook(bookId);
			req.setAttribute("book", book);
			req.getRequestDispatcher("queryOneBook.jsp").forward(req, resp);
			return;
		}
		resp.sendRedirect("applySuccess.jsp");
	}
	
}
