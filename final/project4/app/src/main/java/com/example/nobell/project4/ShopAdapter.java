package com.example.nobell.project4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop,null);
        return new ViewHolder(v);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Shop_item item=items.get(position);
        holder.mLogo.setImageResource(item.getImage());
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
                context.startActivity(i);
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
}