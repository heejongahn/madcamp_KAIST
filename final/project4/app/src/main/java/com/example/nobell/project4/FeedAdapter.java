package com.example.nobell.project4;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    List<Feed_item> items;
    int item_layout;
    public FeedAdapter(Context context, List<Feed_item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Feed_item item = items.get(position);
        final Shop_item shop = item.get_shop();

//        holder.mLogo.setImageResource(item.get_shop().getImage());
//        Bitmap logo = BitmapFactory.decodeFile(FileManager.getFile(item.get_shop().getImage()).getAbsolutePath());
//        holder.mLogo.setImageBitmap(logo);
        FileManager.getImage(item.get_shop().getImage(), holder.mLogo);
        holder.mShopname.setText(item.get_shop().getName());
        holder.mFeeddate.setText(item.get_date());
        holder.mBody.setText(item.get_body());
//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,item.get_shop().getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
        if (item.get_image()!=null) {
            FileManager.getImage(item.get_image(), holder.mPhoto);
        }

        if (item_layout==R.layout.fragment_feed) {
            holder.mShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ShopPageActivity.class);
                    i.putExtra("logo", shop.getImage());
                    i.putExtra("shopname", shop.getName());
                    i.putExtra("shopcategory", shop.getCategory());
                    i.putExtra("shopphone", shop.getPhone());
                    i.putExtra("location", shop.getLocation());
                    i.putExtra("latitude", shop.getLatitude());
                    i.putExtra("longitude", shop.getLongitude());
                    i.putExtra("shopid", shop.getShopid());
                    i.putExtra("issubscribed", shop.isSubscribed());
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mShopname, mFeeddate, mBody;
        ImageView mLogo, mPhoto;
        CardView cardview;
        LinearLayout mShop;

        public ViewHolder(View itemView) {
            super(itemView);

            mShopname = (TextView) itemView.findViewById(R.id.shopname_textview2);
            mFeeddate = (TextView) itemView.findViewById(R.id.feeddate_textview2);
            mLogo = (ImageView) itemView.findViewById(R.id.logo_imageview2);
            mBody = (TextView) itemView.findViewById(R.id.body_textView2);
            mPhoto = (ImageView) itemView.findViewById(R.id.feed_photo);

            cardview=(CardView)itemView.findViewById(R.id.cardview);
            mShop = (LinearLayout) itemView.findViewById(R.id.shop_layout);
        }
    }
}