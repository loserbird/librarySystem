package com.bingqin.po;

public class User {
	private String uid;			//账号
	private String userName;//名字;
	private String password;	//密码
	private String email;		//邮箱
	private String major;		//专业
	private String department;  //学院
	private String grade;		//年级
	private String role;		//角色
	private int state;			//0表示未被验证，1表示已被管理员验证
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", major=" + major + ", department=" + department + ", grade=" + grade + ", role=" + role + ", state="
				+ state + "]";
	}
	
	
}
