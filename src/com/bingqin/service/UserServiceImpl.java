package com.bingqin.service;

import java.util.List;

import com.bingqin.dao.UserDao;
import com.bingqin.dao.UserDaoImpl;
import com.bingqin.po.User;
import com.bingqin.util.UserUtil;

public class UserServiceImpl implements UserService {
	private UserDao userdao = new UserDaoImpl();
	
	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public User findUser(String uid,String password){
		return userdao.findUser(uid, password);
	}
	public User findUser(String uid){
		return userdao.findUser(uid);
	}
	public void addUser(User user){
		userdao.addUser(user);
	}
	public List<String> validate(User user){
		return UserUtil.validate(user);
	}
	public void updatePassword(String uid,String password){
		userdao.updatePassword(uid, password);
	}
	public void updateUser(User user){
		userdao.updateUser(user);
	}
	public List<User> queryList(int state){
		return userdao.queryList(state);
	}
	public void allowRegist(String[] list){
		userdao.updateUser(list);
	}
	
	public List<User> queryUser(String userId,String userName,String grade){
		return userdao.queryUser(userId, userName, grade);
	}
}
