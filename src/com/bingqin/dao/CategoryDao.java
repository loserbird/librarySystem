package com.bingqin.dao;

import java.util.List;

import com.bingqin.po.Category;

public interface CategoryDao {
	/**
	 * 根据类别名得到类别
	 * @return
	 */
	public Category findCategory(String categoryName);
	/**
	 * 得到所有类别
	 * @param categoryName
	 * @return
	 */
	public List<String> findCategory();
	/**
	 * 修改分类
	 * @param c
	 */
	public void update(Category c);
	/**
	 * 添加分类
	 * @param c
	 */
	public void add(Category c);
	/**
	 * 查询分类
	 * @return
	 */
	public List<Category> query() ;
}
