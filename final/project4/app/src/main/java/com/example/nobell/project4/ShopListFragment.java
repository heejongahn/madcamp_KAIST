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
 * Created by daeseongkim on 2015. 11. 29..
 */
public class ShopListFragment extends Fragment {
    RecyclerView recyclerView;
    List<Shop_item> items;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shop_list, container, false);

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

        class UserGetShopsTask extends AsyncTask<Void, Void, JSONArray> {
            UserGetShopsTask() {

            }

            @Override
            protected JSONArray doInBackground(Void... params) {
                // TODO: attempt authentication against a network service.
                try {
                    String sid = MainActivity.get_SID();
                    JSONObject json = ServerConnector.GetFromServer("/user/shops", sid);
                    Log.e ("/user/shops json", json.toString());
                    if (json.getBoolean("ok") == true) {
                        return json.getJSONArray("shops");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

                // TODO: register the new account here.
                return null;
            }

            @Override
            protected void onPostExecute(final JSONArray shops) {
                if (shops != null) {
                    for (int i = 0; i < shops.length(); i++) {
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

        items = new ArrayList<>();
        UserGetShopsTask task = new UserGetShopsTask();
        task.execute();
    }

}

