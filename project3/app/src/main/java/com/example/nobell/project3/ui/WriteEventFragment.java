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

import com.activeandroid.query.Select;
import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class WriteEventFragment extends Fragment {
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
    private static final String KEY_EVENT = "eventID";

    public static void activate(Event e) {
        Bundle args = new Bundle();

        if (e == null) {
            e = new Event();
            e.save();
        }
        args.putLong(KEY_EVENT, e.getId());

        /* Make new fragment object with given arguments. */
        Fragment frag = WriteEventFragment.instantiate(MainActivity.getInstance(), WriteEventFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public WriteEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        long eventId = getArguments().getLong(KEY_EVENT);
        mEvent = new Select().from(Event.class).where("Id = ?", eventId).executeSingle();

        mTags = mEvent.getTags();
        if (mTags == null) {
            mTags = new ArrayList<Tag>();
        }

        mFriends = mEvent.getFriends();
        if (mFriends == null) {
            mFriends = new ArrayList<Friend>();
        }
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
            mTags.add(t);

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
            getActivity().getSupportFragmentManager().popBackStack();
            // TODO: 메인 탭의 이벤트 리스트, 태그 리스트을 다시 refresh 해줘야 함
        }
    }
}
