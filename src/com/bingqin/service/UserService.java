package com.bingqin.service;

import java.util.List;

import com.bingqin.po.User;

public interface UserService {
	/**
	 * 根据账号和密码查找用户
	 * @param uid
	 * @param password
	 * @return
	 */
	public User findUser(String uid,String password);
	/**
	 * 根据账号查找用户
	 * @param uid
	 * @return
	 */
	public User findUser(String uid);
	
	/**
	 *添加用户
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 校验用户数据格式
	 * @param user
	 * @return
	 */
	public List<String> validate(User user);
	/**
	 * 根据账号和新密码修改密码
	 * @param uid
	 * @param password
	 */
	public void updatePassword(String uid,String password);
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user);
	/**
	 * state=0查找尚未审核通过的用户
	 * state=1查找已通过审核的用户
	 * @return
	 */
	public List<User> queryList(int state);
	/**
	 * 允许用户注册
	 * @param list
	 */
	public void allowRegist(String[]  list);
	/**
	 * 多条件查询用户
	 * @param userId
	 * @param userName
	 * @param grade
	 * @return
	 */
	public List<User> queryUser(String userId,String userName,String grade);
}
