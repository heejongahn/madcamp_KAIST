package com.example.nobell.project3.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Friend;

import java.util.List;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class FriendTabFragment extends Fragment implements Updatable, Representable{
    private ListView mListView;
    private FriendAdapter mAdapter;
    private List<Friend> friends;
    private boolean updated = false;

    public FriendTabFragment() {
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
        return "내 친구들";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_tab,null);

        mListView = (ListView) view.findViewById(R.id.listView);
        friends = new Select().all().from(Friend.class).execute();

        mAdapter = new FriendAdapter(getContext(), R.layout.friend_item, friends);
        mAdapter.notifyDataSetChanged();

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mItemClickListener);

        Button friendAddButton = (Button) view.findViewById(R.id.friend_add_button);
        friendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendAddFragment.activate();
            }
        });

        return view;
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {
            FriendDetailFragment.activate(friends.get(position));
        }
    };

}
