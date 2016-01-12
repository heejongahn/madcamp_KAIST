package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class WriteEventFragment extends Fragment implements Updatable {
    /*
     * This is not on the main tab part.
     * WriteEventFragment.activate() makes activate this part on
     * the container located in layout/activity_main
     *
     * Objective: reduce activity as much as possible.
     */

    private String mOriginalToolbarTitle;
    private Toolbar mToolbar;
    private Event mEvent;
    private List<Tag> mTags;
    private List<Friend> mFriends;
    private List<Friend> mAddedFriends;
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
        mAddedFriends = (ArrayList<Friend>) arg;
        updated = true;
    }

    public void reactivated() {
        if (updated) {
            for (Friend f: mAddedFriends) {
                LinearLayout friendLayout = (LinearLayout) getActivity().findViewById(R.id.write_event_friends);
                Button friendButton = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, friendLayout, false);
                friendButton.setText(f.name);
                friendLayout.addView(friendButton);
            }
            mAddedFriends = new ArrayList<Friend>();
            updated = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        long eventId = getArguments().getLong(KEY_EVENT);
        mEvent = Event.flushCache(eventId);

        mTags = new ArrayList<Tag>();
        mFriends = new ArrayList<Friend>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_event, container, false);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mOriginalToolbarTitle = (String) mToolbar.getTitle();
        mToolbar.setTitle("글 쓰기");

        Button tagAddButton = (Button) view.findViewById(R.id.add_tag_button);
        tagAddButton.setOnClickListener(new addTagListener());

        Button writeButton = (Button) view.findViewById(R.id.event_save_button);
        writeButton.setOnClickListener(new saveEventListener());

        Button frinedAddButton = (Button) view.findViewById(R.id.add_friend_button);
        frinedAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendSelectFragment.activate();
            }
        });

        if (mEvent.body != null) {
            EditText bodyEditText = (EditText) view.findViewById(R.id.write_event_body);
            bodyEditText.setText(mEvent.body);
        }

        LinearLayout tagLayout = (LinearLayout) view.findViewById(R.id.write_event_tags);
        for (Tag t: mEvent.getTags()) {
            Button tagButton = (Button) inflater.inflate(R.layout.custom_small_button, tagLayout, false);
            tagButton.setText(t.tagName);
            tagLayout.addView(tagButton);
        }

        LinearLayout friendLayout = (LinearLayout) view.findViewById(R.id.write_event_friends);
        for (Friend f: mEvent.getFriends()) {
            Button friendButton = (Button) inflater.inflate(R.layout.custom_small_button, friendLayout, false);
            friendButton.setText(f.name);
            friendLayout.addView(friendButton);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar.setTitle(mOriginalToolbarTitle);
    }

    public class addTagListener implements View.OnClickListener {
        public addTagListener() {}

        @Override
        public void onClick(View v) {
            LinearLayout tagLayout = (LinearLayout) getActivity().findViewById(R.id.write_event_tags);
            EditText tagEditText = (EditText) getActivity().findViewById(R.id.write_event_tag);
            String name = tagEditText.getText().toString();
            tagEditText.setText("");

            Tag t = Tag.addOrGet(name);
            if (!mEvent.getTags().contains(t)) {
                mTags.add(t);
            }

            Button button = (Button) getLayoutInflater(null).inflate(R.layout.custom_small_button, tagLayout, false);
            button.setText(name);

            tagLayout.addView(button);
        }
    }

    public class saveEventListener implements View.OnClickListener {
        public saveEventListener() {}

        @Override
        public void onClick(View v) {
            EditText bodyEditText = (EditText) getActivity().findViewById(R.id.write_event_body);
            String body = bodyEditText.getText().toString();

            mEvent.body = body;
            mEvent.save();

            for (Tag t: mTags) {
                mEvent.addTag(t);
            }
            for (Friend f: mFriends) {
                mEvent.addFriend(f);
            }

            if (mEvent.getDate() == null) {
                mEvent.setDate(new Date());
            }

            MainActivity.getInstance().notifyChangedToFragments(null);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
