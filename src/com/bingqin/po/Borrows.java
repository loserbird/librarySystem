package com.bingqin.po;

import java.util.Date;

public class Borrows {
	private int id;
	private int isReturned;//是否归还
	private int state;//是否允许结借书
	private Date applyDate;//申请日期
	private Date borrowDate;//借书日期
	private Date returnDate;//还书日期
	private Date shouldReturnDate;//应还日期
	private Book book;
	private User user;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getShouldReturnDate() {
		return shouldReturnDate;
	}
	public void setShouldReturnDate(Date shouldReturnDate) {
		this.shouldReturnDate = shouldReturnDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsReturned() {
		return isReturned;
	}
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
	}
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
