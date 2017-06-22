package com.bingqin.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * 发邮件类
 * @author 蔡炳钦
 *
 */
public class MailUtil {
	/**
	 * 
	 * @param email 邮件地址
	 * @param emailMsg 邮件内容
	 */
	public static void sendMail(String email,String emailMsg){
		// 创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("15626140150", "CBQ768593");//账号名加密码
			}
		};
		Session session = Session.getInstance(props, auth);
		// 创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress("15626140150@163.com")); // 设置发送者
			message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
			message.setSubject("用户激活");

			message.setContent(emailMsg, "text/html;charset=utf-8");
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		//创建 Transport用于将邮件发送
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
