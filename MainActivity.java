package com.example.ouyifu.swiperefreshdemo;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> datas;
    private RecyclerView mrecyclerView;
    private Myadapter myadapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    myadapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrecyclerView = (RecyclerView) findViewById(R.id.mrecycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mswipe);
        datas = new ArrayList<String>();
        initdata();
        myadapter = new Myadapter(this, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        mrecyclerView.setAdapter(myadapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // datas.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            refresh();
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void initdata() {
        for (int i = 'A'; i < 'z'; i++) {
            datas.add((char) i + "");
        }
    }

    public void refresh() {
        for (int i = 'A'; i < 'z'; i++) {
            datas.add("new" + (char) i);
        }
    }

}
