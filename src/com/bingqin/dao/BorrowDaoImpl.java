package com.bingqin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bingqin.po.Book;
import com.bingqin.po.Borrows;
import com.bingqin.po.Category;
import com.bingqin.po.Pager;
import com.bingqin.po.User;
import com.bingqin.util.DbUtil;

public class BorrowDaoImpl implements BorrowDao {
	//添加申请
	public boolean addApply(int bookId, String userId){
		Connection con = DbUtil.getConnection();
		String sql1 = "select * from borrow where isReturned=0 and bookId="+bookId+" and userId='"+userId+"'";
		System.out.println(sql1);
		ResultSet rs = DbUtil.getResultSet(con,sql1);
		try {
			if(rs.next()){
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			DbUtil.close(rs);
		}
		String sql = "insert into borrow values(null,?,?,?,null,null,null,0,0)";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		Date date = new Date();
		try {
			ps.setInt(1,bookId);
			ps.setString(2, userId);
			ps.setDate(3, new java.sql.Date(date.getTime()));
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);
			DbUtil.close(con);
		}
		return true;
	}
	//查询待审核的借书申请
	public List<Borrows> queryBorrowApply(){
		Connection con = DbUtil.getConnection();
		String sql = "SELECT bw.id,bw.applyDate,"
				+ "bk.bookName,bk.author,"
				+ "u.uid,u.userName,c.days "
				+ "FROM borrow bw,book bk,USER u,category c "
				+ "WHERE bw.state=0 AND bw.bookId=bk.id AND bw.userId=u.uid AND bk.categoryId=c.id";
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Borrows> list = new ArrayList<Borrows>();
		try {
			while(rs.next()){
				Borrows bw = new Borrows();
				Book b = new Book();
				User u = new User();
				Category c = new  Category();
				c.setDays(rs.getInt("days"));
				b.setAuthor(rs.getString("author"));
				b.setBookName(rs.getString("bookName"));
				b.setCategory(c);
				u.setUid(rs.getString("uid"));
				u.setUserName(rs.getString("userName"));
				bw.setId(rs.getInt("id"));
				bw.setApplyDate(rs.getDate("applyDate"));
				bw.setBook(b);
				bw.setUser(u);
				list.add(bw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return list;
		
	}
	//查询未还的书籍
	public List<Borrows> queryNotReturnBorrow(String  userId){
		Connection con = DbUtil.getConnection();
		String sql = "SELECT bw.id,bw.bookId,bw.borrowDate,bw.shouldReturnDate,"
				+ "bk.bookName,bk.author,"
				+ "c.categoryName "
				+ "FROM borrow bw,book bk,category c "
				+ "WHERE bw.state=1 and bw.isReturned=0 AND bw.bookId=bk.id AND bk.categoryId=c.id and bw.userId='"+userId+"'";
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Borrows> list = new ArrayList<Borrows>();
		try {
			while(rs.next()){
				Borrows bw = new Borrows();
				Book b = new Book();
				Category c = new  Category();
				c.setCategoryName(rs.getString("categoryName"));
				b.setId(rs.getInt("bookId"));
				b.setAuthor(rs.getString("author"));
				b.setBookName(rs.getString("bookName"));
				b.setCategory(c);
				bw.setId(rs.getInt("id"));
				bw.setBorrowDate(rs.getDate("borrowDate"));
				bw.setShouldReturnDate(rs.getDate("shouldReturnDate"));
				bw.setBook(b);
				list.add(bw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return list;
		
	}
	//查询已还的书籍
	public List<Borrows> queryReturnBorrow(String  userId){
		Connection con = DbUtil.getConnection();
		String sql = "SELECT bw.id,bw.bookId,bw.borrowDate,bw.returnDate,"
				+ "bk.bookName,bk.author,"
				+ "c.categoryName "
				+ "FROM borrow bw,book bk,category c "
				+ "WHERE bw.state=1 and bw.isReturned=1 AND bw.bookId=bk.id AND bk.categoryId=c.id and bw.userId='"+userId+"'";
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Borrows> list = new ArrayList<Borrows>();
		try {
			while(rs.next()){
				Borrows bw = new Borrows();
				Book b = new Book();
				Category c = new  Category();
				c.setCategoryName(rs.getString("categoryName"));
				b.setId(rs.getInt("bookId"));
				b.setAuthor(rs.getString("author"));
				b.setBookName(rs.getString("bookName"));
				b.setCategory(c);
				bw.setId(rs.getInt("id"));
				bw.setBorrowDate(rs.getDate("borrowDate"));
				bw.setReturnDate(rs.getDate("returnDate"));
				bw.setBook(b);
				list.add(bw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return list;
		
	}
	//查询用户的 借书记录
	public List<Borrows> queryBorrowRecord(String  userId){
		String sql = "SELECT bw.id,bw.bookId,bw.borrowDate,bw.returnDate,"
				+ "bk.bookName,bk.author,"
				+ "c.categoryName "
				+ "FROM borrow bw,book bk,category c "
				+ "WHERE bw.state=1 AND bw.bookId=bk.id AND bk.categoryId=c.id and bw.userId='"+userId+"'";
		Connection con = DbUtil.getConnection();
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Borrows> list = new ArrayList<Borrows>();
		try {
			while(rs.next()){
				Borrows bw = new Borrows();
				Book b = new Book();
				Category c = new  Category();
				c.setCategoryName(rs.getString("categoryName"));
				b.setId(rs.getInt("bookId"));
				b.setAuthor(rs.getString("author"));
				b.setBookName(rs.getString("bookName"));
				b.setCategory(c);
				bw.setId(rs.getInt("id"));
				bw.setBorrowDate(rs.getDate("borrowDate"));
				bw.setReturnDate(rs.getDate("returnDate"));
				bw.setBook(b);
				list.add(bw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return list;
	}
	//查询所有借书记录
	public Pager<Borrows> allBorrowHistory(int pageNum,int pageSize){
		Connection con = DbUtil.getConnection();
		String sql1 = "select count(*) from borrow where state=1";
		ResultSet rs1 = DbUtil.getResultSet(con, sql1);
		int totalRecords = 0;
		try {
			if(rs1.next()){
				totalRecords = rs1.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			DbUtil.close(rs1);
		}
		int totalPage = (totalRecords+pageSize-1)/pageSize;
		int currentPage =totalPage>pageNum?pageNum:totalPage;
		int beginIndex = (currentPage-1)*pageSize;
		String sql = "SELECT bw.id,bw.bookId,bw.borrowDate,bw.returnDate,"
				+ "bk.bookName,bk.author,"
				+ "c.categoryName,u.uid,u.userName "
				+ "FROM borrow bw,book bk,category c,user u "
				+ "WHERE bw.state=1 AND bw.bookId=bk.id "
				+ "AND bk.categoryId=c.id and bw.userId=u.uid order by borrowDate desc limit "+beginIndex+","+pageSize;
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Borrows> list = new ArrayList<Borrows>();
		try {
			while(rs.next()){
				Borrows bw = new Borrows();
				Book b = new Book();
				Category c = new  Category();
				User u  =new User();
				c.setCategoryName(rs.getString("categoryName"));
				u.setUid(rs.getString("uid"));
				u.setUserName(rs.getString("userName"));
				b.setId(rs.getInt("bookId"));
				b.setAuthor(rs.getString("author"));
				b.setBookName(rs.getString("bookName"));
				b.setCategory(c);
				bw.setId(rs.getInt("id"));
				bw.setBorrowDate(rs.getDate("borrowDate"));
				bw.setReturnDate(rs.getDate("returnDate"));
				bw.setBook(b);
				bw.setUser(u);
				list.add(bw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		Pager<Borrows> pager = new Pager<Borrows>(currentPage,pageSize,totalRecords,list);
		return pager;
	}
	//允许借书申请
	public void update(String[] list){
		Date date = new Date();
		java.sql.Date bdate = new java.sql.Date(date.getTime());
		StringBuilder sb = new StringBuilder("update borrow set state=1,"
				+ "borrowDate='"+bdate+"',shouldReturnDate = case id ");
		for(int i=0;i<list.length;i++){
			String sb1 = list[i].split(",")[0];
			String sb2= list[i].split(",")[1];
			java.sql.Date rdate = new java.sql.Date(date.getTime()+(long)Integer.parseInt(sb2)*24*3600*1000);
			sb.append("when "+sb1+" then "+"'"+rdate+"' ");
		}
		sb.append("end where id in(");
		for(int i=0;i<list.length;i++){
			String sb3= list[i].split(",")[0];
			sb.append(sb3);
			if(i<list.length-1) sb.append(",");
		}
		sb.append(")");
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		try {
			st.executeUpdate(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}
	//还书
	public void returnBook(String[] ids){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ids.length;i++){
			sb.append(ids[i]);
			if(i<ids.length-1) sb.append(",");
		}
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		String sql = "update borrow set isReturned=1,returnDate='"+date+"' where id in("+sb.toString()+")";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}

}
