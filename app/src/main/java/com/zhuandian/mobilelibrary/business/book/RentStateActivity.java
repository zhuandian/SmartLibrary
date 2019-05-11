package com.zhuandian.mobilelibrary.business.book;


import android.os.Bundle;
import android.widget.TextView;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;
import com.zhuandian.mobilelibrary.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class RentStateActivity extends BaseActivity {


    @BindView(R.id.tv_user_info)
    TextView tvUserInfo;
    @BindView(R.id.tv_rent_state)
    TextView tvRentState;
    @BindView(R.id.tv_rent_time)
    TextView tvRentTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent_state;
    }

    @Override
    protected void setUpView() {
        BookEntity entity = (BookEntity) getIntent().getSerializableExtra("entity");
        tvUserInfo.setText("用户姓名：" + BmobUser.getCurrentUser(UserEntity.class).getUsername());
        tvRentState.setText(entity.getBookState() == 1 ? "借书成功" : "还书成功");
        tvRentTime.setText((entity.getBookState() == 1 ? "借书时间" : "还书时间") + entity.getUpdatedAt());

    }


}
