package com.example.nobell.project4;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    Context context;
    List<Shop_item> items;
    int item_layout;
    public ShopAdapter(Context context, List<Shop_item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop, parent, false);
        return new ViewHolder(v);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Shop_item item=items.get(position);
//        holder.mLogo.setImageResource(item.getImage());
//        Bitmap logo = BitmapFactory.decodeFile(FileManager.getFile(item.getImage()).getAbsolutePath());
//        holder.mLogo.setImageBitmap(logo);
//        Uri uri = Uri.fromFile(FileManager.getFile(item.getImage()));
//        holder.mLogo.setImageURI(uri);
        FileManager.getImage(item.getImage(), holder.mLogo);
        holder.mShopname.setText(item.getName());
        holder.mShopcategory.setText(item.getCategory());
        holder.mShopphone.setText(item.getPhone());
        holder.mLocation.setText(item.getLocation());

        holder.mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("shopname", item.getName());
                i.putExtra("latitude", item.getLatitude());
                i.putExtra("longitude", item.getLongitude());
                context.startActivity(i);
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShopPageActivity.class);
                i.putExtra("logo", item.getImage());
                i.putExtra("shopname", item.getName());
                i.putExtra("shopcategory", item.getCategory());
                i.putExtra("shopphone", item.getPhone());
                i.putExtra("location", item.getLocation());
                i.putExtra("latitude", item.getLatitude());
                i.putExtra("longitude", item.getLongitude());
                i.putExtra("shopid", item.getShopid());
                i.putExtra("issubscribed", holder.mStar.isChecked());
                context.startActivity(i);
            }
        });

//        if (MainActivity.get_SHOP(item.shopid) != null) {
//            holder.mStar.setChecked(true);
//        }
        holder.mStar.setChecked(item.isSubscribed());

        holder.mStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.star_checkbox) {
                    if (isChecked) {
                        UserSubscribeTask task = new UserSubscribeTask(item.getShopid());
                        task.execute();
                    } else {
                        UserUnsubscribeTask task = new UserUnsubscribeTask(item.getShopid());
                        task.execute();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mShopname, mShopcategory, mShopphone, mLocation;
        ImageView mLogo, mMap;
        CardView cardview;
        CheckBox mStar;

        public ViewHolder(View itemView) {
            super(itemView);

            mShopname = (TextView) itemView.findViewById(R.id.shopname_textview3);
            mShopcategory = (TextView) itemView.findViewById(R.id.shopcategory_textview3);
            mShopphone = (TextView) itemView.findViewById(R.id.shopphone_textview3);
            mLogo = (ImageView) itemView.findViewById(R.id.logo_imageview3);
            mLocation = (TextView) itemView.findViewById(R.id.shoploc_textview3);
            mMap = (ImageView) itemView.findViewById(R.id.map_imageView3);
            mStar = (CheckBox) itemView.findViewById(R.id.star_checkbox);
            cardview=(CardView)itemView.findViewById(R.id.cardview);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public static class UserSubscribeTask extends AsyncTask<Void, Void, JSONObject> {
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
    public static class UserUnsubscribeTask extends AsyncTask<Void, Void, JSONObject> {
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
}