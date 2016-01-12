package com.example.nobell.project3.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    /* Sort the given events itself. */
    private EventAdapter mAdapter;
    private Context mContext;
    private List<Event> mEvents;
    private int mResource;
    private LayoutInflater mInflater;

    public EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        mContext = context;
        Event.sort(events, true);
        mEvents = events;
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapter = this;
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
        if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);
        }

        final Event mEvent = mEvents.get(position);

        TextView body = (TextView) convertView.findViewById(R.id.event_body);
        body.setText(mEvent.body);

        TextView date = (TextView) convertView.findViewById(R.id.event_date);
        date.setText(mEvent.getDate());

        List<Tag> tags = mEvent.getTags();

        LinearLayout tag_layout = (LinearLayout) convertView.findViewById(R.id.event_tags);
        if (tag_layout != null) {tag_layout.removeAllViews();}

        if (tags.size() != 0) {
            if (tags.size() > 3) {
                for (i=0; i < 3; i++) {
                    Button tagButton = (Button) mInflater.inflate(R.layout.custom_small_button, null);
                    tagButton.setText(tags.get(i).tagName);
                    tag_layout.addView(tagButton);
                }
                TextView moreTags = new TextView(mContext);
                moreTags.setText(String.format("...and %d more tags", tags.size() - 3));
                moreTags.setTextSize(12);
                tag_layout.addView(moreTags);
            }
            else {
                for (Tag tag: tags) {
                    Button tagButton = (Button) mInflater.inflate(R.layout.custom_small_button, null);
                    tagButton.setText(tag.tagName);
                    tag_layout.addView(tagButton);
                }
            }
        }

        List<Friend> friends = mEvent.getFriends();

        LinearLayout friend_layout = (LinearLayout) convertView.findViewById(R.id.event_friends);
        if (friend_layout != null) {friend_layout.removeAllViews();}

        if (friends.size() != 0) {
            if (friends.size() > 3) {
                for (i=0; i < 3; i++) {
                    Button friendButton = (Button) mInflater.inflate(R.layout.custom_small_button, null);
                    friendButton.setText(friends.get(i).name);
                    friend_layout.addView(friendButton);
                }
                TextView moreFriends = new TextView(mContext);
                moreFriends.setText(String.format("...and %d more friends", friends.size() - 3));
                moreFriends.setTextSize(12);
                friend_layout.addView(moreFriends);
            }
            else {
                for (Friend friend : friends) {
                    Button friendButton = (Button) mInflater.inflate(R.layout.custom_small_button, null);
                    friendButton.setText(friend.name);
                    friend_layout.addView(friendButton);
                }
            }
        }

        Button editButton = (Button) convertView.findViewById(R.id.event_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteEventFragment.activate(mEvent);
            }
        });


        Button deleteButton = (Button) convertView.findViewById(R.id.event_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvent.delete();
                mEvents.remove(mEvent);
                mAdapter.notifyDataSetChanged();
            }
        });
        
        return convertView;
    }
}