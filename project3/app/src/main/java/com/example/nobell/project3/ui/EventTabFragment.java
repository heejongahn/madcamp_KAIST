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
            int i;
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
                if (tags.size() > 3) {
                    for (i=0; i < 3; i++) {
                        Button tagButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                        tagButton.setText(tags.get(i).tagName);
                        tag_layout.addView(tagButton);
                    }
                    TextView moreTags = new TextView(getActivity());
                    moreTags.setText(String.format("...and %d more tags", tags.size() - 3));
                    moreTags.setTextSize(12);
                    tag_layout.addView(moreTags);
                }
                else {
                    for (Tag tag: tags) {
                        Button tagButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                        tagButton.setText(tag.tagName);
                        tag_layout.addView(tagButton);
                    }
                }
            }

            LinearLayout friend_layout = (LinearLayout) item.findViewById(R.id.event_friends);

            List<Friend> friends = event.getFriends();
            if (friends.size() != 0) {
                if (friends.size() > 3) {
                    for (i=0; i < 3; i++) {
                        Button friendButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                        friendButton.setText(friends.get(i).name);
                        friend_layout.addView(friendButton);
                    }
                    TextView moreFriends = new TextView(getActivity());
                    moreFriends.setText(String.format("...and %d more friends", friends.size() - 3));
                    moreFriends.setTextSize(12);
                    friend_layout.addView(moreFriends);
                }
                else {
                    for (Friend friend : friends) {
                        Button friendButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                        friendButton.setText(friend.name);
                        friend_layout.addView(friendButton);
                    }
                }
            }

            return item;
        }

    }
}
