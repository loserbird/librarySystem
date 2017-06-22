package com.bingqin.view;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@WebServlet("/majorServlet")
public class MajorServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/xml;charset=utf-8");
		SAXReader sax = new SAXReader();//xml解析器
		InputStream in = this.getClass().getResourceAsStream("/school.xml");
		try {
			Document document = sax.read(in);
			String department = req.getParameter("pname");//得到学院名
			System.out.println(department);
			Element city = (Element) document.selectSingleNode("//department[@name='"+department+"']");
			String xmlStr = city.asXML();
			resp.getWriter().print(xmlStr);
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}
