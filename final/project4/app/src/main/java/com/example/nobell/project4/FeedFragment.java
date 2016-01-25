package com.example.nobell.project4;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        items = new ArrayList<>();
        UserGetPostsTask user = new UserGetPostsTask();
        user.execute();

        recyclerView.setAdapter(new FeedAdapter(getContext(),items,R.layout.fragment_feed));
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserGetPostsTask extends AsyncTask<Void, Void, JSONArray> {
        UserGetPostsTask () {

        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            try {
                String sid = MainActivity.get_SID();
                JSONObject posts = ServerConnector.GetFromServer("/user/posts", sid);
                if (posts.getBoolean("ok")==true) {
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
                try {
                    for (int i=0; i<posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);
                        String shopid = post.getString("shopid");
                        Feed_item feed = new Feed_item(MainActivity.get_SHOP(shopid),
                                post.getString("body"),
                                post.getString("date"),
                                post.getString("_id"));
                        items.add(feed);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}

