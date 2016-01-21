package com.example.nobell.project4;

import android.content.Intent;
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
    Shop_item shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("샵앤샵 (#&#)");
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

        Intent i = getIntent();

        shop = new Shop_item(i.getIntExtra("logo", -1),
                i.getStringExtra("shopname"),
                i.getStringExtra("shopcategory"),
                i.getStringExtra("shopphone"),
                i.getStringExtra("location"));

        mShopname.setText(shop.getName());
        mShopcategory.setText(shop.getCategory());
        mShopphone.setText(shop.getPhone());
        mShoploc.setText(shop.getLocation());
        mLogo.setImageResource(shop.getImage());

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

        item[0] = new Feed_item(shop, "20% 할인 진행 중입니다.\n" +
                "사랑은 점!점!점!점! 그렇게!\n" +
                "떠나가 버렸지만!!!\n" +
                "눈물은 가슴속에 묻었다!\n" +
                "슬픈 체리보이!", "2016.1.21 10:10");
        item[1] = item[0];
        for(int i=0;i<2;i++) items.add(item[i]);

        recyclerView.setAdapter(new FeedAdapter(this,items, R.layout.fragment_feed));
    }
}
