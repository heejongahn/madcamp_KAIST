package com.example.nobell.project4;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        final Feed_item item=items.get(position);

        holder.mLogo.setImageResource(item.get_shop().getImage());
        holder.mShopname.setText(item.get_shop().getName());
        holder.mFeeddate.setText(item.get_date());
//        holder.mShopphone.setText(item.get_shop().getPhone());
        holder.mBody.setText(item.get_body());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,item.get_shop().getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mShopname, mFeeddate, mBody;
        ImageView mLogo;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);

            mShopname = (TextView) itemView.findViewById(R.id.shopname_textview2);
            mFeeddate = (TextView) itemView.findViewById(R.id.feeddate_textview2);
            mLogo = (ImageView) itemView.findViewById(R.id.logo_imageview2);
            mBody = (TextView) itemView.findViewById(R.id.body_textView2);

            cardview=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}