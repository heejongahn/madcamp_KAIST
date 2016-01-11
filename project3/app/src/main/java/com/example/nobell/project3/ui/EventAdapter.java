package com.example.nobell.project3.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
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

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    private Context mContext;
    private List<Event> mEvents;
    private int mResource;
    LayoutInflater mInflater;

    public EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        mContext = context;
        mEvents = events;
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        LinearLayout old_tags = (LinearLayout) convertView.findViewById(R.id.event_tags);
        if (old_tags != null) {old_tags.removeAllViews();}

        LinearLayout old_friends = (LinearLayout) convertView.findViewById(R.id.event_friends);
        if (old_friends != null) {old_friends.removeAllViews();}

        Event event = mEvents.get(position);

        TextView body = (TextView) convertView.findViewById(R.id.event_body);
        body.setText(event.body);

        TextView date = (TextView) convertView.findViewById(R.id.event_date);
        date.setText(event.getDate());

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);


        LinearLayout tag_layout = (LinearLayout) convertView.findViewById(R.id.event_tags);

        List<Tag> tags = event.getTags();
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

        LinearLayout friend_layout = (LinearLayout) convertView.findViewById(R.id.event_friends);

        List<Friend> friends = event.getFriends();
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

        return convertView;
    }

}