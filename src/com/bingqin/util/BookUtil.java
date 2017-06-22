package com.bingqin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bingqin.po.Book;
/**
 * 校验书本数据
 * @author 蔡炳钦
 *
 */
public class BookUtil {
	public static List<String> validate(Book book){
		Matcher bookName = Pattern.compile("[\u4e00-\u9fa5\\w]+").matcher(book.getBookName());
		Matcher author = Pattern.compile("[\u4e00-\u9fa5]{2,10}||[a-zA-Z]{5,25}").matcher(book.getAuthor());
		List<String> list  = new ArrayList<String>();
		if(!bookName.matches()){
			list.add("请正确填写书名");
		}
		if(!author.matches()){
			list.add("作者名字为2-10个中文字符或2到25个英文字符");
		}
		if(book.getDescription()==null||"".equals(book.getDescription())){
			list.add("描述不能为空");
		}
		if(book.getCount()<0){
			list.add("书的数量不能为负数");
		}
		if("请选择类别".equals(book.getCategory().getCategoryName())){
			list.add("请选择类别");
		}
		return list;
		
	}
}
