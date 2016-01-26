package com.example.nobell.project4;

import android.app.Activity;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
                try {
                    get_list();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    get_list();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    public void get_list () throws JSONException, IOException{
        class UserGetPostsTask extends AsyncTask<Void, Void, JSONArray> {
            UserGetPostsTask () {

            }

            @Override
            protected JSONArray doInBackground(Void... params) {
                // TODO: attempt authentication against a network service.
                try {
                    String sid = MainActivity.get_SID();
                    JSONObject posts = ServerConnector.GetFromServer("/user/posts", sid);
                    JSONObject shops = ServerConnector.GetFromServer("/user/shops", sid);
                    JSONArray posta = posts.getJSONArray("posts");
                    JSONArray shopa = shops.getJSONArray("shops");
                    Log.e ("posta", posta.toString());
                    Log.e ("shopa", shopa.toString());
                    if (posts.getBoolean("ok") && shops.getBoolean("ok")) {
                        try {
                            Log.e ("posta size", Integer.toString(posta.length()));
                            for (int i=0; i<posta.length(); i++) {
                                JSONObject post = posta.getJSONObject(i);
                                String shopid = post.getString("shopId");
                                Shop_item shop = null;
                                for (int j=0; j<shopa.length(); j++) {
                                    JSONObject shopo = shopa.getJSONObject(j);
                                    if (shopid.equals(shopo.getString("_id"))) {
                                        shop = new Shop_item(shopo.getString("photo"),
                                                shopo.getString("shopname"),
                                                shopo.getString("category"),
                                                shopo.getString("phonenum"),
                                                shopo.getJSONObject("location").getString("address"),
                                                shopo.getJSONObject("location").getDouble("lat"),
                                                shopo.getJSONObject("location").getDouble("lon"),
                                                shopo.getString("_id"));
                                        break;
                                    }
                                }

                                Feed_item feed = new Feed_item(shop,
                                        post.getString("body"),
                                        post.getString("date"),
                                        post.getString("_id"),
                                        post.getString("photo"));
                                items.add(feed);
                            }
                            Log.e ("items1", Integer.toString(items.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return posts.getJSONArray("posts");
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
            protected void onPostExecute(final JSONArray posts) {
                if (posts != null) {
                    Log.e ("items", Integer.toString(items.size()));
                    recyclerView.setAdapter(new FeedAdapter(getContext(),items,R.layout.fragment_feed));
                }
            }

            @Override
            protected void onCancelled() {

            }
        }

        items = new ArrayList<>();
        UserGetPostsTask user = new UserGetPostsTask();
        user.execute();
    }

}

