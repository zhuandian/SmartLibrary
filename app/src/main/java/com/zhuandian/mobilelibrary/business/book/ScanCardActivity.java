package com.zhuandian.mobilelibrary.business.book;

import android.widget.TextView;

import com.zhuandian.mobilelibrary.R;
import com.zhuandian.mobilelibrary.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import butterknife.BindView;

/**
 * desc :
 * author：xiedong
 * date：2019/2/28
 */
public class ScanCardActivity extends BaseActivity {

    @BindView(R.id.card_number)
    TextView cardNumber;
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
                        cardNumber.setText(clientContent);
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

}
