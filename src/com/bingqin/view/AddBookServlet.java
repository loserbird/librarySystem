package com.bingqin.view;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bingqin.po.Book;
import com.bingqin.po.Category;
import com.bingqin.po.User;
import com.bingqin.service.BookService;
import com.bingqin.service.BookServiceImpl;
import com.bingqin.service.CategoryService;
import com.bingqin.service.CategoryServiceImpl;
import com.bingqin.util.UploadUtil;
@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//判断当前用户是否是管理员，如果不是返回首页
		User user1 = (User) req.getSession().getAttribute("user");
		if (user1 != null && user1.getRole().equals("student")) {
			resp.sendRedirect("index.jsp");
			return;
		}
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//将数据封装到map集合中
		Map<String, String> map = new HashMap<String, String>();
		//设置缓存区大小为5m，临时文件存储位置为此项目根路径下的temp目录
		DiskFileItemFactory factory = new DiskFileItemFactory(5*1024*1024,
				new File(this.getServletContext().getRealPath("/temp")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 判断是否是上传操作
		if(upload.isMultipartContent(req)){
			upload.setHeaderEncoding("utf-8");// 解决上传文件中文乱问题
			try {
				List<FileItem> items = upload.parseRequest(req);
				for(FileItem item:items){
					if(item.isFormField()){
						String fieldName = item.getFieldName();
						String value = item.getString("utf-8");
						if("category".equals(fieldName)&&"请选择类别".equals(value)){
							req.setAttribute("categoryError", "请选择类别");
							req.getRequestDispatcher("addBook.jsp").forward(req, resp);
							return;
						}
						map.put(fieldName, value);
					}else{
						//是上传组件
						String fileName = item.getName(); // 得到上传文件名称
						// 得到真实名称
						fileName = UploadUtil.subFileName(fileName);
						// 得到随机名称
						String uuidFileName = UploadUtil.randomFileName(fileName);
						String randomDir = UploadUtil.randomDir(uuidFileName);//得到随机目录
						String path = this.getServletContext().getRealPath(
								"/upload" + randomDir);
						File pathFile = new File(path);
						if (!pathFile.exists()) { //如果 目录不存在，创建
							pathFile.mkdirs();
						}
						// 得到一个imgurl
						String imgurl = "/upload" + randomDir + "/"+ uuidFileName;
						map.put("imgurl", imgurl);//封装上传图片路径	
						try {
							item.write(new File(pathFile, uuidFileName)); // 文件上传操作
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			CategoryService cservice = new CategoryServiceImpl();
			Category category = cservice.findCategory(map.get("category"));//得到类别
			Book book = new Book();
			book.setBookName(map.get("bookName"));
			book.setAuthor(map.get("author"));
			book.setCategory(category);
			book.setCount(Integer.parseInt(map.get("count")));
			book.setDescription(map.get("description"));
			book.setImgurl(map.get("imgurl"));
			BookService bservice = new BookServiceImpl();
			bservice.add(book);	//添加到数据库
			resp.sendRedirect("addBook.jsp");
		}
	}
}
	



