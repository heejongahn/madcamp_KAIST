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
 * Created by Hyoungseok on 2016-01-19.
 */
public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    List<Shop_item> items;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        Shop_item[] shop = new Shop_item[6];
        shop[0] = new Shop_item(R.drawable.starbucks, "Starbucks", "Cafe", "010-3062-4019", "대전광역시 유성구 구성동 한국과학기술원", 36.373863, 127.358183);
        shop[1] = new Shop_item(R.drawable.apple, "Apple Store", "Appliance", "010-4494-4019", "부산광역시 남구 수영동 39-24", 35.170475, 129.116091);
        shop[2] = new Shop_item(R.drawable.dell, "Dell", "Computer", "010-2730-4522", "강원도 삼척시 사직로 4-11", 37.432341, 129.165854);
        shop[3] = new Shop_item(R.drawable.hnm, "H&M", "Fashion", "042-141-4395", "인천광역시 부평구 항동로 46번길 38-3", 37.481814, 126.740523);
        shop[4] = new Shop_item(R.drawable.kfc, "KFC", "Chicken", "123-4566-7875", "우리은하 태양계 화성 마아션", 36.374100, 127.365249);
        shop[5] = new Shop_item(R.drawable.twitter, "Twitter", "SNS", "421-124-4214", "미쿡 샌프란시스코 어딘가", 36.373711, 127.363758);

        for(int i=0;i<6;i++) items.add(shop[i]);

        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.refreshView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_list(items);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                get_list(items);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    public void get_list (List<Shop_item> target_items) {
        recyclerView.setAdapter(new ShopAdapter(getContext(),target_items,R.layout.fragment_shop_list));
    }

    public List<Shop_item> get_target_items(String target, List<Shop_item> items_all){
        List<Shop_item> result_items = new ArrayList<Shop_item>(items_all);
        target = target.toLowerCase();
        for (Shop_item unit: items_all){
            String title = unit.getName().toLowerCase();
            if (!title.contains(target)){
                result_items.remove(unit);
            }
        }
        return result_items;
    }

    public void search_items(String target){
        List<Shop_item> target_items = get_target_items(target, items);
        get_list(target_items);
    }

    public void initialize(){
        get_list(items);
    }

}
