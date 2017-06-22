package com.bingqin.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bingqin.po.Book;
import com.bingqin.service.BookService;
import com.bingqin.service.BookServiceImpl;
@WebServlet("/collect")
/**
 * 收藏夹
 * @author 蔡炳钦
 *
 */
public class CollectServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if("add".equals(method)){
			add(req,resp);
		}else if("remove".equals(method)){
			remove(req,resp);
		}
	}
	//取消收藏
	private void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] bookids = req.getParameterValues("bookId");
		HttpSession session = req.getSession();
		List<Book> collect = (List<Book>) session.getAttribute("collect");//得到收藏夹
		for(int j=0;j<bookids.length;j++){
			for(int i=0;i<collect.size();i++){
				Book book = collect.get(i);
				if(book.getId()==Integer.parseInt(bookids[j])){
					collect.remove(i);
				}
			}
		}
		session.setAttribute("collect", collect);
		req.getRequestDispatcher("showCollect.jsp").forward(req, resp);
		
	}
	//添加收藏
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		HttpSession session = req.getSession();
		List<Book> collect = (List<Book>) session.getAttribute("collect");//得到收藏夹
		if(collect==null){	//如果收藏夹不存在，说明第一次收藏书籍
			collect = new ArrayList<Book>();
		}
		for(int i=0;i<collect.size();i++){
			Book book = collect.get(i);
			if(book.getId()==bookId){
				req.setAttribute("addCollectError", "该书籍你已经收藏");
				req.getRequestDispatcher("showCollect.jsp").forward(req, resp);
				return;
			}
		}
		BookService service = new BookServiceImpl();
		Book newbook = service.queryOneBook(bookId);
		collect.add(newbook);
		session.setAttribute("collect", collect);
		req.getRequestDispatcher("showCollect.jsp").forward(req, resp);
	}

}
