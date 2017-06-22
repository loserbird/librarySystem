package com.bingqin.dao;

import java.util.List;

import com.bingqin.po.Book;
import com.bingqin.po.Pager;

public interface BookDao {
	/**
	 * 添加书籍
	 * @param book
	 */
	public void add(Book book);
	/**
	 * 查找所有可借阅的书籍
	 * @return
	 */
	public Pager<Book> queryAvailableBook(int pageNum,int pageSize);
	/**
	 * 查找所有书籍
	 * @return
	 */
	public List<Book> allBook();
	/**
	 * 根据id查找书籍
	 * @param id
	 * @return
	 */
	public Book queryOneBook(int id);
	/**
	 * 多条件查找所有可借阅书籍
	 * @param bookName
	 * @param author
	 * @param categoryName
	 * @return
	 */
	public List<Book> query(String bookName,String author,String categoryName);
	/**
	 * 多条件查找所有书籍
	 * @param bookName
	 * @param author
	 * @param categoryName
	 * @return
	 */
	public List<Book> queryAllBook(String bookName,String author,String categoryName);
	/**
	 * 批量修改书本数量
	 * @param bookIds
	 */
	public void updateCount(String[] bookIds);
	/**
	 * 把某本书的数量减一
	 * @param id
	 */
	public void updateCount(int id);
	/**
	 * 修改书本信息
	 * @param book
	 */
	public void update(Book book);
	/**
	 * 上架下架书本
	 * @param bookIds
	 */
	public void updownBook(String[] bookIds);
}
