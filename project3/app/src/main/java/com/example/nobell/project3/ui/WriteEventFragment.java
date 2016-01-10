package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;

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

    public static void activate() {
        WriteEventFragment mFrag = new WriteEventFragment();
        MainActivity.getInstance().startFragment(mFrag);
    }

    public WriteEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_event, container, false);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mOriginalToolbarTitle = (String) mToolbar.getTitle();
        mToolbar.setTitle("글 쓰기");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar.setTitle(mOriginalToolbarTitle);
    }
}
