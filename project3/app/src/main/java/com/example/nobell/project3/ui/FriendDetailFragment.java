package com.example.nobell.project3.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
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

import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

        ListView listView = (ListView) view.findViewById(R.id.friend_detail_listview);
        List<Event> events = friend.getEvents();
        listView.setAdapter(new EventAdapter(this.getActivity(), R.layout.event_item, events));

        ImageView photo = (ImageView) view.findViewById(R.id.friend_detail_image);
        photo.setImageBitmap(friend.getPhoto());

        TextView text = (TextView) view.findViewById(R.id.friend_detail_name);
        text.setText("이름 : " + friend.name);

        TextView count = (TextView) view.findViewById(R.id.friend_detail_event_count);
        count.setText("총 글 갯수 : " + String.valueOf(events.size()));

        TextView last_meet = (TextView) view.findViewById(R.id.friend_detail_last_meet);

        if(events.size() > 0)
        {
            Date date = new Date(FindLastMeet(events));
            DateFormat mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

            last_meet.setText("가장 최근 만난 날 : " + mediumFormat.format(date));
        }
        else
            last_meet.setText("가장 최근 만날 날 : 없음");



        return view;
    }

    private Long FindLastMeet(List<Event> input_events)
    {
        Collections.sort(input_events, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                return lhs.date > rhs.date ? -1 : lhs.date < rhs.date ? 1:0;
            }
        });

        return input_events.get(0).date;

    }

}
