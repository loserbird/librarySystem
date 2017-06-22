package com.bingqin.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/CheckCodeServlet")
public class CheckCodeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int width = 60;
		int height = 20;
		// 绘制一张内存中图片
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		//得到画笔对象
		Graphics g =bi.getGraphics();
		Color c= getRandomColor(200,255);
		//设置画笔颜色
		g.setColor(c);
		g.fillRect(0,0,width,height);		//绘制图片背景
		
		g.setColor(Color.WHITE);		
		g.drawRect(0, 0, width - 1, height - 1);//绘制边框

		
		char[] ch="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		Random r= new Random();
		int len = ch.length,index;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<4;i++){
			index = r.nextInt(len);
			g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));
			g.drawString(ch[index]+"",(i*15)+3,18);
			sb.append(ch[index]);
		}
		req.getSession().setAttribute("checkcode",sb.toString());
		ImageIO.write(bi, "JPG", resp.getOutputStream());
	}
	/**
	 * 取其某一范围的color
	 * 
	 * @param fc
	 *            int 范围参数1
	 * @param bc
	 *            int 范围参数2
	 * @return Color
	 */
	private Color getRandomColor(int fc, int bc){
		Random random = new Random();
		if(fc>255){
			fc = 255;
		}
		if(bc>255){
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
