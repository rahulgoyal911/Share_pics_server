package com.example.rahul.ps;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class MainActivity extends AppCompatActivity
{
    TextView tv1,tv2;
    ServerSocket ss;
    Socket path;
    Thread sThread;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        sThread = new Thread(new Runnable()
        {
            //int ctr=0;
            @Override
            public void run()
            {
                try
                {
                    ss=new ServerSocket(2568);
                    path=ss.accept();
                    DataOutputStream dos = new DataOutputStream(path.getOutputStream());
                    BufferedOutputStream bos = new BufferedOutputStream(dos);

                    FileInputStream fis = new FileInputStream("/storage/emulated/0/aaa.jpg");
                    int input;
                    //int ctr=0;
                    while ((input = fis.read()) != -1)
                    {
                        dos.write(input);
                        ///ctr++;
                       // Toast.makeText(this,(ctr/1024/1024),Toast.LENGTH_SHORT).show();
                        //tv2.setText(String.valueOf(ctr/1024/1024));
                        //tv2.setText((fis.read()/1024/1024));
                    }

                    bos.flush();
                    dos.flush();
                    bos.close();
                    dos.close();
                    fis.close();
                    Message msg = Message.obtain();
                    msg.obj = "File successfully transferred!";
                    handler.sendMessage(msg);

                }
                catch(Exception e)
                {
                    Message msg = Message.obtain();
                    msg.obj = e.toString();
                    handler.sendMessage(msg);
                }
            }
        });
        sThread.start();


    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            tv1.setText(msg.obj.toString());
            super.handleMessage(msg);
        }
    };

}


