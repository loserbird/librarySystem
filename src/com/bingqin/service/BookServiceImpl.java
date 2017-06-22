package com.bingqin.service;

import java.util.List;

import com.bingqin.dao.BookDao;
import com.bingqin.dao.BookDaoImpl;
import com.bingqin.po.Book;
import com.bingqin.po.Pager;

public class BookServiceImpl implements BookService{
	private BookDao bookdao = new BookDaoImpl();

	public BookDao getBookdao() {
		return bookdao;
	}

	public void setBookdao(BookDao bookdao) {
		this.bookdao = bookdao;
	}
	public void add(Book book){
		bookdao.add(book);
	}
	public Pager<Book> queryAvailableBook(int pageNum,int pageSize){
		return bookdao.queryAvailableBook(pageNum, pageSize);
	}
	public Book queryOneBook(int id){
		return bookdao.queryOneBook(id);
	}
	public List<Book> query(String bookName,String author,String categoryName){
		return bookdao.query(bookName, author, categoryName);
	}
	public List<Book> allBook(){
		return bookdao.allBook();
	}
	public void updownBook(String[] bookIds){
		bookdao.updownBook(bookIds);
	}
	public void update(Book book){
		bookdao.update(book);
	}
	public List<Book> queryAllBook(String bookName,String author,String categoryName){
		return bookdao.queryAllBook(bookName, author, categoryName);
	}
}
