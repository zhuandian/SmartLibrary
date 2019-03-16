package com.zhuandian.mobilelibrary.business.book;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RecommendBookActivity extends BaseActivity {


    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend_book;
    }

    @Override
    protected void setUpView() {

        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, BmobException e) {
                if (e == null) {
                    BookEntity bookEntity = list.get(0);
                    Glide.with(RecommendBookActivity.this).load(bookEntity.getBookImgUrl()).into(ivBook);
                    tvDesc.setText(String.format("根据您的阅读习惯系统为您推荐了 %s, \n\n该书入馆时间：%s\n,该书讲述的故事为：%s", bookEntity.getBookName(), bookEntity.getCreatedAt(), bookEntity.getBookDesc()));
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
