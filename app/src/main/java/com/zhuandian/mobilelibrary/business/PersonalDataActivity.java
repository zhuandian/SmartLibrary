package com.zhuandian.mobilelibrary.business;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalDataActivity extends BaseActivity {


    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private UserEntity userEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void setUpView() {
        userEntity = BmobUser.getCurrentUser(UserEntity.class);
        etNickName.setText(userEntity.getNikeName());
        etPhone.setText(userEntity.getMobilePhoneNumber());
        etSex.setText(userEntity.getSex());
    }


    @OnClick(R.id.tv_submit)
    public void onClick() {

        if (!TextUtils.isEmpty(etNickName.getText().toString()) || !TextUtils.isEmpty(etSex.getText().toString())) {
            userEntity.setNikeName(etNickName.getText().toString());
            userEntity.setUserInfo(etSex.getText().toString());
            userEntity.setPassword(etPhone.getText().toString());
            userEntity.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(PersonalDataActivity.this, "更新成功...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(PersonalDataActivity.this, "昵称跟描述为必填项...", Toast.LENGTH_SHORT).show();
        }
    }
}
