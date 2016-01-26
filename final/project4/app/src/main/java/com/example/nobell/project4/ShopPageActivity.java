package com.example.nobell.project4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopPageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Feed_item> items;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mShopname, mShopcategory, mShopphone, mShoploc;
    private ImageView mLogo, mMap;
    private CheckBox mStar;
    Shop_item shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shop & Shop");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mShopname = (TextView) findViewById(R.id.shopname_textview);
        mShopcategory = (TextView) findViewById(R.id.shopcategory_textview);
        mShopphone = (TextView) findViewById(R.id.shopphone_textview);
        mShoploc = (TextView) findViewById(R.id.shoploc_textview);
        mLogo = (ImageView) findViewById(R.id.logo_imageview);
        mMap = (ImageView) findViewById(R.id.map_imageView3);
        mStar = (CheckBox) findViewById(R.id.star_checkbox2);

        Intent i = getIntent();
        shop = new Shop_item(i.getStringExtra("logo"),
                i.getStringExtra("shopname"),
                i.getStringExtra("shopcategory"),
                i.getStringExtra("shopphone"),
                i.getStringExtra("location"),
                i.getDoubleExtra("latitude", 0),
                i.getDoubleExtra("longitude", 0),
                i.getStringExtra("shopid"));

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), MapsActivity.class);
                i.putExtra("shopname", shop.getName());
                i.putExtra("latitude", shop.getLatitude());
                i.putExtra("longitude", shop.getLongitude());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);
            }
        });

        mShopname.setText(shop.getName());
        mShopcategory.setText(shop.getCategory());
        mShopphone.setText(shop.getPhone());
        mShoploc.setText(shop.getLocation());
//        mLogo.setImageResource(shop.getImage());
//        Uri uri = Uri.fromFile(FileManager.getFile(shop.getImage()));
//        mLogo.setImageURI(uri);
        FileManager.getImage(shop.getImage(), mLogo);
        mStar.setChecked(i.getBooleanExtra("issubscribed", false));

        mStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.star_checkbox2) {
                    if (isChecked) {
                        UserSubscribeTask task = new UserSubscribeTask(shop.getShopid());
                        task.execute();
                    } else {
                        UserUnsubscribeTask task = new UserUnsubscribeTask(shop.getShopid());
                        task.execute();
                    }
                }
            }
        });

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
        ShopGetPostTask task = new ShopGetPostTask();
        task.execute();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSubscribeTask extends AsyncTask<Void, Void, JSONObject> {
        String shopid;

        UserSubscribeTask (String shopId) {
            shopid = shopId;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                String sid = MainActivity.get_SID();
                JSONObject json = new JSONObject();
                json.put("shopid", shopid);
                JSONObject result = ServerConnector.uploadToServer(json, "/user/subscribe/", sid);
                if (result.getBoolean("ok")==true) {
                    return result;
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
        protected void onPostExecute(final JSONObject result) {

        }

        @Override
        protected void onCancelled() {

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserUnsubscribeTask extends AsyncTask<Void, Void, JSONObject> {
        String shopid;

        UserUnsubscribeTask (String shopId) {
            shopid = shopId;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                String sid = MainActivity.get_SID();
                JSONObject json = new JSONObject();
                json.put("shopid", shopid);
                JSONObject result = ServerConnector.uploadToServer(json, "/user/unsubscribe/", sid);
                if (result.getBoolean("ok")==true) {
                    return result;
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
        protected void onPostExecute(final JSONObject result) {

        }

        @Override
        protected void onCancelled() {

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ShopGetPostTask extends AsyncTask<Void, Void, JSONArray> {
        ShopGetPostTask () {
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                JSONObject result = ServerConnector.GetFromServer("/shop/posts?shopid="+shop.getShopid(), null);
                if (result.getBoolean("ok")==true) {
                    return result.getJSONArray("posts");
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
                        Feed_item feed = new Feed_item(shop,
                                post.getString("body"),
                                post.getString("date"),
                                post.getString("_id"),
                                post.getString("photo"));
                        items.add(feed);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new FeedAdapter(getApplicationContext(), items, R.id.recyclerview2));
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
