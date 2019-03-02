package com.zhuandian.mobilelibrary.business.book;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;
import com.zhuandian.mobilelibrary.entity.BookEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * desc :
 * author：xiedong
 * date：2019/2/28
 */
public class ScanCardActivity extends BaseActivity {

    @BindView(R.id.card_number)
    EditText cardNumber;
    private String cardId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_card;
    }


    @Override
    protected void setUpView() {
        runServer();
    }

    public void runServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initServer();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void initServer() {
        try {
            //创建一个ServerSocket，port是客户端的端口
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                //从请求队列中取出链接,如果只接受一次则不用使用while循环
                Socket socket = serverSocket.accept();
                //获取客户端信息
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final String clientContent = input.readLine();
                //接下来可以对信息进行操作，我这里是直接打印了，你可以读取或者做其他操作
                System.out.println(clientContent + "-------------------");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] split = clientContent.split("=");
                        if (split != null) {
                            cardId = split[1];
                        }
                        cardNumber.setText(cardId);


                        upDateBookState(cardId);
                    }
                });

                //初始化输出流，回复客户端
                PrintStream out = new PrintStream(socket.getOutputStream());
                //获取键盘输出的内容
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                //将信息发送给客户端
                out.println("open");

                //关闭
                out.close();
                input.close();
            }
        } catch (IOException e) {
            System.out.println("服务器异常:" + e);
        }
    }

    private void upDateBookState(String cardId) {
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("bookId", Long.valueOf(cardId));
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, BmobException e) {
                if (e == null) {
                    BookEntity bookEntity = list.get(0);
                    if (bookEntity.getBookState() == 1) {
                        bookEntity.setBookState(2);
                    } else if (bookEntity.getBookState() == 2) {
                        bookEntity.setBookState(1);
                    }
                    bookEntity.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ScanCardActivity.this, "同步借阅信息成功...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    @OnClick({R.id.tv_update_state, R.id.tv_update_by_hand})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_by_hand:
                upDateBookState(cardNumber.getText().toString());
                break;
            case R.id.tv_update_state:
                Toast.makeText(ScanCardActivity.this, "请在读取区域刷卡...", Toast.LENGTH_SHORT).show();
                runServer();
                break;

        }

    }
}
