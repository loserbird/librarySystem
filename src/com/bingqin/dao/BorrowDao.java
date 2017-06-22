package com.bingqin.dao;

import java.util.List;

import com.bingqin.po.Borrows;
import com.bingqin.po.Pager;

public interface BorrowDao {
	/**
	 * 添加申请
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public boolean addApply(int bookId,String userId);
	/**
	 * 查看待审核的借书申请
	 * @return
	 */
	public List<Borrows> queryBorrowApply();
	/**
	 * 根据用户账号查询我的借书情况
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryNotReturnBorrow(String  userId);
	/**
	 * 查询我的借书历史
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryReturnBorrow(String  userId);
	/**
	 * 根据用户账号查询用户的借书记录
	 * @param userId
	 * @return
	 */
	public List<Borrows> queryBorrowRecord(String  userId);
	/**
	 * 查看所有借书记录
	 * @return
	 */
	public Pager<Borrows> allBorrowHistory(int pageNum,int pageSize);
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
	
}
