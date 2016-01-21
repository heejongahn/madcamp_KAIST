package com.example.nobell.project4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShopPageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Feed_item> items;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mShopname, mShopcategory, mShopphone, mShoploc;
    private ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mShopname = (TextView) findViewById(R.id.shopname_textview);
        mShopcategory = (TextView) findViewById(R.id.shopcategory_textview);
        mShopphone = (TextView) findViewById(R.id.shopphone_textview);
        mShoploc = (TextView) findViewById(R.id.shoploc_textview);
        mLogo = (ImageView) findViewById(R.id.logo_imageview);

        get_shop_info();

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.refreshView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_list();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                get_list();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void get_shop_info () {
        //mShopname.set()
        // ...
    }

    public void get_list () {
        items = new ArrayList<>();
        Feed_item[] item=new Feed_item[3];
        item[0]=new Feed_item(R.drawable.a2,"#1");
        item[1]=new Feed_item(R.drawable.b2,"#2");
        item[2]=new Feed_item(R.drawable.c2,"#3");

        for(int i=0;i<3;i++) items.add(item[i]);

        recyclerView.setAdapter(new FeedAdapter(this,items,R.layout.fragment_feed));
    }
}
