package com.zhuandian.mobilelibrary.business.book;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.adapter.BookListAdapter;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class BookListActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        getAllBooKList();
    }

    private void getAllBooKList() {
        String bookType = getIntent().getStringExtra("bookType");
        BmobQuery<BookEntity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("bookType", bookType)
                .findObjects(new FindListener<BookEntity>() {
                    @Override
                    public void done(List<BookEntity> list, final BmobException e) {
                        list.addAll(list);
                        rvList.setAdapter(new BookListAdapter(list, new BookListAdapter.OnStateTextClickListener() {
                            @Override
                            public void onClick(final BookEntity bookEntity) {
                                if (bookEntity.getBookState() == 1) {
                                    bookEntity.setBookState(2);
                                } else if (bookEntity.getBookState() == 2) {
                                    bookEntity.setBookState(1);
                                }
                                bookEntity.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(BookListActivity.this, String.format("%s %s 成功...",
                                                    bookEntity.getIsOverdue() == 1 ? "" : "逾期", bookEntity.getBookState() == 2 ? "还书" : "借阅"), Toast.LENGTH_SHORT).show();
                                            getAllBooKList();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onClickBorrow(BookEntity bookEntity) {
                                Toast.makeText(BookListActivity.this, "续借成功，还书周期自动延长30天...", Toast.LENGTH_SHORT).show();
                            }
                        }));
                        rvList.setLayoutManager(new LinearLayoutManager(BookListActivity.this));
                    }
                });

    }

}
