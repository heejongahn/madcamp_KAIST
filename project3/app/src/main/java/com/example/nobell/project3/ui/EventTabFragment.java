package com.example.nobell.project3.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Appearance;
import com.example.nobell.project3.dataset.Description;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.List;

public class EventTabFragment extends Fragment {
    public EventTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_event_tab, container, false);

        ListView listView = (ListView) layout.findViewById(R.id.event_listview);
        List<Event> events = new Select().all().from(Event.class).execute();
        listView.setAdapter(new EventAdapter(this.getActivity(), R.layout.event_item, events));

        return layout;
    }


    public class EventAdapter extends ArrayAdapter<Event> {
        private List<Event> mEvents;
        private int mResource;

        public EventAdapter(Context context, int resource, List<Event> events) {
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

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout item;
            if (convertView != null) {
                item = (LinearLayout) convertView;
            } else {
                item = (LinearLayout) getLayoutInflater(null).inflate(mResource, parent, false);
            }

            LinearLayout old_tags = (LinearLayout) item.findViewById(R.id.event_tags);
            old_tags.removeAllViews();

            LinearLayout old_friends = (LinearLayout) item.findViewById(R.id.event_friends);
            old_friends.removeAllViews();

            Event event = mEvents.get(position);

            TextView body = (TextView) item.findViewById(R.id.event_body);
            body.setText(event.body);

            TextView date = (TextView) item.findViewById(R.id.event_date);
            date.setText(event.getDate());

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);


            LinearLayout tag_layout = (LinearLayout) item.findViewById(R.id.event_tags);

            List<Tag> tags = event.getTags();
            if (tags.size() != 0) {
                Button tagButton = new Button(getActivity());
                tagButton.setLayoutParams(params);
                tagButton.setGravity(Gravity.CENTER);
                tagButton.setMinHeight(0);
                tagButton.setMinWidth(0);
                tagButton.setTextSize(8);

                for (Tag tag: tags) {
                    tagButton.setText(tag.tagName);
                    tag_layout.addView(tagButton);
                }
            }

            LinearLayout friend_layout = (LinearLayout) item.findViewById(R.id.event_friends);

            List<Friend> friends = event.getFriends();
            if (friends.size() != 0) {
                Button friendButton = new Button(getActivity());
                friendButton.setLayoutParams(params);
                friendButton.setGravity(Gravity.CENTER);
                friendButton.setPadding(0, 0, 0, 0);
                friendButton.setMinHeight(0);
                friendButton.setMinWidth(0);
                friendButton.setTextSize(8);;

                for (Friend friend: friends) {
                    friendButton.setText(friend.name);
                    friend_layout.addView(friendButton);
                }
            }

            return item;
        }

    }
}
