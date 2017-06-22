package com.bingqin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bingqin.po.User;

public class UserUtil {
	/**
	 * 用户数据校验
	 * @param user
	 * @return
	 */
	public static List<String> validate(User user){
		List<String> list = new ArrayList<String>();
		Matcher id = Pattern.compile("\\d{10}").matcher(user.getUid());//匹配学号
		Matcher email = Pattern.compile("\\w{3,20}@\\w+\\.(com|org|cn|net|gov)").matcher(user.getEmail());//匹配邮箱
		Matcher name = Pattern.compile("[\u4e00-\u9fa5]{2,10}||[a-zA-Z]{5,25}").matcher(user.getUserName());//匹配姓名
		Matcher password = Pattern.compile("^[a-zA-Z]\\w{5,15}$").matcher(user.getPassword());
		
		if(!id.matches()){
			list.add("账号为你的学号，10个数字字符");
		}
		if(!name.matches()){
			list.add("姓名为2到10个中文字符或5到25个英文为字符");
		}
		if(!email.matches()){
			list.add("邮箱格式错误");
		}
		if(!password.matches()){
			list.add("密码以字母开头，为6到16个字符，不能全为数字或字母，必须是字母数字或其他符号组合");
		}
		if(user.getDepartment().equals("请选择")){
			list.add("学院不能为空");
		}
		if(user.getGrade().equals("请选择")){
			list.add("年级不能为空");
		}
		if(user.getMajor().equals("请选择")){
			list.add("专业不能为空");
		}
		return list;
	}
	/**
	 * 校验密码
	 * @param password
	 * @param rpassword
	 * @return
	 */
	public static List<String> checkPassword(String password,String rpassword){
		Matcher pwm = Pattern.compile("^[a-zA-Z]\\w{5,15}$").matcher(password);
		List<String> list = new ArrayList<String>();
		if(password==null){
			list.add("密码不能为空");
		}
		if(rpassword==null){
			list.add("请确认密码");
		}
		if(!password.equals(rpassword)){
			list.add("密码不一致");
		}
		if(!pwm.matches()){
			list.add("密码以字母开头，为6到16个字符，不能全为数字或字母，必须是字母数字或其他符号组合");
		}
		return list;
	}
	public static void main(String[] args) {
		Matcher name = Pattern.compile("^[a-zA-Z]\\w{5,15}$").matcher("123");
		System.out.println(name.matches());
	}
}
