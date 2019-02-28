package com.zhuandian.mobilelibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.business.book.ScanCardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    }


    @OnClick(R.id.tv_scan)
    public void onClick() {
        startActivity(new Intent(this, ScanCardActivity.class));
    }
}
