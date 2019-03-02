package com.zhuandian.mobilelibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zhuandian.mobilelibrary.adapter.BookListAdapter;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.business.book.ScanCardActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_scan)
    TextView tvScan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        getAllBooKList();
    }

    private void getAllBooKList() {
        BmobQuery<BookEntity> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, final BmobException e) {
                list.addAll(list);
                rvList.setAdapter(new BookListAdapter(list, new BookListAdapter.OnStateTextClickListener() {
                    @Override
                    public void onClick(BookEntity bookEntity) {
                        if (bookEntity.getBookState() == 1) {
                            bookEntity.setBookState(2);
                        } else if (bookEntity.getBookState() == 2) {
                            bookEntity.setBookState(1);
                        }
                        bookEntity.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    getAllBooKList();
                                }
                            }
                        });
                    }
                }));
                rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

    }


    @OnClick(R.id.tv_scan)
    public void onClick() {
        startActivity(new Intent(this, ScanCardActivity.class));
    }
}
