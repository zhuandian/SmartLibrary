package com.zhuandian.mobilelibrary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.List;

/**
 * desc :
 * author：xiedong
 * date：2019/2/28
 */
public class BookListAdapter extends RecyclerView.Adapter {

    private List<BookEntity> mDatas;

    public BookListAdapter(List<BookEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BooKViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class BooKViewHolder extends RecyclerView.ViewHolder {

        public BooKViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
