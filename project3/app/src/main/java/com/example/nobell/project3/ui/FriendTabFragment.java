package com.example.nobell.project3.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;

import java.util.List;

@SuppressLint("ValidFragment")
public class FriendTabFragment extends Fragment {
    private ListView mListView;
    private FriendAdapter mAdapter;

    public FriendTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_tab,null);

        mListView = (ListView) view.findViewById(R.id.listView);
        List<Friend> friends = new Select().all().from(Friend.class).execute();

        mAdapter = new FriendAdapter(getContext(), R.layout.friend_combined_listview, friends);
        mAdapter.notifyDataSetChanged();

        mListView.setAdapter(mAdapter);
        //m_ListView.setOnItemClickListener(mItemClickListener);

        return view;
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {
            // TODO Add this events
        }
    };


    public class FriendAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private int mLayout;
        private List<Friend> mFriends;

        public FriendAdapter(Context context, int layout, List<Friend> friends){
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mContext = context;
            this.mLayout = layout;
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
            } else {
                photo.setImageBitmap(friend.getPhoto());
            }
            TextView name = (TextView) convertView.findViewById(R.id.friend_name);
            name.setText(friend.name);

            return convertView;

        }

        @Override
        public int getCount(){return mFriends.size();}
        @Override
        public String getItem(int position){return null;}
        @Override
        public long getItemId(int position){return position;}
    }

}
