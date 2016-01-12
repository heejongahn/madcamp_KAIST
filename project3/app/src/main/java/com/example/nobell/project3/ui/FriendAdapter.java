package com.example.nobell.project3.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Friend;

import java.util.List;

public class FriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private int mLayout;
    private List<Friend> mFriends;

    public FriendAdapter(Context context, int layout, List<Friend> friends){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.mLayout = layout;
        Friend.sort(friends, false);
        this.mFriends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(mLayout, parent, false);
        }

        Friend friend = mFriends.get(position);

        ImageView photo = (ImageView) convertView.findViewById(R.id.friend_image);
        if (friend.getPhoto() == null) {
            Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.basic_profile);
            photo.setImageBitmap(image);
            friend.setPhoto(image);
        } else {
            photo.setImageBitmap(friend.getPhoto());
        }
        TextView name = (TextView) convertView.findViewById(R.id.friend_name);
        name.setText(friend.name);

        TextView memo = (TextView) convertView.findViewById(R.id.friend_memo);
        memo.setText(friend.memo);

        return convertView;

    }

    @Override
    public int getCount(){return mFriends.size();}
    @Override
    public String getItem(int position){return null;}
    @Override
    public long getItemId(int position){return position;}
}