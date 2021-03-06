package com.zhuandian.mobilelibrary.business.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.Utils.Constant;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.UserEntity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_user_register)
    TextView tvUserRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private String userName;
    private String passWord;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {

    }


    @OnClick({R.id.tv_user_register, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_register:
                startActivity(new Intent(LoginActivity.this, UserRegisterActivity.class));
                break;
            case R.id.tv_login:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        userName = etUsername.getText().toString();
        passWord = etPassword.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "请完善登陆信息...", Toast.LENGTH_SHORT).show();
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passWord);
            userEntity.login(new SaveListener<UserEntity>() {
                @Override
                public void done(UserEntity userEntity, BmobException e) {
                    if (e == null) {
                        Constant.ROLE_ID = userEntity.getRoleId(); //刷新登陆用户roleId
                        Constant.IS_USRE_LOGIN = true;
                        setResult(Constant.LOGIN_SUCCESS);
//                        startActivity(new Intent(LoginActivity.this, BookListActivity.class));

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "登陆失败...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
