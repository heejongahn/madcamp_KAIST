package com.example.junhong.tabview2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class Dialog extends DialogFragment {
    private int mDayofMonth;

    public Dialog() {
        // Required empty public constructor
    }

    public static Dialog newInstance(int day) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putInt("dayOfMonth", day);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDayofMonth = getArguments().getInt("dayOfMonth");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TableLayout table = (TableLayout) inflater.inflate(R.layout.dialog, container, false);
        return table;
    }

}
