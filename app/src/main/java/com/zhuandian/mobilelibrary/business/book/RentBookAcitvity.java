package com.zhuandian.mobilelibrary.business.book;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.adapter.BookListAdapter;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class RentBookAcitvity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent_book_acitvity;
    }

    @Override
    protected void setUpView() {
        getAllBooKList();
    }


    private void getAllBooKList() {
        BmobQuery<BookEntity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("bookState", 1);
        bmobQuery.findObjects(new FindListener<BookEntity>() {
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
                                    Toast.makeText(RentBookAcitvity.this, String.format("%s 成功...", bookEntity.getBookState() == 2 ? "还书" : "借阅"), Toast.LENGTH_SHORT).show();
                                    getAllBooKList();
                                }
                            }
                        });
                    }

                    @Override
                    public void onClickBorrow(BookEntity bookEntity) {
                        Toast.makeText(RentBookAcitvity.this, "续借成功，还书周期自动延长30天...", Toast.LENGTH_SHORT).show();
                    }
                }));
                rvList.setLayoutManager(new LinearLayoutManager(RentBookAcitvity.this));
            }
        });

    }
}
