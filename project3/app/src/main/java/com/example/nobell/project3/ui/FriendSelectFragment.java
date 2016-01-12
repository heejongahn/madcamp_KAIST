package com.example.nobell.project3.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FriendSelectFragment extends Fragment implements Updatable, Representable{
    private long mSelected[];
    private ListView mListView;
    private FriendSelectAdapter mAdapter;
    private List<Friend> mFriends;
    private boolean updated = false;


    public static void activate() {
        /* Make new fragment object with given arguments. */
        Fragment frag = FriendSelectFragment.instantiate(MainActivity.getInstance(), FriendSelectFragment.class.getName(), null);
        MainActivity.getInstance().startFragment(frag);
    }

    public FriendSelectFragment() {
    }

    @Override
    public void reactivated() {
        if (updated) {
            // update the UI.
            mAdapter.notifyDataSetChanged();
            updated = false;
        }
    }
    @Override
    public void notifyChanged(Object arg) {
        updated = true;
    }


    @Override
    public String getTitle() {
        return "친구 선택";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_select,null);

        mListView = (ListView) view.findViewById(R.id.listView);
        mFriends = new Select().all().from(Friend.class).execute();

        mAdapter = new FriendSelectAdapter(getContext(), R.layout.friend_select_item, mFriends);

        mListView.setAdapter(mAdapter);

        Button select = (Button) view.findViewById(R.id.friend_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> selected = mAdapter.getSelectedFriends();

                MainActivity.getInstance().notifyChangedToFragments(selected);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
