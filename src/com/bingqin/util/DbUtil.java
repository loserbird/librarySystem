package com.bingqin.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtil {
	//创造数据源数据源对象
	private static ComboPooledDataSource  datasource = new ComboPooledDataSource();
	//防止多线程并发问题
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = tl.get();//从当前线程中取出连接对象 
		if(con!=null) return con;//如果不为空，则返回，否则从连接池中取
		else{
			try {
				con = datasource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}
			
	}
	public static DataSource getDataSource(){
		return datasource;
	}
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void beginTransaction() throws SQLException{
		Connection con = tl.get();//从当前线程中取出连接对象 ，如果不为空，则表示事务已经开启
		if(con!=null) throw new SQLException("事务已经开启，请不要重复开启");
		con = getConnection();
		con.setAutoCommit(false);
		tl.set(con);	//把该连接设为当前线程的连接对象
	}
	public static void CommitTransaction() throws SQLException{
		Connection con = tl.get();	
		if(con==null) throw new SQLException("事务还没开启，不能提交");
			con.commit();
			con.close();
			tl.remove();	//事务提交之后从当前线程中移除连接
	}
	public static void rollbackTransaction() throws SQLException{
		Connection con = tl.get();
		if(con==null) throw new SQLException("事务还没开启，不能回滚");
		con.rollback();
		con.close();
		tl.remove();	//事务回滚之后从当前线程中移除连接
	}
	/**
	 * 关闭数据库连接对象
	 * @param connection
	 */
	public static void close(Connection connection) {
		Connection con = tl.get();
			try {
				if(con == null)
				connection.close();
				// 如果con != null，说明有事务，那么需要判断参数连接是否与con相等，若不等，说明参数连接不是事务专用连接
				if(con != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * 获取PreparedStatement
	 * @param con
	 * @param sql
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection con,String sql){
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}
	/**
	 * 获得Statement
	 * @param conn
	 * @return
	 */
	public static Statement getStatement(Connection con) {
		Statement stmt = null; 
		try {
			if(con != null) {
				stmt = con.createStatement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	/**
	 * 获得结果集
	 * @param stmt
	 * @param sql
	 * @return
	 */
	public static ResultSet getResultSet(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			if(stmt!= null) {
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 获得结果集
	 * @param pstmt
	 * @return
	 */
	public static ResultSet getResultSet(PreparedStatement pstmt) {
		ResultSet rs = null;
		try {
			if(pstmt!= null) {
				rs = pstmt.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 获得结果集
	 * @param con
	 * @param sql
	 * @return
	 */
	public static ResultSet getResultSet(Connection con, String sql) {
		ResultSet rs = null;
		Statement stmt = getStatement(con);
		try {
			if(stmt!= null) {
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 关闭statement
	 * @param st
	 */
	public static void close(Statement st){
		try {
			if(st!=null){
				st.close();
				st=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭结果集
	 * @param rs
	 */
	public static void close(ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
				rs=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
