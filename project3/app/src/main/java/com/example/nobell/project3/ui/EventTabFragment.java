package com.example.nobell.project3.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Appearance;
import com.example.nobell.project3.dataset.Description;
import com.example.nobell.project3.dataset.Event;

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
            LinearLayout item = (LinearLayout) convertView;
            if (convertView == null) {
                item = (LinearLayout) getLayoutInflater(null).inflate(mResource, parent, false);
            }

            Event event = mEvents.get(position);

            TextView body = (TextView) item.findViewById(R.id.event_body);
            body.setText(event.body);

            TextView tag = (TextView) item.findViewById(R.id.event_tag);
            int numOfTags = event.getTags().size();
            if (numOfTags != 0) {
                tag.setText(String.format("%d 개의 태그", numOfTags));
            } else {
                tag.setText("태그 없음");
            }

            TextView friend = (TextView) item.findViewById(R.id.event_friend);
            int numOfFriends = event.getFriends().size();
            if (numOfFriends != 0) {
                friend.setText(String.format("%d 명의 친구", numOfFriends));
           } else {
                friend.setText("친구 없음");
            }
            return item;
        }

    }
}
