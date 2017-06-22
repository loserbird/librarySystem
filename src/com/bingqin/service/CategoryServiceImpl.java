package com.bingqin.service;

import java.util.List;

import com.bingqin.dao.CategoryDao;
import com.bingqin.dao.CategoryDaoImpl;
import com.bingqin.po.Category;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao dao = new CategoryDaoImpl();
	
	public CategoryDao getDao() {
		return dao;
	}
	public void setDao(CategoryDao dao) {
		this.dao = dao;
	}
	public List<String> findCategory(){
		return dao.findCategory();
	}
	public Category findCategory(String categoryName){
		return dao.findCategory(categoryName);
	}
	
	public void update(Category c){
		dao.update(c);
	}
	
	public void add(Category c){
		
		dao.add(c);
	}
	
	public List<Category> query() {
		return dao.query();
	}
}
