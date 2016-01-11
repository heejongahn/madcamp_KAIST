package com.example.nobell.project3.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Friend;

/**
 * Created by Sia on 2016-01-11.
 */
public class FriendAddFragment extends Fragment {

    public FriendAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_tab,null);

        return view;
    }
}
