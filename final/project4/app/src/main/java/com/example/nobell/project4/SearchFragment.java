package com.example.nobell.project4;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

        GetAllShopTask task = new GetAllShopTask();
        task.execute();

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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetAllShopTask extends AsyncTask<Void, Void, JSONArray> {
        GetAllShopTask () {

        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                JSONObject json = ServerConnector.GetFromServer("/shop/all", null);

                return json.getJSONArray("shops");

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final JSONArray shops) {
            if (shops != null) {
                for (int i=0; i<shops.length(); i++) {
                    try {
                        JSONObject shop = shops.getJSONObject(i);
                        Shop_item shopi = new Shop_item(shop.getString("photo"),
                                shop.getString("shopname"),
                                shop.getString("category"),
                                shop.getString("phonenum"),
                                shop.getJSONObject("location").getString("address"),
                                shop.getJSONObject("location").getDouble("lat"),
                                shop.getJSONObject("location").getDouble("lon"),
                                shop.getString("_id"));
                        items.add(shopi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setAdapter(new ShopAdapter(getContext(), items, R.layout.fragment_shop_list));
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
