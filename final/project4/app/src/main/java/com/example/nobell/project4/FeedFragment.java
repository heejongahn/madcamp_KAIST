package com.example.nobell.project4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeseongkim on 2015. 11. 29..
 */
public class FeedFragment extends Fragment {
    RecyclerView recyclerView;
    List<Feed_item> items;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.refreshView);
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

        return rootView;
    }

    public void get_list () {
        items = new ArrayList<>();
        Feed_item[] item = new Feed_item[2];
        Shop_item[] shop = new Shop_item[6];
        shop[0] = new Shop_item(R.drawable.starbucks, "Starbucks", "Cafe", "010-3062-4019", "대전광역시 유성구 구성동 한국과학기술원");
        shop[1] = new Shop_item(R.drawable.apple, "Apple Store", "Appliance", "010-4494-4019", "부산광역시 남구 수영동 39-24");
        shop[2] = new Shop_item(R.drawable.dell, "Dell", "Computer", "010-2730-4522", "강원도 삼척시 사직로 4-11");
        shop[3] = new Shop_item(R.drawable.hnm, "H&M", "Fashion", "042-141-4395", "인천광역시 부평구 항동로 46번길 38-3");
        shop[4] = new Shop_item(R.drawable.kfc, "KFC", "Chicken", "123-4566-7875", "우리은하 태양계 화성 마아션");
        shop[5] = new Shop_item(R.drawable.twitter, "Twitter", "SNS", "421-124-4214", "미쿡 샌프란시스코 어딘가");
        item[0] = new Feed_item(shop[0], "20% 할인 진행 중입니다.\n사랑은 점!점!점!점! 그렇게!\n떠나가 버렸지만!!!\n눈물은 가슴속에 묻었다!\n슬픈 체리보이!", "2016.1.21 10:10");
        item[1] = item[0];

        for(int i=0;i<2;i++) items.add(item[i]);
        recyclerView.setAdapter(new FeedAdapter(getContext(),items,R.layout.fragment_feed));
    }
}

