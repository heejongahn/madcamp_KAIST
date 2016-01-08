package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    public static void activate() {
        FragmentTransaction t = ((MainActivity)MainActivity.getContext()).getSupportFragmentManager().beginTransaction();
        WriteEventFragment mFrag = new WriteEventFragment();
        t.replace(0, mFrag);
        t.addToBackStack(null);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        t.commit();
    }

    public WriteEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_event, container, false);
    }


}
