package com.bingqin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bingqin.po.Category;
import com.bingqin.util.DbUtil;

public class CategoryDaoImpl implements CategoryDao{
	//得到所有的类别
	public List<String> findCategory(){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "select categoryName from category";
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			rs = st.executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(st);		
			DbUtil.close(con);
		}
		return list;
	}
	
	public Category findCategory(String categoryName){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "select * from category where categoryName='"+categoryName+"'";
		ResultSet rs = null;
		Category c = null;
		try {
			rs = st.executeQuery(sql);
			if(rs.next()){
				c = new Category();
				c.setId(rs.getInt("id"));
				c.setCategoryName(rs.getString("categoryName"));
				c.setDays(rs.getInt("days"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(st);		
			DbUtil.close(con);
		}
		return c;//否则返回1
	}
	//添加分类
	public void add(Category c){
		Connection con = DbUtil.getConnection();
		String sql = "insert into category values(null,?,?)";
		System.out.println(sql);
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1,c.getCategoryName());
			ps.setInt(2, c.getDays());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);		
			DbUtil.close(con);
		}
	}
	//修改分类
	public void update(Category c){
		Connection con = DbUtil.getConnection();
		String sql = "update category set categoryName=?,days=? where id=?";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1,c.getCategoryName());
			ps.setInt(2, c.getDays());
			ps.setInt(3, c.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);		
			DbUtil.close(con);
		}
	}
	//查询分类
	public List<Category> query() {
		Connection con = DbUtil.getConnection();
		String sql = "select * from category";
		ResultSet rs = DbUtil.getResultSet(con, sql);
		List<Category> list = new ArrayList<Category>();
		try {
			while(rs.next()){
				Category c = new Category();
				c.setCategoryName(rs.getString("categoryName"));
				c.setDays(rs.getInt("days"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DbUtil.close(rs);		
			DbUtil.close(con);
		}
		return list;
	}
}
