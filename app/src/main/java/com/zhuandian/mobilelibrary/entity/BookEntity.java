package com.zhuandian.mobilelibrary.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2019/2/26
 */
public class BookEntity extends BmobObject {
    private long bookId;
    private int bookState;  //1 .已经借阅   2.未借阅
    private String bookName;
    private String bookDesc;
    private String bookImgUrl;
    private String bookType;
    private int totalRentCount;
    private int isAvailable; // 1.未报废 2.图书已报废

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getTotalRentCount() {
        return totalRentCount;
    }

    public void setTotalRentCount(int totalRentCount) {
        this.totalRentCount = totalRentCount;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookImgUrl() {
        return bookImgUrl;
    }

    public void setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getBookState() {
        return bookState;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }
}
