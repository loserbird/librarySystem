package com.bingqin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bingqin.po.User;
import com.bingqin.util.DbUtil;

public class UserDaoImpl implements UserDao{
	//辅助查找方法，避免代码重用
	private User queryUtil(String sql){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		ResultSet rs = DbUtil.getResultSet(st, sql);
		User user = null;
		try {
			if(rs.next()){
				user = new User();
				user.setDepartment(rs.getString("department"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getString("grade"));
				user.setMajor(rs.getString("major"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setState(rs.getInt("state"));
				user.setUid(rs.getString("uid"));
				user.setUserName(rs.getString("userName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(st);
			DbUtil.close(con);
		}
		return user;
	}
	//根据账号和密码查找用户
	public User findUser(String uid,String password){
		String sql = "select * from user where uid='"+uid+"' and password='"+password+"'";
		return queryUtil(sql);
		
	}
	//根据账号查找用户
	public User findUser(String uid){
		String sql = "select * from user where uid='"+uid+"'";
		return queryUtil(sql);
	}
	//添加用户
	public void addUser(User user){
		Connection con = DbUtil.getConnection();
		String sql = "insert into user values(?,?,?,?,'student',?,?,?,0)";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1, user.getUid());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getDepartment());
			ps.setString(6, user.getMajor());
			ps.setString(7, user.getGrade());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);
			DbUtil.close(con);
		}
	}
	//修改密码
	public void updatePassword(String uid,String password){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "update user set password='"+password+"' where uid="+uid;
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(st);
			DbUtil.close(con);
		}
	}
	//修改用户信息
	public void updateUser(User user){
		Connection con = DbUtil.getConnection();
		String sql = "update user set userName=?,email=? where uid='"+user.getUid()+"'";
		PreparedStatement ps = DbUtil.getPreparedStatement(con, sql);
		try {
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(ps);
			DbUtil.close(con);
		}
	}
	//辅助查找方法，避免代码重用
	public List<User> query(String sql){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		ResultSet rs = DbUtil.getResultSet(st, sql);
		List<User> list = new ArrayList<User>();
		try{
			while(rs.next()){
				User user = new User();
				user.setDepartment(rs.getString("department"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getString("grade"));
				user.setMajor(rs.getString("major"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setState(rs.getInt("state"));
				user.setUid(rs.getString("uid"));
				user.setUserName(rs.getString("userName"));
				list.add(user);
				} 
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbUtil.close(rs);
				DbUtil.close(st);
				DbUtil.close(con);
			}
		return list;
	}
	//根据用户是否注册查找用户
	public List<User> queryList(int state){
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "select * from user where role='student' and state="+state;
		return query(sql);
	}
	//多条件查找用户，拼接sql语句
	public List<User> queryUser(String userId,String userName,String grade){
		StringBuilder sb = new StringBuilder("select * from user where role='student' and state=1");
		if(userId!=null&&!"".equals(userId)){
			sb.append(" and uid like '%"+userId+"%'");
		}
		if(userName!=null&&!"".equals(userName)){
			sb.append(" and userName like '%"+userName+"%'");
		}
		if(!grade.equals("请选择")){
			sb.append(" and grade='"+grade+"'");
		}
		System.out.println(sb.toString());
		return query(sb.toString());
	}
	//修改用户注册状态
	public void updateUser(String[] list){
		StringBuilder sb = new StringBuilder("");
		for(int i=0;i<list.length;i++){
			sb.append(list[i]);
			if(i<list.length-1) sb.append(",");
		}
		Connection con = DbUtil.getConnection();
		Statement st = DbUtil.getStatement(con);
		String sql = "update user set state=1 where uid in("+sb.toString()+")";
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
