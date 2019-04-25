package com.zhuandian.mobilelibrary.business.book;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.business.HomeActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BookDetailActivity extends BaseActivity {


    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    private BookEntity bookEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void setUpView() {
        String bookName = getIntent().getStringExtra("bookName");
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("bookName", bookName);
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        tvDesc.setText("抱歉，本馆暂时未收入您要检索的图书...");
                        return;
                    }
                     bookEntity = list.get(0);
                    Glide.with(BookDetailActivity.this).load(bookEntity.getBookImgUrl()).into(ivBook);
                    tvDesc.setText(String.format("成功为您检索到 %s, \n\n该书入馆时间：%s\n,内容简介：%s", bookEntity.getBookName(), bookEntity.getCreatedAt(), bookEntity.getBookDesc()));
                }
            }
        });

    }




    @OnClick(R.id.iv_book)
    public void onClick() {
        Intent intent = new Intent(BookDetailActivity.this, SearchResultActivity.class);
        intent.putExtra("entity", bookEntity);
        startActivity(intent);
    }
}
