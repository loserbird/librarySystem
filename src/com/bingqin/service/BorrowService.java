package com.bingqin.service;

import java.util.List;

import com.bingqin.po.Borrows;
import com.bingqin.po.Pager;

public interface BorrowService {
	/**
	 * 添加借书申请
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public boolean addApply(int bookId,String userId);
	/**
	 * 查询待审核的借书申请
	 * @return
	 */
	public List<Borrows> queryBorrowApply();
	/**
	 * 查询用户未还的 书籍
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryNotReturnBorrow(String  userId);
	/**
	 * 查询已还的书籍
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryReturnBorrow(String  userId);
	/**
	 * 允许借书申请
	 * @param list
	 */
	public void update(String[] list);
	/**
	 * 还书
	 * @param ids
	 */
	public void returnBook(String[] ids);
	/**
	 * 根据用户账号查询用户的借书记录
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryBorrowRecord(String  userId);
	/**
	 * 
	 * @param pageNum 当前页数
	 * @param pageSize 一页显示的数量
	 * @return	pager对象
	 */
	public Pager<Borrows> allBorrowHistory(int pageNum,int pageSize);
}
