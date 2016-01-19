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
        Shop_item[] item=new Shop_item[5];
        item[0]=new Shop_item(R.drawable.a,"#1");
        item[1]=new Shop_item(R.drawable.b,"#2");
        item[2]=new Shop_item(R.drawable.c,"#3");
        item[3]=new Shop_item(R.drawable.d,"#4");
        item[4]=new Shop_item(R.drawable.e,"#5");

        for(int i=0;i<item.length;i++) items.add(item[i]);

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
        recyclerView.setAdapter(new SearchAdapter(getContext(),target_items,R.layout.fragment_shop_list));
    }

    public List<Shop_item> get_target_items(String target, List<Shop_item> items_all){
        List<Shop_item> result_items = new ArrayList<Shop_item>(items_all);
        target = target.toLowerCase();
        for (Shop_item unit: items_all){
            String title = unit.title.toLowerCase();
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
