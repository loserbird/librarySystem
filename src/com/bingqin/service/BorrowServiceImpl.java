package com.bingqin.service;

import java.sql.SQLException;
import java.util.List;

import com.bingqin.dao.BookDaoImpl;
import com.bingqin.dao.BorrowDao;
import com.bingqin.dao.BorrowDaoImpl;
import com.bingqin.po.Borrows;
import com.bingqin.po.Pager;
import com.bingqin.util.DbUtil;

public class BorrowServiceImpl implements BorrowService {
	private BorrowDao dao = new BorrowDaoImpl();
	
	public BorrowDao getDao() {
		return dao;
	}

	public void setDao(BorrowDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean addApply(int bookId, String userId) {
		boolean flag = false;
		try {
			DbUtil.beginTransaction();	//开启事务
			new BookDaoImpl().updateCount(bookId);//书本数量减一
			 flag=dao.addApply(bookId, userId);	//添加借书申请
			DbUtil.CommitTransaction();//提交事务
		} catch (SQLException e) {
			try {
				DbUtil.rollbackTransaction();//事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
		
	}
	public List<Borrows> queryBorrowApply(){
		return dao.queryBorrowApply();
	}
	public List<Borrows> queryNotReturnBorrow(String  userId){
		return dao.queryNotReturnBorrow(userId);
	}
	public List<Borrows> queryReturnBorrow(String  userId){
		return dao.queryReturnBorrow(userId);
	}
	public void update(String[] list){
		dao.update(list);
	}
	public void returnBook(String[] ids){
		String[] bookIds = new String[ids.length];
		String[] borrowIds = new String[ids.length];
		for(int i=0;i<ids.length;i++){
			borrowIds[i]=ids[i].split(",")[0];
			bookIds[i]=ids[i].split(",")[1];
		}
		try {
			DbUtil.beginTransaction();
			dao.returnBook(borrowIds);
			new BookDaoImpl().updateCount(bookIds);
			DbUtil.CommitTransaction();
		} catch (SQLException e) {
			try {
				DbUtil.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public List<Borrows> queryBorrowRecord(String  userId){
		return dao.queryBorrowRecord(userId);
	}
	public Pager<Borrows> allBorrowHistory(int pageNum,int pageSize){
		return dao.allBorrowHistory(pageNum, pageSize);
	}

}
