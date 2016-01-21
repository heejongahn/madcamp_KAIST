package com.example.nobell.project4;

import android.graphics.drawable.Drawable;
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

    public void get_list () {
        items = new ArrayList<>();
        Feed_item[] item = new Feed_item[2];
        Shop_item[] shop = new Shop_item[2];
        shop[0] = new Shop_item(R.drawable.starbucks, "Starbucks", "Cafe", "010-3062-4019", "대전광역시 유성구 구성동 한국과학기술원");
        item[0] = new Feed_item(shop[0], "20% 할인 진행 중", "2016.1.21 10:10");
        item[1] = item[0];
        for(int i=0;i<2;i++) items.add(item[i]);

        mShopname.setText(shop[0].getName());
        mShopcategory.setText(shop[0].getCategory());
        mShopphone.setText(shop[0].getPhone());
        mShoploc.setText(shop[0].getLocation());
        mLogo.setImageResource(shop[0].getImage());

        recyclerView.setAdapter(new FeedAdapter(this,items,R.layout.fragment_feed));
    }
}
