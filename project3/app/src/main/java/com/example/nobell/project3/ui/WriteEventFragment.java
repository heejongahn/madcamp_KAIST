package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteEventFragment extends Fragment implements Updatable, Representable {
    /*
     * This is not on the main tab part.
     * WriteEventFragment.activate() makes activate this part on
     * the container located in layout/activity_main
     *
     * Objective: reduce activity as much as possible.
     */
    View view;  /* Used in listener */
    @Override
    public String getTitle() {
        return "글쓰기";
    }
    private Toolbar mToolbar;
    private Event mEvent;
    private List<Tag> mTags;
    private List<Friend> mFriends;
    private static final String KEY_EVENT = "eventID";
    private boolean updated = false;

    public static void activate(Event e) {
        Bundle args = new Bundle();

        if (e == null) {
            e = new Event();
            e.save();
        }
        args.putLong(KEY_EVENT, Event.getIdWithCache(e));

        /* Make new fragment object with given arguments. */
        Fragment frag = WriteEventFragment.instantiate(MainActivity.getInstance(), WriteEventFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public WriteEventFragment() {
        // Required empty public constructor
    }

    public void notifyChanged(Object arg) {
        if (arg != null) {
            for (Friend f : (ArrayList<Friend>) arg) {
                mFriends.add(f);
            }
        }
        updated = true;
    }

    public void reactivated() {
        if (updated) {
            refreshFriend(null);
            updated = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        long eventId = getArguments().getLong(KEY_EVENT);
        mEvent = Event.flushCache(eventId);

        mTags = mEvent.getTags();
        mFriends = mEvent.getFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_write_event, container, false);

        Button tagAddButton = (Button) view.findViewById(R.id.add_tag_button);
        tagAddButton.setOnClickListener(new addTagListener());

        Button writeButton = (Button) view.findViewById(R.id.event_save_button);
        writeButton.setOnClickListener(new saveEventListener());

        Button friendAddButton = (Button) view.findViewById(R.id.add_friend_button);
        friendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendSelectFragment.activate();
            }
        });

        if (mEvent.body != null) {
            EditText bodyEditText = (EditText) view.findViewById(R.id.write_event_body);
            bodyEditText.setText(mEvent.body);
        }

        refreshTags(view);
        refreshFriend(view);

        return view;
    }

    public class addTagListener implements View.OnClickListener {
        public addTagListener() {}

        @Override
        public void onClick(View v) {
            LinearLayout tagLayout = (LinearLayout) view.findViewById(R.id.write_event_tags);
            EditText tagEditText = (EditText) view.findViewById(R.id.write_event_tag);
            String name = tagEditText.getText().toString();
            if (name.length()>0) {
                tagEditText.setText("");

                Tag t = Tag.addOrGet(name);
                if (!mTags.contains(t)) { /* Surprisingly, equals() might be implemented on the ActivteAndroid data objects */
                    /* Add tag button */
                    mTags.add(t);

                    Button button = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, tagLayout, false);
                    button.setText(name);

                    tagLayout.addView(button);
                }

            }
            refreshTags(null);
        }
    }

    public class saveEventListener implements View.OnClickListener {
        public saveEventListener() {}

        @Override
        public void onClick(View v) {
            EditText bodyEditText = (EditText) view.findViewById(R.id.write_event_body);
            String body = bodyEditText.getText().toString();

            if (body.length() == 0) {
                bodyEditText.setError("적어도 1글자 이상이어야 합니다.");
                bodyEditText.requestFocus();
                return;
            }

            mEvent.body = body;

            for (Tag t: mTags) {
                if (!mEvent.getTags().contains(t)) {
                    mEvent.addTag(t);
                }
            }

            for (Friend f: mFriends) {
                if (!mEvent.getFriends().contains(f)) {
                    mEvent.addFriend(f);
                }
            }

            if (mEvent.getDate() == null) {
                mEvent.setDate(new Date());
            }

            mEvent.save();

            MainActivity.getInstance().notifyChangedToFragments(null);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public void refreshTags(View parent) {
        LinearLayout tag_layout;
        if (parent != null) {
            tag_layout = (LinearLayout) parent.findViewById(R.id.write_event_tags);
        } else {
            tag_layout = (LinearLayout) view.findViewById(R.id.write_event_tags);
        }
        if (tag_layout != null) {tag_layout.removeAllViews();}

        if (mTags.size() != 0) {
            if (mTags.size() > 3) {
                for (int i=0; i < 3; i++) {
                    Button tagButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                    tagButton.setText(mTags.get(i).tagName);
                    tag_layout.addView(tagButton);
                }
                TextView moreTags = new TextView(getContext());
                moreTags.setText(String.format("...and %d more tags", mTags.size() - 3));
                moreTags.setTextSize(12);
                tag_layout.addView(moreTags);
            }
            else {
                for (Tag tag: mTags) {
                    Button tagButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                    tagButton.setText(tag.tagName);
                    tag_layout.addView(tagButton);
                }
            }
        }
    }

    public void refreshFriend(View parent) {
        LinearLayout friend_layout;
        if (parent != null) {
            friend_layout = (LinearLayout) parent.findViewById(R.id.write_event_friends);
        } else {
            friend_layout = (LinearLayout) view.findViewById(R.id.write_event_friends);
        }

        if (friend_layout != null) {friend_layout.removeAllViews();}

        if (mFriends.size() != 0) {
            if (mFriends.size() > 3) {
                for (int i=0; i < 3; i++) {
                    Button friendButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                    friendButton.setText(mFriends.get(i).name);
                    friend_layout.addView(friendButton);
                }
                TextView moreFriends = new TextView(getContext());
                moreFriends.setText(String.format("...and %d more friends", mFriends.size() - 3));
                moreFriends.setTextSize(12);
                friend_layout.addView(moreFriends);
            }
            else {
                for (Friend friend : mFriends) {
                    Button friendButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, null);
                    friendButton.setText(friend.name);
                    friend_layout.addView(friendButton);
                }
            }
        }
    }
}
