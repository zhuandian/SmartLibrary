package com.zhuandian.mobilelibrary.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.Utils.Constant;
import com.zhuandian.mobilelibrary.Utils.GlideImageLoader;
import com.zhuandian.mobilelibrary.adapter.BookListAdapter;
import com.zhuandian.mobilelibrary.adapter.HotBookAdapter;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.business.book.BookDetailActivity;
import com.zhuandian.mobilelibrary.business.book.BookListActivity;
import com.zhuandian.mobilelibrary.business.book.RentBookAcitvity;
import com.zhuandian.mobilelibrary.business.book.SearchResultActivity;
import com.zhuandian.mobilelibrary.business.login.LoginActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_categary)
    TextView tvCategary;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_my_list)
    TextView tvMyList;
    @BindView(R.id.rv_list)
    RecyclerView hotBookList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void setUpView() {
        initBanner();
        initHotBook();
    }

    private void initHotBook() {
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(final List<BookEntity> list, BmobException e) {
                Collections.sort(list, new Comparator<BookEntity>() {
                    @Override
                    public int compare(BookEntity o1, BookEntity o2) {
//                         *返回负数表示：o1 小于o2，
//                         *返回0 表示：o1和o2相等，
//                         *返回正数表示：o1大于o2。
                        return o2.getTotalRentCount() - o1.getTotalRentCount();
                    }
                });

                hotBookList.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                hotBookList.setAdapter(new HotBookAdapter(list, new HotBookAdapter.OnStateTextClickListener() {
                    @Override
                    public void onClick(BookEntity bookEntity) {
                        Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
                        intent.putExtra("entity", bookEntity);
                        startActivity(intent);
                    }
                }));

            }
        });
    }

    private void initBanner() {
        List<Integer> images = new ArrayList<>();
        if (Constant.ROLE_ID == 1) {  //模拟用户type类型1登陆成功之后首页展示的内容
            images.add(R.drawable.user_1_1);
            images.add(R.drawable.user_1_2);
        } else if (Constant.ROLE_ID == 2) {//模拟用户type类型2登陆成功之后首页展示的内容
            images.add(R.drawable.user_2_1);
            images.add(R.drawable.user_2_2);
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    @OnClick({R.id.tv_login, R.id.tv_search, R.id.tv_categary, R.id.tv_personal_data, R.id.tv_my_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                startActivityForResult(new Intent(HomeActivity.this, LoginActivity.class), Constant.REQUSET_LOGIN);
                break;
            case R.id.tv_search:
                String bookName = etBookName.getText().toString();
                if (TextUtils.isEmpty(bookName)) {
                    Toast.makeText(this, "请输入类别...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(HomeActivity.this, BookListActivity.class);
                    intent.putExtra("bookType", bookName);
                    startActivity(intent);
                }
                break;
            case R.id.tv_categary:
                showBookCateGaryDialog();
                break;
            case R.id.tv_personal_data:
//                if (Constant.IS_USRE_LOGIN) {
//                    Intent intent = new Intent(HomeActivity.this, BookListActivity.class);
//                    intent.putExtra("bookType", Constant.USER_LIKE_BOOK_TYPE);
//                    startActivity(intent);
//                    Toast.makeText(this, String.format("根据您的浏览习惯，为您推荐了 %s 类型数据", Constant.USER_LIKE_BOOK_TYPE), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "请先登录...", Toast.LENGTH_SHORT).show();
//                }
                if (Constant.IS_USRE_LOGIN) {
                    startActivity(new Intent(HomeActivity.this, PersonalDataActivity.class));
                } else {
                    Toast.makeText(this, "请先登录..", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_my_list:
                if (Constant.IS_USRE_LOGIN) {
                    startActivity(new Intent(HomeActivity.this, RentBookAcitvity.class));
                } else {
                    Toast.makeText(this, "请先登录...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void showBookCateGaryDialog() {
        final String bookCategoryArray[] = {"娱乐", "古典", "励志", "童话"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("选择书籍类型")
                .setItems(bookCategoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, BookListActivity.class);
                        Constant.USER_LIKE_BOOK_TYPE = bookCategoryArray[which];
                        intent.putExtra("bookType", bookCategoryArray[which]);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constant.LOGIN_SUCCESS:
                setUpView();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLogin.setText(Constant.IS_USRE_LOGIN ? "退出" : "登陆");
    }
}
