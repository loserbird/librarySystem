package com.bingqin.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

@WebServlet("/departmentServlet")
public class DepartmentServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		SAXReader sax = new SAXReader();//得到xml解析器
		InputStream in = this.getClass().getResourceAsStream("/school.xml");//创造文件输入流
		try {
			Document document = sax.read(in);//读文件，得到document对象
			List<Attribute> nodelist = document.selectNodes("//department/@name");//得到所有的学院
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<nodelist.size();i++){
				sb.append(nodelist.get(i).getValue());
				if(i<nodelist.size()-1){
					sb.append(",");	//两个学院之间用逗号分隔开
				}
			}
			System.out.println(sb.toString());
			resp.getWriter().print(sb.toString());//输出
		} catch (DocumentException e) {
		
			e.printStackTrace();
		}
	}
}
