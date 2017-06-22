package com.bingqin.po;

import java.util.List;
//用来做分页的类
public class Pager<T> {
	private  int pageSize = 10;//每页显示多少条记录
	
	private int currentPage;//当前显示第几页
	
	private int totalRecords;//一共多少条记录
	
	private int totalPage;//一共多少页
	
	private List<T> dataList;//要显示的数据
	private boolean previous;
	private boolean next;
	
	public Pager(){
		super();
	}
	public Pager(int pageNum,int pageSize,int totalRecords,List<T> sourceList){
		if(sourceList==null||sourceList.size()==0){	//如果没有数据则什么都不做
			return;
		}
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.totalPage = (totalRecords+pageSize-1)/pageSize;
		this.currentPage = this.totalPage>pageNum?pageNum:totalPage;
		this.dataList = sourceList;
	}
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	//判断是否能跳到上一页
	public boolean isPrevious(){
		return this.currentPage > 1;
	}
	//判断是否能跳到下一页
	public boolean isNext(){
		return this.currentPage < getTotalPage();
	}
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
