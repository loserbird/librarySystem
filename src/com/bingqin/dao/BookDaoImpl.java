package com.bingqin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bingqin.po.Book;
import com.bingqin.po.Borrows;
import com.bingqin.po.Category;
import com.bingqin.po.Pager;
import com.bingqin.util.DbUtil;

public class BookDaoImpl implements BookDao{
	//添加书籍
	public void add(Book book){
		Connection con = DbUtil.getConnection();
		String sql = "insert into book values(null,?,?,?,?,?,?,1)";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1, book.getBookName());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setInt(4, book.getCategory().getId());
			ps.setString(5, book.getImgurl());
			ps.setInt(6, book.getCount());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);
			DbUtil.close(con);
		}
	}
	//得到所有书籍的数量
	public int getBookCount(String sql){
		Connection con = DbUtil.getConnection();
		
		ResultSet rs1 = DbUtil.getResultSet(con, sql);
		int totalRecords = 0;
		try {
			if(rs1.next()){
				totalRecords = rs1.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			DbUtil.close(rs1);
			DbUtil.close(con);
		}
		return totalRecords;
	}
	//查看所有可借阅的书籍,然后分页
	public Pager<Book> queryAvailableBook(int pageNum,int pageSize){
		String sql1 = "select count(*) from book where isAvailable=1";
		int totalRecords = getBookCount(sql1);
		int totalPage = (totalRecords+pageSize-1)/pageSize;
		int currentPage =totalPage>pageNum?pageNum:totalPage;
		int beginIndex = (currentPage-1)*pageSize;
		String sql = "select b.*,c.categoryName,c.days from book b,category c where b.categoryId=c.id and b.isAvailable=1 limit "+beginIndex+","+pageSize;
		List<Book> list = queryUtil(sql);
		Pager<Book> pager = new Pager<Book>(currentPage,pageSize,totalRecords,list);
		return pager;
	}
	//查找所有书籍
	public List<Book> allBook(){
		String sql = "select b.*,c.categoryName,c.days from book b,category c"
				+ " where b.categoryId=c.id order by isAvailable";
		return queryUtil(sql);
	}
	//辅助查找方法，避免代码重用
	private List<Book> queryUtil(String sql){
		Connection con = DbUtil.getConnection();
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Book> list = new ArrayList<Book>();
		try {
			while(rs.next()){
				Book book = new Book();
				Category c = new Category();
				c.setId(rs.getInt("categoryId"));
				c.setCategoryName(rs.getString("categoryName"));
				c.setDays(rs.getInt("days"));
				book.setBookName(rs.getString("bookName"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(c);
				book.setCount(rs.getInt("count"));
				book.setDescription(rs.getString("description"));
				book.setId(rs.getInt("id"));
				book.setImgurl(rs.getString("imgurl"));
				book.setIsAvailable(rs.getInt("isAvailable"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return list;
	}
	//根据id查找书籍
	public Book queryOneBook(int id) {
		Connection con = DbUtil.getConnection();
		String sql = "select b.*,c.categoryName,c.days from book b,category c "
				+ "where b.categoryId=c.id and b.id="+id;
		ResultSet rs = DbUtil.getResultSet(con, sql);
		Book book = null;
		try {
			if(rs.next()){
				book = new Book();
				Category c = new Category();
				c.setId(rs.getInt("categoryId"));
				c.setCategoryName(rs.getString("categoryName"));
				c.setDays(rs.getInt("days"));
				book.setBookName(rs.getString("bookName"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(c);
				book.setCount(rs.getInt("count"));
				book.setDescription(rs.getString("description"));
				book.setId(rs.getInt("id"));
				book.setImgurl(rs.getString("imgurl"));
				book.setIsAvailable(rs.getInt("isAvailable"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		return book;
	}
	//把书的数量减1
	public void updateCount(int id){
		Connection con = DbUtil.getConnection();
		String sql = "update book b set b.count=b.count-1 where id="+id;
		Statement st = DbUtil.getStatement(con);
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}
	//批量把书的数量加一
	public void updateCount(String[] bookIds){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<bookIds.length;i++){
			sb.append(bookIds[i]);
			if(i<bookIds.length-1) sb.append(",");
		}
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "update book b set b.count=b.count+1 where id in("+sb.toString()+")";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}
	//上架下架书籍
	public void updownBook(String[] bookIds){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<bookIds.length;i++){
			sb.append(bookIds[i]);
			if(i<bookIds.length-1) sb.append(",");
		}
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "update book b set b.isAvailable=not b.isAvailable where b.id in("+sb.toString()+")";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}
	//多条件查找所有可借阅的书籍
	public List<Book> query(String bookName,String author,String categoryName){
		StringBuilder sb = new StringBuilder("select b.*,c.categoryName,c.days from book b,category c where b.isAvailable=1 and b.categoryId=c.id");
		return queryUtil(sb,bookName,author,categoryName);
	}
	//多条件查找所有书籍
	public List<Book> queryAllBook(String bookName,String author,String categoryName){
		StringBuilder sb = new StringBuilder("select b.*,c.categoryName,c.days from book b,category c where b.categoryId=c.id");
		return queryUtil(sb,bookName,author,categoryName);
	}
	private List<Book> queryUtil(StringBuilder sb,String bookName,String author,String categoryName){
		if(bookName!=null&&!"".equals(bookName)){
			sb.append(" and b.bookName like '%" +bookName+"%'");
		}
		if(author!=null&&!"".equals(author)){
			sb.append(" and b.author like '%" +author+"%'");
		}
		if(!categoryName.equals("全部")){
			sb.append(" and c.categoryName='"+categoryName+"'");
		}
		Connection con = DbUtil.getConnection();
		ResultSet rs = DbUtil.getResultSet(con, sb.toString());
		List<Book> list = new ArrayList<Book>();
		try {
			while (rs.next()) {
				Book book = new Book();
				Category c = new Category();
				c.setId(rs.getInt("categoryId"));
				c.setCategoryName(rs.getString("categoryName"));
				c.setDays(rs.getInt("days"));
				book.setBookName(rs.getString("bookName"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(c);
				book.setCount(rs.getInt("count"));
				book.setDescription(rs.getString("description"));
				book.setId(rs.getInt("id"));
				book.setImgurl(rs.getString("imgurl"));
				book.setIsAvailable(rs.getInt("isAvailable"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(con);
		}
		System.out.println(list);
		return list;
	}
	//修改书本信息
	public void update(Book book){
		Connection con = DbUtil.getConnection();
		String sql = "update book b set b.bookName=?,b.author=?,b.description=?,b.count=?,"
				+ "b.categoryId=(select c.id FROM category c WHERE c.categoryName=?) where b.id=?";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1, book.getBookName());
			ps.setString(2,book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setInt(4,book.getCount());
			ps.setString(5, book.getCategory().getCategoryName());
			ps.setInt(6,book.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(ps);
			DbUtil.close(con);
		}
	}
}

