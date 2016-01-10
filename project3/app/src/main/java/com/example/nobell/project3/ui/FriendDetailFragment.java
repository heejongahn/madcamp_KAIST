package com.example.nobell.project3.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.List;

public class FriendDetailFragment extends Fragment {
    /*
     * This is not on the main tab part.
     * FriendDetailFragment.activate() makes activate this part on
     * the container located in layout/activity_main
     *
     * Objective: reduce activity as much as possible.
     */

    private Long friendId;
    private Friend friend;

    public static void activate(Friend friend) {
        Bundle args = new Bundle();
        args.putLong("friendId", Friend.getIdWithCache(friend));

        Fragment frag = FriendDetailFragment.instantiate(MainActivity.getInstance(), FriendDetailFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public FriendDetailFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle instance) {
        super.onCreate(instance);

        /* After the real fragment started,
         * get arguments from the fragment. */
        friendId = getArguments().getLong("friendId", 0);
        friend = Friend.flushCache(friendId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_detail, container, false);

        ImageView photo = (ImageView) view.findViewById(R.id.friend_detail_image);
        photo.setImageBitmap(friend.getPhoto());

        TextView text = (TextView) view.findViewById(R.id.friend_detail_name);
        text.setText("이름 : " + friend.name);

        ListView listView = (ListView) view.findViewById(R.id.friend_detail_listview);
        List<Event> events = friend.getEvents();
        listView.setAdapter(new FriendDetailListViewAdapter(this.getActivity(), R.layout.friend_detail_event_item, events));

        return view;
    }

    public class FriendDetailListViewAdapter extends ArrayAdapter<Event> {

        private List<Event> mEvents;
        private int mResource;

        public FriendDetailListViewAdapter(Context context, int resource, List<Event> events) {
            super(context, resource, events);
            mEvents = events;
            mResource = resource;
        }

        public int getCount() {
            return mEvents.size();
        }

        public Event getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater(null).inflate(mResource, parent, false);
            }

            Event event = mEvents.get(position);

            TextView body = (TextView) convertView.findViewById(R.id.friend_detail_event_body);
            body.setText(event.body);

            TextView date = (TextView) convertView.findViewById(R.id.friend_detail_event_date);
            date.setText(event.getDate());

            return convertView;
        }

    }
}
