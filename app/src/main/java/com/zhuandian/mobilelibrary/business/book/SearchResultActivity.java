package com.zhuandian.mobilelibrary.business.book;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;


import butterknife.BindView;


/**
 * 从搜索页、首页点击图片进入到书籍详细信息页
 */
public class SearchResultActivity extends BaseActivity {


    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }


    @Override
    protected void setUpView() {
        BookEntity bookEntity = (BookEntity) getIntent().getSerializableExtra("entity");
        Glide.with(SearchResultActivity.this).load(bookEntity.getBookImgUrl()).into(ivBook);
        tvBookName.setText("书名：" + bookEntity.getBookName());
        tvAuthor.setText("作者：" + bookEntity.getAuthor());
        tvCompany.setText("出版社：" + bookEntity.getCompanyName());
        tvDesc.setText(String.format("详细信息：\n该书入馆时间：%s\n,该书讲述的故事为：%s", bookEntity.getCreatedAt(), bookEntity.getBookDesc()));
    }


}
